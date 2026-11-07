package org.example.pokemonprojettest.repository;

import org.example.pokemonprojettest.entity.Dresseur;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@TestPropertySource(properties = "spring.sql.init.mode=never")
@Sql("/data.sql")
class DresseurRepositoryTest {

    @Autowired
    private DresseurRepository repository;

    @Test
    void should_returnDresseurAndPokemons_when_idExists() {
        // Arrange
        long id = 101L;

        // Act
        var result = repository.findById(id);

        // Assert
        assertThat(result).isPresent();
        Dresseur dresseur = result.orElseThrow();
        assertThat(dresseur.getNom()).isEqualTo("Sacha");
        assertThat(dresseur.getRegion()).isEqualTo("Kanto");
        assertThat(dresseur.getPokemons()).extracting("nom")
                .containsExactlyInAnyOrder("Pikachu", "Salameche");
    }

    @Test
    void should_returnEmpty_when_idDoesNotExist() {
        // Arrange
        long id = 999L;

        // Act
        var result = repository.findById(id);

        // Assert
        assertThat(result).isEmpty();
    }

    @Test
    void should_persistDresseur_when_saved() {
        // Arrange
        Dresseur dresseur = new Dresseur();
        dresseur.setNom("Ondine");
        dresseur.setRegion("Kanto");

        // Act
        Dresseur saved = repository.saveAndFlush(dresseur);

        // Assert
        assertThat(saved.getId()).isNotNull();
        assertThat(repository.findById(saved.getId())).get()
                .extracting(Dresseur::getNom, Dresseur::getRegion)
                .containsExactly("Ondine", "Kanto");
    }

    @Test
    void should_removeDresseur_when_deleted() {
        // Arrange
        Dresseur dresseur = repository.findById(102L).orElseThrow();

        // Act
        repository.delete(dresseur);
        repository.flush();

        // Assert
        assertThat(repository.findById(102L)).isEmpty();
    }
}