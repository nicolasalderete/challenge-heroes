package com.challenge.heroes.infraestructure.web;

public class HeroeRequest {

    public String getName() {
        return name;
    }

    private String name;

    public HeroeRequest(String name) {
        this.name = name;
    }

    public HeroeRequest() {
    }

    @Override
    public String toString() {
        return "HeroeRequest{" +
                "name='" + name + '\'' +
                '}';
    }
}
