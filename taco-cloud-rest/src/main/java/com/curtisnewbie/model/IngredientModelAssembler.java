package com.curtisnewbie.model;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import com.curtisnewbie.controllers.IngredientController;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class IngredientModelAssembler
        extends RepresentationModelAssemblerSupport<Ingredient, IngredientModel> {

    public IngredientModelAssembler() {
        super(IngredientController.class, IngredientModel.class);
    }

    @Override
    public IngredientModel toModel(Ingredient ingredient) {
        IngredientModel model = new IngredientModel(ingredient);
        model.add(linkToSelf(ingredient));
        return model;
    }

    public List<IngredientModel> toModelsList(Collection<Ingredient> ingredients) {
        List<IngredientModel> models = new ArrayList<>();
        ingredients.forEach(ingredient -> {
            models.add(toModel(ingredient));
        });
        return models;
    }

    public CollectionModel<IngredientModel> toModels(Collection<Ingredient> ingredients) {
        return CollectionModel.of(toModelsList(ingredients));
    }

    public static Link linkToSelf(Ingredient ingredient) {
        return linkTo(methodOn(IngredientController.class).ingredientById(ingredient.getId()))
                .withSelfRel();
    }
}
