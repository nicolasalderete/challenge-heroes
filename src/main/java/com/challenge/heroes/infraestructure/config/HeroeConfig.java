package com.challenge.heroes.infraestructure.config;

import com.challenge.heroes.application.ports.in.HeroesService;
import com.challenge.heroes.application.ports.out.HeroesPersistence;
import com.challenge.heroes.application.usecase.HeroeServiceImpl;
import com.challenge.heroes.infraestructure.persistence.maper.HeroeMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HeroeConfig {

    @Bean
    public HeroesService heroesServiceImpl(HeroesPersistence repository) {
        return new HeroeServiceImpl(repository);
    }

    @Bean
    public HeroeMapper heroeMapper() {
        return HeroeMapper.INSTANCE;
    }
}
