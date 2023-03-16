package com.happytummy.happytummybackend.services.implementation;

import com.happytummy.happytummybackend.models.*;
import com.happytummy.happytummybackend.repositories.*;
import com.happytummy.happytummybackend.services.RecipeService;
import com.happytummy.happytummybackend.services.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class RecipeServiceImplementation implements RecipeService {

    @Autowired
    RecipeRepository recipeRepository;

    @Autowired
    TagRepository tagRepository;

    @Autowired
    IngredientRepository ingredientRepository;

    @Autowired
    StepRepository stepRepository;

    @Autowired
    NutritionRepository nutritionRepository;

    @Autowired
    ReviewRepository reviewRepository;

    @Autowired
    ReviewService reviewService;
    private UserRecipeQueryParam recipe_det;


    @Override
    public List<Recipe> getRecipes(RecipeQueryParam queryParam) {
        int length = queryParam.getLength() != null ? queryParam.getLength() : 10;
        int pageIndex = queryParam.getPageIndex() != null ? queryParam.getPageIndex() : 0;
        if(queryParam.getTag() != null){
            return recipeRepository.findByTagName(queryParam.getTag(),length,pageIndex);
        }
        else if(queryParam.getIngredients() != null){
            if(queryParam.getIngredients().contains("||")){
                return recipeRepository.findByIngredientName(queryParam.getIngredients().split("\\|\\|"),length,pageIndex);
            }
            if (queryParam.getIngredients().contains("&&")){
                return recipeRepository.findByCombinedIngredientName(queryParam.getIngredients().split("&&"),length,pageIndex);
            }
            return recipeRepository.findByIngredientName(queryParam.getIngredients().split(","),length,pageIndex);
        }
        else if(queryParam.getQ() != null){
            return recipeRepository.findBySearch(queryParam.getQ(),length,pageIndex);
        }
        else{
            return recipeRepository.findByLimit(length,pageIndex);
        }
    }


    @Override
    public Object getRecipeById(String id) {
        Map<String, Object> responseData = new HashMap<>();
        Optional<Recipe> recipeOptional = recipeRepository.findById(Integer.valueOf(id));
        if (recipeOptional.isPresent()) {
            Recipe recipe = recipeOptional.get();
            responseData.put("recipe", recipe);
            responseData.put("tags", tagRepository.findByRecipeId(id).toArray());
            responseData.put("ingredients", ingredientRepository.findByRecipeId(id).toArray());
            responseData.put("steps", stepRepository.findByRecipeId(id).toArray());
            responseData.put("nutrition", nutritionRepository.findByRecipeId(id).toArray());
            responseData.put("reviews", reviewService.getReviewByRecipeId(id).toArray());

            return responseData;
        } else {
            return "Recipe not found";
        }
    }

    @Override
    public Object addRecipeByUser(UserRecipeQueryParam recipe_details, String id){
        Recipe recipe=recipe_details.getRecipe();
        if(recipe!=null){
            recipe.setUser_id(Integer.parseInt(id));
            recipeRepository.save(recipe);
        }
        int recipe_id= recipe.getId();
        List<Nutrition> nutritions= List.of(recipe_details.getNutrition());
        List<Step> steps= List.of(recipe_details.getSteps());
        List<Tag> tag= List.of(recipe_details.getTag());
        List<Ingredient> ingredient= List.of(recipe_details.getIngredient());

        if (nutritions!=null) {
            for (Nutrition nutrition1 : nutritions) {
                nutrition1.setRecipeId(String.valueOf(recipe_id));
                nutritionRepository.save(nutrition1);
            }
        }

        if(steps!=null) {
            for (Step step1 : steps) {
                step1.setRecipeId(String.valueOf(recipe_id));
                stepRepository.save(step1);
            }
        }

        if(tag!=null) {
            for (Tag tag1 : tag) {
                tag1.setRecipeId(String.valueOf(recipe_id));
                tagRepository.save(tag1);
            }
        }

        if(ingredient!=null) {
            for (Ingredient ingredient1 : ingredient) {
                ingredient1.setRecipeId(String.valueOf(recipe_id));
                ingredientRepository.save(ingredient1);
            }
        }

        try{
            return "recipe successfully added";
        }
        catch (Exception e){
            return new Response("Error occured while adding the recipe",e.getMessage());
        }
    }


}

