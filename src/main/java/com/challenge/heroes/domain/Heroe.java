package com.challenge.heroes.domain;

import java.util.UUID;

public class Heroe {

    public UUID getId() {
        return id;
    }

    private UUID id;

    public String getName() {
        return name;
    }

    private String name;

    public Heroe(UUID id, String name) {
        this.id = id;
        this.name = name;
    }
}
