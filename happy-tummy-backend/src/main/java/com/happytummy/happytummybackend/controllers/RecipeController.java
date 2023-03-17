package com.happytummy.happytummybackend.controllers;

import com.happytummy.happytummybackend.models.Recipe;
import com.happytummy.happytummybackend.models.RecipeQueryParam;
import com.happytummy.happytummybackend.models.Response;
import com.happytummy.happytummybackend.models.UserRecipeQueryParam;
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

    @PostMapping("/{id}")
    public Object addRecipeByUser(@RequestBody UserRecipeQueryParam recipe_details,@PathVariable String id){
        if(recipe_details.getRecipe().getName() == null || recipe_details.getRecipe().getName().equals("") || recipe_details.getRecipe().getName().equals(" ")){
            return new Response("error","Recipe name is required");
        }
        if(recipe_details.getRecipe().getPrepTime() == null || recipe_details.getRecipe().getPrepTime().equals("") || recipe_details.getRecipe().getPrepTime().equals(" ")){
            return new Response("error","Recipe prep time is required");
        }
        if(recipe_details.getRecipe().getCookTime() == null || recipe_details.getRecipe().getCookTime().equals("") || recipe_details.getRecipe().getCookTime().equals(" ")){
            return new Response("error","Recipe cook time is required");
        }
        if(recipe_details.getSteps().size() == 0){
            return new Response("error","Recipe steps are required");
        }
        if(recipe_details.getIngredients().size() == 0){
            return new Response("error","Recipe ingredients are required");
        }

        return recipeService.addRecipeByUser(recipe_details,id);
    }

}
