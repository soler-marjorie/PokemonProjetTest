package org.example.pokemonprojettest.service;

public class DresseurNotFoundException extends RuntimeException {
    public DresseurNotFoundException(Long id) {
        super("Dresseur introuvable : " + id);
    }
}