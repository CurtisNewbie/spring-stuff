package com.curtisnewbie.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Entity
public class Ingredient {

    @Id
    @NotNull
    private String id;

    @NotNull
    private String name;

    @NotNull
    private Type type;

    Ingredient() {
    }

    Ingredient(String id, String name, Type t) {
        this.id = id;
        this.name = name;
        this.type = t;
    }

    public static enum Type {
        WRAP, PROTEIN, VEGGIES, CHEESE, SAUSE
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

    @Override
    public String toString() {
        return "Ingredient [id=" + id + ", name=" + name + ", type=" + type + "]";
    }
}
