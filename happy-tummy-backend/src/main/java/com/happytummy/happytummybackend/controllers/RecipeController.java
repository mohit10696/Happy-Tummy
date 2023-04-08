package com.happytummy.happytummybackend.controllers;

import com.happytummy.happytummybackend.models.Recipe;
import com.happytummy.happytummybackend.models.RecipeQueryParam;
import com.happytummy.happytummybackend.models.Response;
import com.happytummy.happytummybackend.models.UserRecipeQueryParam;
import com.happytummy.happytummybackend.services.RecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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

    private boolean isNullOrEmpty(String s) {
        return s == null || s.trim().isEmpty() || s.equals(" ");
    }
    @PostMapping("/{id}")
    public Object addRecipeByUser(@RequestBody UserRecipeQueryParam recipe_details,@PathVariable String id){
        if (isNullOrEmpty(recipe_details.getRecipe().getName())) {
            return new Response("error", "Recipe name is required");
        }

        if (isNullOrEmpty(recipe_details.getRecipe().getPrepTime())) {
            return new Response("error", "Recipe prep time is required");
        }

        if (isNullOrEmpty(recipe_details.getRecipe().getCookTime())) {
            return new Response("error", "Recipe cook time is required");
        }

        if (recipe_details.getSteps().isEmpty()) {
            return new Response("error", "Recipe steps are required");
        }

        if (recipe_details.getIngredients().isEmpty()) {
            return new Response("error", "Recipe ingredients are required");
        }

        return recipeService.addRecipeByUser(recipe_details, id);
    }

    @PostMapping("/upload/{id}")
    public Object uploadRecipeImage(@PathVariable String id,@RequestParam("file") MultipartFile file){
        return recipeService.uploadRecipeImage(id,file);
    }

}
