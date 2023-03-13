package com.happytummy.happytummybackend.services;

import com.happytummy.happytummybackend.models.Ingredient;
import com.happytummy.happytummybackend.models.Recipe;
import com.happytummy.happytummybackend.models.RecipeQueryParam;

import java.util.List;

public interface IngredientService {
    List<String> getIngredients(RecipeQueryParam queryParam);
}
