package movieticketbookingsystem.entities;

import java.util.List;

public class Cinema {
    public String id;
    public String name;
    public City city;
    public List<Screen> screens;

    public Cinema(String id, String name, City city, List<Screen> screens) {
        this.id = id;
        this.name = name;
        this.city = city;
        this.screens = screens;
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public City getCity() { return city; }
    public List<Screen> getScreens() { return screens; }
}
