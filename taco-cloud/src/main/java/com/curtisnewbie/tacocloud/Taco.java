package com.curtisnewbie.tacocloud;

import java.util.List;

public class Taco {

    private String name;
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
