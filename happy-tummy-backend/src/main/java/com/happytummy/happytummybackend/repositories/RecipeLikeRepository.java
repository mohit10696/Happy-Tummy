package com.happytummy.happytummybackend.repositories;

import com.happytummy.happytummybackend.models.RecipeLike;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RecipeLikeRepository extends JpaRepository<RecipeLike, Long> {
    List<RecipeLike> findByRecipeId(Long recipeId);
    Long countByRecipeId(Long recipeId);
    RecipeLike findByUserIdAndRecipeId(Long userId, Long recipeId);
    List<RecipeLike> findByUserId(Long userId);
    Long countByUserId(Long userId);
}

