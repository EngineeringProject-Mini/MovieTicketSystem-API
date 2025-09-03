package movieticketbookingsystem.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import movieticketbookingsystem.strategy.pricing.PricingStrategy;

import java.time.LocalDateTime;

@Getter
public class Show {
    public String id;

    public String getId() {
        return id;
    }

    public PricingStrategy getPricingStrategy() {
        return pricingStrategy;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public Screen getScreen() {
        return screen;
    }

    public Movie getMovie() {
        return movie;
    }

    public Movie movie;
    public Screen screen;
    public LocalDateTime startTime;
    @JsonIgnore
    public PricingStrategy pricingStrategy;

    public Show(String id, Movie movie, Screen screen, LocalDateTime startTime, PricingStrategy pricingStrategy) {
        this.id = id;
        this.movie = movie;
        this.screen = screen;
        this.startTime = startTime;
        this.pricingStrategy = pricingStrategy;
    }

}
