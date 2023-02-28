package com.challenge.heroes;

import com.challenge.heroes.infraestructure.persistence.entity.HeroeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TestH2Repository extends JpaRepository<HeroeEntity, UUID> {
}
