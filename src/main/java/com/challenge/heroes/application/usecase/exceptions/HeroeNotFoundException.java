package com.challenge.heroes.application.usecase.exceptions;

public class HeroeNotFoundException extends RuntimeException {

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    private String id;

    public HeroeNotFoundException(String id) {
        this.id = id;
    }
}
