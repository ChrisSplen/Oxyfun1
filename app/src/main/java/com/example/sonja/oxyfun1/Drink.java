package com.example.sonja.oxyfun1;

public class Drink {
    private String name;
    private String description;
    private int imageResourceId;
    public static final Drink[] drinks = {
            new Drink("entry1", "A couple of espresso shots with steamed milk"),
            new Drink("entry2", "Espresso, hot milk, and a steamed milk foam"),
            new Drink("entry3", "Highest quality beans roasted and brewed fresh")
    };
    //Each Drink has a name, description, and an image resource
    private Drink(String name, String description) {
        this.name = name;
        this.description = description;
    }
    public String getDescription() {
        return description;
    }
    public String getName() {
        return name;
    }
    public String toString() {
        return this.name;
    }
}

