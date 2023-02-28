package com.challenge.heroes;

import com.challenge.heroes.domain.Heroe;
import com.challenge.heroes.infraestructure.persistence.entity.HeroeEntity;
import com.challenge.heroes.infraestructure.web.HeroeRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.DefaultUriBuilderFactory;
import org.springframework.web.util.UriBuilder;
import org.springframework.web.util.UriBuilderFactory;

import java.net.URI;
import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class HeroesIntegrationTest {

    @LocalServerPort
    private int port;

    private String baseUrl = "http://localhost";

    private static RestTemplate restTemplate;

    private URI api;

    @Autowired
    private TestH2Repository repository;

    @BeforeAll
    public static void init() {
        restTemplate = new RestTemplate();
    }

    @BeforeEach
    public void setup() {
        api = URI.create(new StringBuilder().append(baseUrl).append(":").append(port).append("/heroes").toString());
        System.out.println(api);
    }

    @Test
    public void testAddHeroe() {
        HeroeRequest nuevoHeroe = new HeroeRequest("Batman");
        Heroe response = restTemplate.postForObject(api, nuevoHeroe, Heroe.class);

        Assertions.assertEquals("Batman", response.getName());

    }

    @Test
    public void testGetAllHeroe() {

        HeroeEntity heroe = new HeroeEntity();
        heroe.setName("Spiderman");

        HeroeEntity saveHeroe = repository.save(heroe);

        List<Heroe> response = restTemplate.getForObject(api, List.class);

        Assertions.assertEquals(1, response.size());

    }

    @Test
    public void testGetHeroeById() {

        HeroeEntity heroe = new HeroeEntity();
        heroe.setName("Spiderman");

        HeroeEntity saveHeroe = repository.save(heroe);

        Heroe response = restTemplate.getForObject(api.toString() + "/" + saveHeroe.getId().toString(), Heroe.class);

        Assertions.assertEquals("Spiderman", response.getName());
        Assertions.assertEquals(saveHeroe.getId(), response.getId());

    }
}
