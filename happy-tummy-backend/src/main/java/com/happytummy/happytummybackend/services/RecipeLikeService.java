package com.happytummy.happytummybackend.services;

import com.happytummy.happytummybackend.models.RecipeLike;

import java.util.List;

/**
 * Interface for RecipeLikeService.
 */
public interface RecipeLikeService {

    /**
     * Get all RecipeLikes for a given recipe ID.
     *
     * @param recipeId ID of the recipe.
     * @return List of RecipeLikes for the given recipe ID.
     */
    Long getNumLikesForRecipe(Long recipeId);

    /**
     * Get the number of Likes for a given user ID.
     *
     * @param userId ID of the user.
     * @return Number of Likes for the given user ID.
     */
    Long getNumLikesForUser(Long userId);

    /**
     * Get the number of Likes for a given recipe ID.
     *
     * @param recipeId ID of the recipe.
     * @return Number of Likes for the given recipe ID.
     */
    Boolean removeLike(Long userId, Long recipeId);

    /**
     * Add a RecipeLike for a given user ID and recipe ID.
     *
     * @param userId ID of the user.
     * @param recipeId ID of the recipe.
     * @return RecipeLike object representing the added like.
     */
    RecipeLike addLike(Long userId, Integer recipeId);

    /**
     * Get all RecipeLikes for a given recipe ID.
     *
     * @param recipeId ID of the recipe.
     * @return List of RecipeLikes for the given recipe ID.
     */
    List<RecipeLike> getAllLikesForRecipe(Long recipeId);


}
