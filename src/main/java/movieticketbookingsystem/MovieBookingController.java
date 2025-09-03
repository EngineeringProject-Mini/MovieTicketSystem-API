package movieticketbookingsystem;

import movieticketbookingsystem.entities.*;
import movieticketbookingsystem.enums.SeatStatus;
import movieticketbookingsystem.enums.SeatType;
import movieticketbookingsystem.strategy.payment.CreditCardPaymentStrategy;
import movieticketbookingsystem.strategy.pricing.WeekdayPricingStrategy;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/movies")
public class MovieBookingController {

    private final MovieBookingService service = MovieBookingService.getInstance();

    // --- City ---
    @PostMapping("/cities")
    public City addCity(@RequestParam String id, @RequestParam String name) {
        return service.addCity(id, name);
    }

    // --- Movie ---
    @PostMapping("/movies")
    public String addMovie(@RequestParam String id,
                           @RequestParam String title,
                           @RequestParam int duration) {
        Movie movie = new Movie(id, title, duration);
        service.addMovie(movie);
        return "Movie added: " + movie.getTitle();
    }

    // --- Cinema & Screen with seats ---
    @PostMapping("/cinemas")
    public Cinema addCinema(@RequestParam String id,
                            @RequestParam String name,
                            @RequestParam String cityId,
                            @RequestParam int rows,
                            @RequestParam int cols) {
        Screen screen = new Screen("S-" + id);

        for (int i = 1; i <= rows; i++) {
            for (int j = 1; j <= cols; j++) {
                String seatId = (char) ('A' + (i - 1)) + String.valueOf(j);
                screen.addSeat(new Seat(seatId, i, j,
                        j <= (cols / 2) ? SeatType.REGULAR : SeatType.PREMIUM));
            }
        }

        return service.addCinema(id, name, cityId, List.of(screen));
    }

    // --- Show ---
    @PostMapping("/shows")
    public Show addShow(@RequestParam String id,
                        @RequestParam String movieId,
                        @RequestParam String screenId,
                        @RequestParam String cinemaId,
                        @RequestParam int hoursFromNow) {

        Screen cinema = service.findShows("", "").stream()  // not ideal, better to keep screens mapped
                .map(s -> service.findShows("", ""))
                .flatMap(List::stream)
                .map(Show::getScreen)
                .findFirst().orElse(null);

        Screen screen = null;
        for (Screen scr : service.addCinema(cinemaId, "", "", List.of()).getScreens()) {
            if (scr.getId().equals(screenId)) {
                screen = scr;
            }
        }

        Movie movie = new Movie(movieId, "Dummy", 120); // should lookup real movie
        return service.addShow(id, movie, screen, LocalDateTime.now().plusHours(hoursFromNow), new WeekdayPricingStrategy());
    }

    // --- User ---
    @PostMapping("/users")
    public User addUser(@RequestParam String name, @RequestParam String email) {
        return service.createUser(name, email);
    }

    // --- Search Shows ---
    @GetMapping("/shows/search")
    public List<Show> searchShows(@RequestParam String movieTitle,
                                  @RequestParam String cityName) {
        return service.findShows(movieTitle, cityName);
    }

    // --- Book Tickets ---
    @PostMapping("/book")
    public String bookTickets(@RequestParam String userId,
                              @RequestParam String showId,
                              @RequestParam List<String> seatIds,
                              @RequestParam String cardNumber,
                              @RequestParam String cvv) {

        Show show = service.findShows("", "").stream()
                .filter(s -> s.getId().equals(showId))
                .findFirst().orElseThrow();

        List<Seat> desiredSeats = show.getScreen().getSeats().stream()
                .filter(seat -> seatIds.contains(seat.getId()) && seat.getStatus() == SeatStatus.AVAILABLE)
                .toList();

        Optional<Booking> bookingOpt = service.bookTickets(
                userId,
                showId,
                desiredSeats,
                new CreditCardPaymentStrategy(cardNumber, cvv)
        );

        if (bookingOpt.isPresent()) {
            Booking booking = bookingOpt.get();
            return String.format("✅ Booking Successful!\nBooking ID: %s\nSeats: %s\nTotal: $%.2f",
                    booking.getId(),
                    booking.getSeats().stream().map(Seat::getId).collect(Collectors.toList()),
                    booking.getTotalAmount());
        } else {
            return "❌ Booking failed. Seats may be locked or unavailable.";
        }
    }

    // --- Shutdown ---
    @PostMapping("/shutdown")
    public String shutdown() {
        service.shutdown();
        return "System shut down.";
    }
}
