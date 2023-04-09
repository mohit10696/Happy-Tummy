package com.happytummy.happytummybackend.services;

import com.happytummy.happytummybackend.models.RecipeQueryParam;

import java.util.List;

/**
 * Interface for IngredientService.
 */
public interface IngredientService {

    /**
     * Get the list of distinct ingredient names from the repository.
     *
     * @param queryParam RecipeQueryParam object representing query parameters for ingredient retrieval.
     * @return List of distinct ingredient names.
     */
    List<String> getIngredients(RecipeQueryParam queryParam);
}
