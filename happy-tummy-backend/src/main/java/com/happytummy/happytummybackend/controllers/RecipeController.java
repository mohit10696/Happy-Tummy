package com.happytummy.happytummybackend.controllers;

import com.happytummy.happytummybackend.models.Recipe;
import com.happytummy.happytummybackend.models.RecipeQueryParam;
import com.happytummy.happytummybackend.models.Response;
import com.happytummy.happytummybackend.services.RecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/recipes")
@CrossOrigin()
public class RecipeController {

    @Autowired
    RecipeService recipeService;

    @GetMapping()
    public Object getAllRecipes(RecipeQueryParam recipeQueryParam) {
        try {
            List<Recipe> recipes = recipeService.getRecipes(recipeQueryParam);
            return new Response("success", recipes);
        } catch (Exception e) {
            return new Response("error", e.getMessage());
        }
    }


    @GetMapping("/{id}")
    public Object getSingleRecipe(@PathVariable String id) {
        try {
            Object recipe = recipeService.getRecipeById(id);
            return new Response("success", recipe);
        } catch (Exception e) {
            return new Response("error", e.getMessage());
        }
    }

}
