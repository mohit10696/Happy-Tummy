package com.happytummy.happytummybackend.services.implementation;

import com.happytummy.happytummybackend.models.RecipeQueryParam;
import com.happytummy.happytummybackend.repositories.IngredientRepository;
import com.happytummy.happytummybackend.services.IngredientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Implementation class for IngredientService interface.
 */
@Service
public class IngredientServiceImplementation implements IngredientService {
    @Autowired
    IngredientRepository ingredientRepository;

    /**
     * Get the list of distinct ingredient names from the repository.
     *
     * @param queryParam RecipeQueryParam object representing query parameters for ingredient retrieval.
     * @return List of distinct ingredient names.
     */
    @Override
    public List<String> getIngredients(RecipeQueryParam queryParam) {
        System.out.println("IngredientServiceImplementation.getIngredients");
        return ingredientRepository.findDistinctByIngredientName();
    }
}
