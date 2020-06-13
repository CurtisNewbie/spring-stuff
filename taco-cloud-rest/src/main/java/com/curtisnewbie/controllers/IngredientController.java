package com.curtisnewbie.controllers;

import java.util.Optional;
import javax.validation.Valid;
import com.curtisnewbie.model.Ingredient;
import com.curtisnewbie.model.IngredientModel;
import com.curtisnewbie.model.IngredientModelAssembler;
import com.curtisnewbie.model.IngredientRepository;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping(path = "/ingredients", consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE)
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

    @PutMapping
    public IngredientModel updateIngredient(@Valid @RequestBody Ingredient ingredient) {
        return assembler.toModel(ingreRepo.save(ingredient));
    }

    @PostMapping
    public IngredientModel createIngredient(@Valid @RequestBody Ingredient ingredient) {
        return assembler.toModel(ingreRepo.save(ingredient));
    }

    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    @DeleteMapping("/{ingredientId}")
    public void deleteIngredient(@PathVariable("ingredientId") String ingredientId) {
        ingreRepo.deleteById(ingredientId);
    }
}
