package com.challenge.heroes.application.usecase;

import com.challenge.heroes.application.ports.in.HeroesService;
import com.challenge.heroes.application.ports.out.HeroesPersistence;
import com.challenge.heroes.domain.Heroe;
import com.challenge.heroes.infraestructure.web.exceptions.HeroeNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.UUID;

public class HeroeServiceImpl implements HeroesService {

    private static final Logger LOG = LoggerFactory.getLogger(HeroeServiceImpl.class);

    private HeroesPersistence repository;

    public HeroeServiceImpl(HeroesPersistence repository) {
        this.repository = repository;
    }

    @Override
    public List<Heroe> searchHeroesByName(String name) {
        if (name.isBlank()) {
            return repository.findAll();
        } else {
            return repository.searchByName(name);
        }
    }

    @Override
    public Heroe findHeroe(UUID id) {
        return repository.findById(id).orElseThrow(() -> new HeroeNotFoundException(id.toString()));
    }

    @Override
    public Heroe createHeroe(HeroeCommand nuevoHeroe) {
        LOG.info("Create Heroe: {}", nuevoHeroe.toString());
        return repository.save(new Heroe(nuevoHeroe.getName()));
    }

    @Override
    public String deleteHeroeById(UUID id) {
        repository.findById(id).orElseThrow(() -> new HeroeNotFoundException(id.toString()));
        repository.delete(id);
        return id.toString();
    }

    @Override
    public Heroe updateHeroe(UUID id, HeroeCommand heroeActualizado) {
        Heroe heroe = repository.findById(id).orElseThrow(() -> new HeroeNotFoundException(id.toString()));
        heroe.setName(heroeActualizado.getName());
        return repository.save(heroe);
    }
}
