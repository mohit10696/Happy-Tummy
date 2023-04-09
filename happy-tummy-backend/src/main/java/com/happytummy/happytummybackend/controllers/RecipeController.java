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

    /**
     * Get all recipes.
     *
     * @param recipeQueryParam The query parameters for filtering recipes.
     * @return A response object containing the status and list of recipes.
     */
    @GetMapping()
    public Object getAllRecipes(RecipeQueryParam recipeQueryParam) {
        try {
            List<Recipe> recipes = recipeService.getRecipes(recipeQueryParam);
            return new Response("success", recipes);
        } catch (Exception e) {
            return new Response("error", e.getMessage());
        }
    }

    /**
     * Get a single recipe by id.
     *
     * @param id The id of the recipe.
     * @return A response object containing the status and the recipe.
     */
    @GetMapping("/{id}")
    public Object getSingleRecipe(@PathVariable String id) {
        try {
            Object recipe = recipeService.getRecipeById(id);
            return new Response("success", recipe);
        } catch (Exception e) {
            return new Response("error", e.getMessage());
        }
    }

    /**
     * Check if a string is null or empty or contains only whitespaces.
     *
     * @param s The string to be checked.
     * @return true if the string is null or empty or contains only whitespaces, false otherwise.
     */
    private boolean isNullOrEmpty(String s) {
        return s == null || s.trim().isEmpty() || s.equals(" ");
    }

    /**
     * Add a recipe by user.
     *
     * @param recipe_details The recipe details and user id in the request body.
     * @param id             The id of the user.
     * @return A response object containing the status of the operation.
     */
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

    /**
     * Upload recipe image.
     *
     * @param id   The id of the recipe.
     * @param file The image file in the request body.
     * @return A response object containing the status of the operation.
     */
    @PostMapping("/upload/{id}")
    public Object uploadRecipeImage(@PathVariable String id,@RequestParam("file") MultipartFile file){
        return recipeService.uploadRecipeImage(id,file);
    }

}
