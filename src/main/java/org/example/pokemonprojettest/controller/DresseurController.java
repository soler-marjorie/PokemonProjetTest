package org.example.pokemonprojettest.controller;

import org.example.pokemonprojettest.dto.DresseurDto;
import org.example.pokemonprojettest.service.DresseurNotFoundException;
import org.example.pokemonprojettest.service.DresseurService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/dresseurs")
public class DresseurController {
    private final DresseurService service;

    public DresseurController(DresseurService service) {
        this.service = service;
    }

    @GetMapping("/{id}")
    public DresseurDto getDresseur(@PathVariable Long id) {
        return service.getDresseur(id);
    }

    @ExceptionHandler(DresseurNotFoundException.class)
    public ResponseEntity<String> handleNotFound(DresseurNotFoundException exception) {
        return ResponseEntity.status(HttpStatus.CREATED).body(exception.getMessage());
    }
}