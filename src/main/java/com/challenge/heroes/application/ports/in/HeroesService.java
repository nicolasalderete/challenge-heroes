package com.challenge.heroes.application.ports.in;

import com.challenge.heroes.domain.Heroe;

import java.util.List;
import java.util.UUID;

public interface HeroesService {

    public List<Heroe> searchHeroesByName(String name);

    public Heroe findHeroe(UUID id);

    public Heroe createHeroe(HeroeCommand nuevoHeroe);

    public boolean deleteHeroeById(UUID id);

    public Heroe updateHeroe(String id, HeroeCommand heroeActualizado);

    class HeroeCommand {

        private String name;

        public HeroeCommand(String name) {
            this.name = name;
        }
    }
}
