package com.challenge.heroes;

import com.challenge.heroes.application.ports.in.HeroesService;
import com.challenge.heroes.domain.Heroe;
import com.challenge.heroes.infraestructure.web.HeroesController;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.hamcrest.Matchers.hasSize;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(HeroesController.class)
@ExtendWith(SpringExtension.class)
public class HeroesControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    private HeroesService service;
    @Test
    public void getAllHeroesWithZeroResult() throws Exception {

        List<Heroe> heroesEmpty = new ArrayList<>();
        when(service.searchHeroesByName(anyString())).thenReturn(heroesEmpty);

        mockMvc.perform(MockMvcRequestBuilders.get("/heroes"))
                .andDo(print())
                .andExpect(jsonPath("$", hasSize(heroesEmpty.size())));
    }

    @Test
    public void getAllHeroesWithNameAndZeroResult() throws Exception {

        List<Heroe> heroesEmpty = new ArrayList<>();
        when(service.searchHeroesByName(anyString())).thenReturn(heroesEmpty);

        mockMvc.perform(MockMvcRequestBuilders.get("/heroes?name=man"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(heroesEmpty.size())));
    }

    @Test
    public void getAllHeroesWithOneResult() throws Exception {

        List<Heroe> heroes = List.of(new Heroe(UUID.randomUUID(), "Spiderman"));
        when(service.searchHeroesByName(anyString())).thenReturn(heroes);

        mockMvc.perform(MockMvcRequestBuilders.get("/heroes"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].name").value("Spiderman"));;
    }

    @Test
    public void getHeroesById() throws Exception {

        Heroe spiderman = new Heroe(UUID.randomUUID(), "Spiderman");
        when(service.findHeroe(any(UUID.class))).thenReturn(Optional.of(spiderman));

        mockMvc.perform(MockMvcRequestBuilders.get("/heroes/" + spiderman.getId()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isNotEmpty())
                .andExpect(jsonPath("$.name").value("Spiderman"));
    }

    @Test
    public void getHeroesByIdNotExistReturnNotFound() throws Exception {

        when(service.findHeroe(any(UUID.class))).thenReturn(Optional.empty());
        String id = UUID.randomUUID().toString();

        mockMvc.perform(MockMvcRequestBuilders.get("/heroes/" + id))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$").isNotEmpty())
                .andExpect(jsonPath("$.message").value("No se encontro el Heroe con id " + id))
                .andExpect(jsonPath("$.code").value("error-1"));
    }
}
