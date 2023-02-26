package com.challenge.heroes.application.ports.out;

import com.challenge.heroes.domain.Heroe;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface HeroesPersistence {

    public Heroe save(Heroe nuevoHeroe);

    public Heroe update(Heroe heroeActualizado);

    public boolean delete(UUID id);

    public List<Heroe> searchByName(String name);

    public Optional<Heroe> findById(UUID id);

    public List<Heroe> findAll();
}
