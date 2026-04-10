package com.peladadesegunda.app.dto.enumeration;

public enum Position {

    GOALKEEPER("Goleiro"),
    DEFENDER("Zagueiro"),
    WINGBACK("Lateral"),
    MIDFIELDER("Meio-campista"),
    STRIKER("Atacante");

    private final String position;

    Position(String position) {
        this.position = position;
    }

    public String getPosition() {
        return position;
    }
}
