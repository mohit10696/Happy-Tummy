package com.happytummy.happytummybackend.repositories;

import com.happytummy.happytummybackend.models.Recipe;
import org.springframework.stereotype.Repository;

import java.util.List;
public interface RecipeRepositoryCustom {
    List<Recipe> findByLimit(int limit, int pageIndex);
    List<Recipe> findByTagName(String tagName,int limit,int pageIndex);
    List<Recipe> findByIngredientName(String[] ingredientNames,String[] dietaryCategory,int limit, int pageIndex);
    List<Recipe> findByCombinedIngredientName(String[] ingredientNames,String[] dietaryCategory,int limit, int pageIndex);

    List<Recipe> findBySearch(String search,int limit, int pageIndex);
}
