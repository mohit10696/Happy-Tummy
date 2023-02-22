package com.happytummy.happytummybackend.repositories;

import com.happytummy.happytummybackend.models.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;


public interface RecipeRepository extends JpaRepository<Recipe, Integer>,RecipeRepositoryCustom{
}
