package com.challenge.heroes.infraestructure.web;

import com.challenge.heroes.application.ports.in.HeroesService;
import com.challenge.heroes.application.ports.in.HeroesService.HeroeCommand;
import com.challenge.heroes.domain.Heroe;
import com.challenge.heroes.infraestructure.web.exceptions.HeroeNotFoundException;
import com.challenge.heroes.utils.TimeMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/heroes")
public class HeroesController {

    private static final Logger LOG = LoggerFactory.getLogger(HeroesController.class);

    private HeroesService service;

    public HeroesController(HeroesService service) {
        this.service = service;
    }

    @GetMapping()
    @TimeMethod
    public ResponseEntity<List<Heroe>> getAllHeroes(@RequestParam(value = "name", defaultValue = "", required = false) String name) {
        LOG.info("Invoke GET /heroes with name {}", name);
        return ResponseEntity.ok(service.searchHeroesByName(name));
    }

    @GetMapping("/{id}")
    @TimeMethod
    public ResponseEntity<Heroe> getHeroe(@PathVariable(name = "id", required = true) String id) {
        LOG.info("Invoke GET /heroes/{}", id);
        return ResponseEntity.ok(service.findHeroe(UUID.fromString(id)));
    }

    @PostMapping()
    @TimeMethod
    public ResponseEntity<Heroe> postHeroe(@RequestBody HeroeRequest request) {
        LOG.info("Invoke POST /heroes with Body: {}", request.toString());
        return ResponseEntity.status(HttpStatus.CREATED).body(service.createHeroe(new HeroeCommand(request.getName())));
    }

    @PutMapping("/{id}")
    @TimeMethod
    public ResponseEntity<Heroe> putHeroe(@PathVariable(name = "id", required = true) String id, @RequestBody HeroeRequest request) {
        LOG.info("Invoke PUT /heroe/{} with body: {}", id, request.toString());
        Heroe heroe = service.updateHeroe(UUID.fromString(id), new HeroeCommand(request.getName()));
        return ResponseEntity.ok(heroe);
    }

    @DeleteMapping("/{id}")
    @TimeMethod
    public ResponseEntity deleteHeroe(@PathVariable(name = "id", required = true) String id) {
        LOG.info("Invoke DELETE /heroe/{}", id);
        return ResponseEntity.ok(service.deleteHeroeById(UUID.fromString(id)));
    }
}
