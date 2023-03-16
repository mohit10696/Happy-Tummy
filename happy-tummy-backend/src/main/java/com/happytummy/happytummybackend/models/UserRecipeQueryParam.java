package com.happytummy.happytummybackend.models;
import org.springframework.beans.factory.annotation.Autowired;

public class UserRecipeQueryParam {
    @Autowired
    Recipe recipe;
    @Autowired
    Ingredient ingredient;
    @Autowired
    Tag tag;
    @Autowired
    Nutrition nutrition;
    @Autowired
    Step steps;

    public Recipe getRecipe() {
        return recipe;
    }

    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }

    public Ingredient getIngredient() {
        return ingredient;
    }

    public void setIngredient(Ingredient ingredient) {
        this.ingredient = ingredient;
    }

    public Tag getTag() {
        return tag;
    }

    public void setTag(Tag tag) {
        this.tag = tag;
    }

    public Nutrition getNutrition() {
        return nutrition;
    }

    public void setNutrition(Nutrition nutrition) {
        this.nutrition = nutrition;
    }

    public Step getSteps() {
        return steps;
    }

    public void setSteps(Step steps) {
        this.steps = steps;
    }
}

