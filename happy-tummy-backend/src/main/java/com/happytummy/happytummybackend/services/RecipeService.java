package com.happytummy.happytummybackend.services;

import com.happytummy.happytummybackend.models.Recipe;
import com.happytummy.happytummybackend.models.RecipeQueryParam;

import java.util.List;

public interface RecipeService {
    List<Recipe> getRecipes(RecipeQueryParam queryParam);

    Object getRecipeById(String id);
}
