package com.happytummy.happytummybackend.services.implementation;

import com.happytummy.happytummybackend.models.Ingredient;
import com.happytummy.happytummybackend.models.RecipeQueryParam;
import com.happytummy.happytummybackend.repositories.IngredientRepository;
import com.happytummy.happytummybackend.repositories.RecipeRepository;
import com.happytummy.happytummybackend.services.IngredientService;
import com.happytummy.happytummybackend.services.RecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IngredientServiceImplementation implements IngredientService {
    @Autowired
    IngredientRepository ingredientRepository;

    @Override
    public List<String> getIngredients(RecipeQueryParam queryParam) {
        System.out.println("IngredientServiceImplementation.getIngredients");
        return ingredientRepository.findDistinctByIngredientName();
    }
}
