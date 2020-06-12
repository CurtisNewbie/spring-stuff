package com.curtisnewbie.model;

import java.util.List;
import org.springframework.data.repository.CrudRepository;

public interface IngredientRepository extends CrudRepository<Ingredient, String> {

    List<Ingredient> findAll();
}
