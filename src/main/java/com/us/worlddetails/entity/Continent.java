package com.us.worlddetails.entity;

public enum Continent {
    Asia("Asia"),
    Europe("Europe"),
    North_America("North_America"),
    Africa("Africa"),
    Oceania("Oceania"),
    Antarctica("Antarctica"),
    South_America("South_America");

    private final String value;

    Continent(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}