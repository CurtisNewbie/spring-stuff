package com.curtisnewbie.controllers;

import java.util.List;
import com.curtisnewbie.model.Ingredient;
import com.curtisnewbie.model.IngredientRepository;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/ingredients", produces = MediaType.APPLICATION_JSON_VALUE)
public class IngredientController {

    private IngredientRepository ingreRepo;

    public IngredientController(IngredientRepository ingreRepo) {
        this.ingreRepo = ingreRepo;
    }

    @GetMapping
    ResponseEntity<List<Ingredient>> getIngredients() {
        return ResponseEntity.ok(ingreRepo.findAll());
    }
}
