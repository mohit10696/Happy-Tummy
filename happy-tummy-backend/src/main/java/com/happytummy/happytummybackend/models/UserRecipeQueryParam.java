package com.happytummy.happytummybackend.models;

import java.util.List;

public class UserRecipeQueryParam {
     Recipe recipe;
     List<Tag> tag;
     List<Nutrition> nutrition;
     List<Ingredient> ingredients;
     List<Step> steps;

    public UserRecipeQueryParam() {
    }

    public UserRecipeQueryParam(Recipe recipe, List<Tag> tag, List<Nutrition> nutrition, List<Ingredient> ingredients, List<Step> steps) {
        this.recipe = recipe;
        this.tag = tag;
        this.nutrition = nutrition;
        this.ingredients = ingredients;
        this.steps = steps;
    }

    public Recipe getRecipe() {
        return recipe;
    }

    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }

    public List<Tag> getTag() {
        return tag;
    }

    public void setTag(List<Tag> tag) {
        this.tag = tag;
    }

    public List<Nutrition> getNutrition() {
        return nutrition;
    }

    public void setNutrition(List<Nutrition> nutrition) {
        this.nutrition = nutrition;
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    public List<Step> getSteps() {
        return steps;
    }

    public void setSteps(List<Step> steps) {
        this.steps = steps;
    }


}

