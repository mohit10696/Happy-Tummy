package com.happytummy.happytummybackend.repositories;

import com.happytummy.happytummybackend.models.Nutrition;
import com.happytummy.happytummybackend.models.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NutritionRepository extends JpaRepository<Nutrition, Integer> {
    List<Nutrition> findByRecipeId(String recipeId);
}