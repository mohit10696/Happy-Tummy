package com.happytummy.happytummybackend.services;

import com.happytummy.happytummybackend.models.Recipe;

import java.util.List;

public interface RecipeService {
    List<Recipe> getAllRecipes();
}
