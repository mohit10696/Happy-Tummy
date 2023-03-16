package com.happytummy.happytummybackend.models;

import jakarta.persistence.*;

@Entity
@Table(name = "nutrition")
public class Nutrition {
    @Id
    private int id;
    @Column(name = "recipeId")
    private String recipeId;
    private String nutrition;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRecipeId() {
        return recipeId;
    }

    public void setRecipeId(String recipeId) {
        this.recipeId = recipeId;
    }

    public String getNutrition() {
        return nutrition;
    }

    public void setNutrition(String nutrition) {
        this.nutrition = nutrition;
    }
}
