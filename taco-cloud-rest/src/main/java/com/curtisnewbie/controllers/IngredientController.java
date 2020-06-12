package com.curtisnewbie.controllers;

import java.util.Optional;
import com.curtisnewbie.model.Ingredient;
import com.curtisnewbie.model.IngredientModel;
import com.curtisnewbie.model.IngredientModelAssembler;
import com.curtisnewbie.model.IngredientRepository;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping(path = "/ingredients", produces = MediaType.APPLICATION_JSON_VALUE)
public class IngredientController {

    private IngredientRepository ingreRepo;
    private IngredientModelAssembler assembler;

    public IngredientController(IngredientRepository ingreRepo,
            IngredientModelAssembler assembler) {
        this.ingreRepo = ingreRepo;
        this.assembler = assembler;
    }

    @GetMapping
    public CollectionModel<IngredientModel> getIngredients() {
        CollectionModel<IngredientModel> models = assembler.toModels(ingreRepo.findAll());
        models.add(WebMvcLinkBuilder
                .linkTo(WebMvcLinkBuilder.methodOn(IngredientController.class).getIngredients())
                .withRel("all"));
        return models;
    }

    @GetMapping("/{ingredientId}")
    public IngredientModel ingredientById(@PathVariable("ingredientId") String id) {
        Optional<Ingredient> opt = ingreRepo.findById(id);
        if (opt.isPresent())
            return assembler.toModel(opt.get());
        else
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Ingredient Not Found");
    }
}
