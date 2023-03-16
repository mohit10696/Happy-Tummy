package com.happytummy.happytummybackend.models;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;

import java.util.ArrayList;

public class UserRecipeQueryParam {
@OneToOne
    private Recipe recipe;
    @ManyToOne
    private ArrayList<Tag> tag =new ArrayList<Tag>();
    @ManyToOne
    private ArrayList<Nutrition> nutritions =new ArrayList<Nutrition>();
    @ManyToOne
    private ArrayList<Ingredient> ingredients =new ArrayList<Ingredient>();
    @ManyToOne
    private ArrayList<Step> stepps =new ArrayList<Step>();

    public Recipe getRecipe() {
        return this.recipe;
    }

    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }

    public ArrayList<Ingredient> getIngredient() {
        return this.ingredients;
    }

    public ArrayList<Tag> getTag() {
        return this.tag;
    }

    public ArrayList<Nutrition> getNutrition() {
        return this.nutritions;
    }

    public ArrayList<Step> getSteps() {
        return this.stepps;
    }

}

