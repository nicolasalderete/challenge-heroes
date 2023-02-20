package com.challenge.heroes.domain;

import java.util.UUID;

public class Heroe {

    private UUID id;

    private String name;

    public Heroe(UUID id, String name) {
        this.id = id;
        this.name = name;
    }
}
