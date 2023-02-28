package com.challenge.heroes.domain;

import java.io.Serializable;
import java.util.UUID;

public class Heroe implements Serializable {

    private UUID id;

    private String name;

    public Heroe(String name) {
        this.name = name;
    }

    public Heroe() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Heroe{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
