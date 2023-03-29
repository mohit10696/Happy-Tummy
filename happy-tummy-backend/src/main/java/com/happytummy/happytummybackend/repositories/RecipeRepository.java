package com.happytummy.happytummybackend.repositories;

import com.happytummy.happytummybackend.models.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface RecipeRepository extends JpaRepository<Recipe, Integer>,RecipeRepositoryCustom{

    List<Recipe> findByUserId(Long userId);

}
