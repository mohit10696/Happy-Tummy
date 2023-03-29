package com.happytummy.happytummybackend.services;

import com.happytummy.happytummybackend.models.RecipeLike;

import java.util.List;

public interface RecipeLikeService {
    Long getNumLikesForRecipe(Long recipeId);
    Long getNumLikesForUser(Long userId);
    Boolean removeLike(Long userId, Long recipeId);
    RecipeLike addLike(Long userId, Integer recipeId);
    List<RecipeLike> getAllLikesForRecipe(Long recipeId);


}
