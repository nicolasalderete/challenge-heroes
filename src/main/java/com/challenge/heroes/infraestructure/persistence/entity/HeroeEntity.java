package com.challenge.heroes.infraestructure.persistence.entity;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "HEROES")
public class HeroeEntity {

    @Id
    @GeneratedValue()
    private UUID id;

    @Column(name = "name")
    private String name;

    public HeroeEntity() {
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
}
