package com.challenge.heroes.infraestructure.web;

import com.challenge.heroes.application.ports.in.HeroesService;
import com.challenge.heroes.domain.Heroe;
import com.challenge.heroes.infraestructure.web.exceptions.HeroeNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/heroes")
public class HeroesController {

    private HeroesService service;

    public HeroesController(HeroesService service) {
        this.service = service;
    }

    @GetMapping()
    public ResponseEntity<List<Heroe>> getAllHeroes(@RequestParam(value = "name", defaultValue = "", required = false) String name) {
        return ResponseEntity.ok(service.searchHeroesByName(name));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Heroe> getHeroe(@PathVariable(name = "id", required = true) String id) {
        return ResponseEntity.ok(service.findHeroe(UUID.fromString(id)).orElseThrow(() -> new HeroeNotFoundException(id)));
    }
}
