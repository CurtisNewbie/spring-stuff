package com.curtisnewbie.model;

import com.curtisnewbie.model.Ingredient.Type;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

@Relation(itemRelation = "ingredient", collectionRelation = "ingredients")
public class IngredientModel extends RepresentationModel<IngredientModel> {

    private final String id;
    private final String name;
    private final Type type;

    public IngredientModel(Ingredient ingredient) {
        this.id = ingredient.getId();
        this.name = ingredient.getName();
        this.type = ingredient.getType();
    }

    /**
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @return the type
     */
    public Type getType() {
        return type;
    }
}
