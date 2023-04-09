package com.happytummy.happytummybackend.controllers;


import com.happytummy.happytummybackend.models.RecipeQueryParam;
import com.happytummy.happytummybackend.models.Response;
import com.happytummy.happytummybackend.services.IngredientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/ingredients")
@CrossOrigin()
public class IngredientController {
    @Autowired
    private IngredientService ingredientService;

//    public IngredientService getIngredientService() {
//        return ingredientService;
//    }

    public void setIngredientService(IngredientService ingredientService) {
        this.ingredientService = ingredientService;
    }

    /**
     * Retrieves all ingredients based on the provided recipe query parameters.
     *
     * @param recipeQueryParam The recipe query parameters.
     * @return A response object containing the status ("success" or "error") and the list of ingredients.
     */
    @GetMapping()
    public Object getAllIngredients(RecipeQueryParam recipeQueryParam){
        try {
            List<String> ingredient = ingredientService.getIngredients(recipeQueryParam);
            System.out.println("ingredient = " + ingredient.size());
            return new Response("success", ingredient);
        } catch (Exception e) {
            return new Response("error", e.getMessage());
        }
    }
}
