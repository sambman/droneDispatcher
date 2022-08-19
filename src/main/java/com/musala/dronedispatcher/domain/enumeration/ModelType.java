package com.musala.dronedispatcher.domain.enumeration;

/**
 * The ModelType enumeration.
 */
public enum ModelType {
    LIGHTWEIGHT("Lightweight"),
    MIDDLEWEIGHT("Middleweight"),
    CRUISEWEIGHT("Cruiserweight"),
    HEAVYWEIGHT("Heavyweight");

    private final String value;


    ModelType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
