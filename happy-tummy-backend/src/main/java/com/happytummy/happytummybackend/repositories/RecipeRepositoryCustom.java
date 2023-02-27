package com.happytummy.happytummybackend.repositories;

import com.happytummy.happytummybackend.models.Recipe;
import org.springframework.stereotype.Repository;

import java.util.List;
public interface RecipeRepositoryCustom {
    List<Recipe> findByLimit(int limit);
    List<Recipe> findByTagName(String tagName,int limit);
    List<Recipe> findByIngredientName(String[] ingredientNames,int limit);

    List<Recipe> findBySearch(String search,int limit);

}
