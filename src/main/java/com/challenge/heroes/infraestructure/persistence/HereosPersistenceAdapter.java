package com.challenge.heroes.infraestructure.persistence;

import com.challenge.heroes.application.ports.out.HeroesPersistence;
import com.challenge.heroes.domain.Heroe;
import com.challenge.heroes.infraestructure.persistence.entity.HeroeEntity;
import com.challenge.heroes.infraestructure.persistence.maper.HeroeMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class HereosPersistenceAdapter implements HeroesPersistence {

    private static final Logger LOG = LoggerFactory.getLogger(HereosPersistenceAdapter.class);

    private HeroesRepositoryJpa repository;

    private HeroeMapper mapper;

    @Autowired
    public HereosPersistenceAdapter(HeroesRepositoryJpa repository, HeroeMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public Heroe save(Heroe nuevoHeroe) {
        LOG.info("Adapter save: {}", nuevoHeroe.toString());
        HeroeEntity heroeEntity = repository.save(mapper.domainToEntity(nuevoHeroe));
        return mapper.entityToDomain(heroeEntity);
    }

    @Override
    public Heroe update(Heroe heroeActualizado) {
        HeroeEntity domainToEntity = mapper.domainToEntity(heroeActualizado);
        HeroeEntity heroeEntity = repository.save(domainToEntity);
        return mapper.entityToDomain(heroeEntity);
    }

    @Override
    public boolean delete(UUID id) {
        repository.deleteById(id);
        return true;
    }



    @Override
    public List<Heroe> searchByName(String name) {
        return mapper.entityListToDomainList(repository.findByNameContains(name));
    }

    @Override
    public Optional<Heroe> findById(UUID id) {
        Optional<HeroeEntity> optionalHeroeEntity = repository.findById(id);
        return Optional.of(mapper.entityToDomain(optionalHeroeEntity.get()));
    }

    @Override
    public List<Heroe> findAll() {
        return mapper.entityListToDomainList(repository.findAll());
    }
}
