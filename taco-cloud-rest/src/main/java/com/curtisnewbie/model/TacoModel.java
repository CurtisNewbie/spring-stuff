package com.curtisnewbie.model;

import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

// A DTO for Taco, but with the ability to manage links
@Relation(itemRelation = "taco", collectionRelation = "tacos")
public class TacoModel extends RepresentationModel<TacoModel> {

    @Autowired
    private IngredientModelAssembler assembler;
    private final String name;
    private final Date createdAt;
    private final List<IngredientModel> ingredients;

    public TacoModel(Taco taco) {
        this.name = taco.getName();
        this.createdAt = taco.getCreatedAt();
        this.ingredients = assembler.toModelsList(taco.getIngredients());
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @return the createdAt
     */
    public Date getCreatedAt() {
        return createdAt;
    }

    /**
     * @return the ingredients
     */
    public List<IngredientModel> getIngredients() {
        return ingredients;
    }
}
