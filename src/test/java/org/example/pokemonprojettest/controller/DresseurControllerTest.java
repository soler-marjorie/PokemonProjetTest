package org.example.pokemonprojettest.controller;

import org.example.pokemonprojettest.dto.DresseurDto;
import org.example.pokemonprojettest.service.DresseurNotFoundException;
import org.example.pokemonprojettest.service.DresseurService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(DresseurController.class)
class DresseurControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private DresseurService service;

    @Test
    void should_returnDresseurDto_when_idExists() throws Exception {
        // Arrange
        long id = 101L;
        when(service.getDresseur(id)).thenReturn(new DresseurDto("Sacha", 2, 10));

        // Act
        ResultActions result = mockMvc.perform(get("/dresseurs/{id}", id));

        // Assert
        result.andExpect(status().isOk())
                .andExpect(jsonPath("$.nomDresseur").value("Sacha"))
                .andExpect(jsonPath("$.nombrePokemons").value(2))
                .andExpect(jsonPath("$.niveauDresseur").value(10));
        verify(service).getDresseur(id);
    }

    @Test
    void should_return201_when_dresseurDoesNotExist() throws Exception {
        // Arrange
        long id = 999L;
        when(service.getDresseur(id)).thenThrow(new DresseurNotFoundException(id));

        // Act
        ResultActions result = mockMvc.perform(get("/dresseurs/{id}", id));

        // Assert
        result.andExpect(status().isCreated());
        verify(service).getDresseur(id);
    }
}