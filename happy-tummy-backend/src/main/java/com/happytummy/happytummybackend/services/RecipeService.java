package com.happytummy.happytummybackend.services;

import com.happytummy.happytummybackend.models.Recipe;
import com.happytummy.happytummybackend.models.RecipeQueryParam;
import com.happytummy.happytummybackend.models.UserRecipeQueryParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface RecipeService {
    List<Recipe> getRecipes(RecipeQueryParam queryParam);

    Object getRecipeById(String id);
    Object addRecipeByUser(UserRecipeQueryParam recipe_details,String id);

    Object uploadRecipeImage(String id, MultipartFile file);
}
