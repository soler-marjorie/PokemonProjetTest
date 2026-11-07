package org.example.pokemonprojettest.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;
import org.example.pokemonprojettest.dto.DresseurDto;
import org.example.pokemonprojettest.entity.Dresseur;
import org.example.pokemonprojettest.entity.Pokemon;
import org.example.pokemonprojettest.repository.DresseurRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class DresseurServiceTest {
    @Mock
    private DresseurRepository repository;
    @InjectMocks
    private DresseurService service;

    @Test
    void should_sum_levels_when_dresseur_has_pokemon_without_bonus() {
        // Arrange
        Dresseur dresseur = dresseur(pokemon("Feu", 10), pokemon("Eau", 20));
        when(repository.findById(1L)).thenReturn(Optional.of(dresseur));

        // Act
        int niveau = service.calculerNiveauDresseur(1L);

        // Assert
        assertEquals(30, niveau);
    }

    @Test
    void should_return_one_when_dresseur_has_no_pokemon() {
        // Arrange
        when(repository.findById(1L)).thenReturn(Optional.of(dresseur()));

        // Act
        int niveau = service.calculerNiveauDresseur(1L);

        // Assert
        assertEquals(1, niveau);
    }

    @Test
    void should_add_diversity_bonus_when_dresseur_has_three_distinct_types() {
        // Arrange
        when(repository.findById(1L)).thenReturn(Optional.of(dresseur(
                pokemon("Feu", 10), pokemon("Eau", 20), pokemon("Électrik", 30))));

        // Act
        int niveau = service.calculerNiveauDresseur(1L);

        // Assert
        assertEquals(80, niveau);
    }

    @Test
    void should_combine_bonuses_when_multiple_pokemon_are_elite() {
        // Arrange
        when(repository.findById(1L)).thenReturn(Optional.of(dresseur(
                pokemon("Feu", 49), pokemon("Eau", 50), pokemon("Électrik", 60))));

        // Act
        int niveau = service.calculerNiveauDresseur(1L);

        // Assert
        assertEquals(199, niveau);
    }

    @Test
    void should_return_dto_when_dresseur_exists() {
        // Arrange
        Dresseur dresseur = dresseur(pokemon("Feu", 50));
        dresseur.setNom("Sacha");
        when(repository.findById(1L)).thenReturn(Optional.of(dresseur));

        // Act
        DresseurDto result = service.getDresseur(1L);

        // Assert
        assertEquals(new DresseurDto("Sacha", 1, 60), result);
    }

    @Test
    void should_throw_exception_when_dresseur_does_not_exist() {
        // Arrange
        when(repository.findById(99L)).thenReturn(Optional.empty());

        // Act
        DresseurNotFoundException exception = assertThrows(DresseurNotFoundException.class,
                () -> service.calculerNiveauDresseur(99L));

        // Assert
        assertEquals("Dresseur introuvable : 99", exception.getMessage());
    }

    private Dresseur dresseur(Pokemon... pokemons) {
        Dresseur dresseur = new Dresseur();
        dresseur.setPokemons(List.of(pokemons));
        return dresseur;
    }

    private Pokemon pokemon(String type, int niveau) {
        Pokemon pokemon = new Pokemon();
        pokemon.setType(type);
        pokemon.setNiveau(niveau);
        return pokemon;
    }
}