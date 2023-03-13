package com.happytummy.happytummybackend.repositories;

import com.happytummy.happytummybackend.models.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IngredientRepository extends JpaRepository<Ingredient, Integer> {
    List<Ingredient> findByRecipeId(String recipeId);

    @Query("SELECT DISTINCT e.plain_ingredient FROM Ingredient e")
    List<String> findDistinctByIngredientName();
}