package com.curtisnewbie.tacocloud;

import java.util.List;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class Taco {

    @NotNull
    @Size(min = 1, message = "Nmae must be at least 5 characters long")
    private String name;

    @Size(min = 1, message = "You must select at least one ingredient")
    private List<String> ingredients;

    public Taco() {
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the ingredients
     */
    public List<String> getIngredients() {
        return ingredients;
    }

    /**
     * @param ingredients the ingredients to set
     */
    public void setIngredients(List<String> ingredients) {
        this.ingredients = ingredients;
    }

    @Override
    public String toString() {
        return "Taco [ingredients=" + ingredients + ", name=" + name + "]";
    }
}
