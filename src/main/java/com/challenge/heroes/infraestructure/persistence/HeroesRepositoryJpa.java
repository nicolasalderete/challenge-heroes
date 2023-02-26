package com.challenge.heroes.infraestructure.persistence;

import com.challenge.heroes.domain.Heroe;
import com.challenge.heroes.infraestructure.persistence.entity.HeroeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface HeroesRepositoryJpa extends JpaRepository<HeroeEntity, UUID> {

    public List<HeroeEntity> findByNameContains(String name);

}
