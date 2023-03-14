package com.happytummy.happytummybackend.services.implementation;

import com.happytummy.happytummybackend.models.Recipe;
import com.happytummy.happytummybackend.models.RecipeQueryParam;
import com.happytummy.happytummybackend.models.Step;
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

}

