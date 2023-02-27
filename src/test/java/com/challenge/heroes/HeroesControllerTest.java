package com.challenge.heroes;

import com.challenge.heroes.application.ports.in.HeroesService;
import com.challenge.heroes.domain.Heroe;
import com.challenge.heroes.infraestructure.web.HeroeRequest;
import com.challenge.heroes.infraestructure.web.HeroesController;
import com.challenge.heroes.infraestructure.web.exceptions.HeroeNotFoundException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
    public void getAllHeroesByNameThenZeroResult() throws Exception {

        List<Heroe> heroesEmpty = new ArrayList<>();
        when(service.searchHeroesByName(anyString())).thenReturn(heroesEmpty);

        mockMvc.perform(MockMvcRequestBuilders.get("/heroes?name=man"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(heroesEmpty.size())));
    }

    @Test
    public void getAllHeroesByNameThenOneResult() throws Exception {

        List<Heroe> heroes = List.of(new Heroe("Spiderman"));
        when(service.searchHeroesByName(anyString())).thenReturn(heroes);

        mockMvc.perform(MockMvcRequestBuilders.get("/heroes?name="))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].name").value("Spiderman"));;
    }

    @Test
    public void getHeroesById() throws Exception {

        Heroe spiderman = new Heroe("Spiderman");
        spiderman.setId(UUID.randomUUID());
        when(service.findHeroe(any(UUID.class))).thenReturn(spiderman);

        mockMvc.perform(MockMvcRequestBuilders.get("/heroes/" + spiderman.getId()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isNotEmpty())
                .andExpect(jsonPath("$.name").value("Spiderman"));
    }

    @Test
    public void getHeroesByIdNotExistReturnNotFound() throws Exception {

        when(service.findHeroe(any(UUID.class))).thenThrow(HeroeNotFoundException.class);
        String id = UUID.randomUUID().toString();

        mockMvc.perform(MockMvcRequestBuilders.get("/heroes/" + id))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$").isNotEmpty())
                .andExpect(jsonPath("$.message").value("No se encontro el Heroe con id null"))
                .andExpect(jsonPath("$.code").value("error-1"));
    }

    @Test
    public void postHeroeReturnNew() throws Exception {
        Heroe nuevoHeroe = new Heroe("Batman");
        nuevoHeroe.setId(UUID.randomUUID());
        when(service.createHeroe(any())).thenReturn(nuevoHeroe);

        HeroeRequest request = new HeroeRequest("Batman");
        ObjectMapper mapper = new ObjectMapper();
        ObjectWriter objectWriter = mapper.writerWithDefaultPrettyPrinter();
        String writeValueAsString = objectWriter.writeValueAsString(request);

        mockMvc.perform(post("/heroes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(writeValueAsString))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$").isNotEmpty())
                .andExpect(jsonPath("$.name").value(nuevoHeroe.getName()))
                .andExpect(jsonPath("$.id").value(nuevoHeroe.getId().toString()));
    }
}
