package com.happytummy.happytummybackend.models;
import org.springframework.beans.factory.annotation.Autowired;

public class UserRecipeQueryParam {

    Recipe recipe;

    Ingredient ingredient[];

    Tag tag[];

    Nutrition nutritions[];

    Step steps[];

    public Recipe getRecipe() {
        return recipe;
    }

    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }

    public Ingredient[] getIngredient() {
        return ingredient;
    }

    public Tag[] getTag() {
        return tag;
    }

    public Nutrition[] getNutrition() {
        return nutritions;
    }

    public Step[] getSteps() {
        return steps;
    }

}

