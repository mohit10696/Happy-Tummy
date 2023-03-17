package com.happytummy.happytummybackend.models;

import jakarta.persistence.*;

@Entity
@Table(name = "ingredient")
public class Ingredient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "recipe_id")
    private String recipeId;

    private String plain_ingredient;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRecipeId() {
        return recipeId;
    }

    public void setRecipeId(String recipe_id) {
        this.recipeId = recipe_id;
    }

    public String getPlain_ingredient() {
        return plain_ingredient;
    }

    public void setPlain_ingredient(String plain_ingredient) {
        this.plain_ingredient = plain_ingredient;
    }
}
