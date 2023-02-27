package com.happytummy.happytummybackend.controllers;


import com.happytummy.happytummybackend.models.Ingredient;
import com.happytummy.happytummybackend.models.RecipeQueryParam;
import com.happytummy.happytummybackend.models.Response;
import com.happytummy.happytummybackend.services.IngredientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/ingredients")
public class IngredientController {
    @Autowired
    IngredientService ingredientService;

    @GetMapping()
    public Object getAllIngredients(RecipeQueryParam recipeQueryParam){
        try {
            List<Ingredient> ingredient = ingredientService.getIngredients(recipeQueryParam);
            return new Response("success", ingredient);
        } catch (Exception e) {
            return new Response("error", e.getMessage());
        }
    }
}
