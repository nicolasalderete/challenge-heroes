package com.challenge.heroes.infraestructure.config;

import com.challenge.heroes.application.ports.in.HeroesService;
import com.challenge.heroes.application.ports.out.HeroesPersistence;
import com.challenge.heroes.application.usecase.HeroeServiceImpl;
import com.challenge.heroes.infraestructure.persistence.maper.HeroeMapper;
import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

@Configuration
@EnableCaching
public class HeroeConfig {

    @Bean
    public HeroesService heroesServiceImpl(HeroesPersistence repository) {
        return new HeroeServiceImpl(repository);
    }

    @Bean
    public HeroeMapper heroeMapper() {
        return HeroeMapper.INSTANCE;
    }

    @Bean
    public Caffeine caffeineConfig() {
        return Caffeine.newBuilder().expireAfterWrite(60, TimeUnit.MINUTES);
    }

    @Bean
    public CacheManager cacheManager(Caffeine caffeine) {
        CaffeineCacheManager caffeineCacheManager = new CaffeineCacheManager();
        caffeineCacheManager.setCaffeine(caffeine);
        return caffeineCacheManager;
    }
}
