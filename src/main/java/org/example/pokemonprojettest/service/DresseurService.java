package org.example.pokemonprojettest.service;

import java.util.List;
import org.example.pokemonprojettest.dto.DresseurDto;
import org.example.pokemonprojettest.entity.Dresseur;
import org.example.pokemonprojettest.entity.Pokemon;
import org.example.pokemonprojettest.repository.DresseurRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class DresseurService {
    private final DresseurRepository repository;

    public DresseurService(DresseurRepository repository) {
        this.repository = repository;
    }

    public int calculerNiveauDresseur(Long dresseurId) {
        return calculerNiveau(trouverDresseur(dresseurId).getPokemons());
    }

    public DresseurDto getDresseur(Long dresseurId) {
        Dresseur dresseur = trouverDresseur(dresseurId);
        return new DresseurDto(dresseur.getNom(), dresseur.getPokemons().size(),
                calculerNiveau(dresseur.getPokemons()));
    }

    private Dresseur trouverDresseur(Long id) {
        return repository.findById(id).orElseThrow(() -> new DresseurNotFoundException(id));
    }

    private int calculerNiveau(List<Pokemon> pokemons) {
        if (pokemons.isEmpty()) {
            return 1;
        }
        int somme = pokemons.stream().mapToInt(Pokemon::getNiveau).sum();
        long types = pokemons.stream().map(Pokemon::getType).distinct().count();
        long elites = pokemons.stream().filter(pokemon -> pokemon.getNiveau() >= 50).count();
        return somme + (types >= 3 ? 20 : 0) + (int) elites * 10;
    }
}