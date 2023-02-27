package com.challenge.heroes.application.ports.in;

import com.challenge.heroes.domain.Heroe;

import java.util.List;
import java.util.UUID;

public interface HeroesService {

    public List<Heroe> searchHeroesByName(String name);

    public Heroe findHeroe(UUID id);

    public Heroe createHeroe(HeroeCommand nuevoHeroe);

    public String deleteHeroeById(UUID id);

    public Heroe updateHeroe(UUID id, HeroeCommand heroeActualizado);


    class HeroeCommand {

        public String getName() {
            return name;
        }

        private String name;

        public HeroeCommand(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return "HeroeCommand{" +
                    "name='" + name + '\'' +
                    '}';
        }
    }


}
