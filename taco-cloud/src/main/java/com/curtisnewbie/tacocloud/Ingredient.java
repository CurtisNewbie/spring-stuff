package com.curtisnewbie.tacocloud;

public class Ingredient {

    private final String id;
    private final String name;
    private final Type type;

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
}
