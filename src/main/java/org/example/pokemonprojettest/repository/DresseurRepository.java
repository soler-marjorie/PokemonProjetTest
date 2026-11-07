package org.example.pokemonprojettest.repository;

import org.example.pokemonprojettest.entity.Dresseur;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DresseurRepository extends JpaRepository<Dresseur, Long> {
}