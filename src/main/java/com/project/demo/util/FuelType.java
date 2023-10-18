package com.project.demo.util;

public enum FuelType {
    INVALID(-1),
    UNKNOWN(0),
    BENZINE(1),
    DIESEL(2),
    ELECTRIC(3),
    HYBRID(4);

    private int id;

    FuelType(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
