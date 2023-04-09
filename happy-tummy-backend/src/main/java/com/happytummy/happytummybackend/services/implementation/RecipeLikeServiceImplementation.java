package com.happytummy.happytummybackend.services.implementation;

import com.happytummy.happytummybackend.models.RecipeLike;
import com.happytummy.happytummybackend.repositories.RecipeLikeRepository;
import com.happytummy.happytummybackend.repositories.RecipeRepository;
import com.happytummy.happytummybackend.services.RecipeLikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RecipeLikeServiceImplementation implements RecipeLikeService {

    @Autowired
    RecipeLikeRepository recipeLikeRepository;

    @Autowired
    RecipeRepository recipeRepository;

    /**
     * Get all RecipeLikes for a given recipe ID.
     *
     * @param recipeId ID of the recipe.
     * @return List of RecipeLikes for the given recipe ID.
     */
    public List<RecipeLike> getAllLikesForRecipe(Long recipeId) {
        return recipeLikeRepository.findByRecipeId(recipeId);
    }

    /**
     * Get the number of Likes for a given user ID.
     *
     * @param userId ID of the user.
     * @return Number of Likes for the given user ID.
     */
    public Long getNumLikesForUser(Long userId) {
        return recipeLikeRepository.countByUserId(userId);
    }

    /**
     * Get the number of Likes for a given recipe ID.
     *
     * @param recipeId ID of the recipe.
     * @return Number of Likes for the given recipe ID.
     */
    public Long getNumLikesForRecipe(Long recipeId) {
        return recipeLikeRepository.countByRecipeId(recipeId);
    }

    /**
     * Add a RecipeLike for a given user ID and recipe ID.
     *
     * @param userId ID of the user.
     * @param recipeId ID of the recipe.
     * @return RecipeLike object representing the added like.
     */
    public RecipeLike addLike(Long userId, Integer recipeId) {
        RecipeLike recipeLike = new RecipeLike();
        recipeLike.setUserId(userId);
        recipeLike.setRecipe(recipeRepository.getOne(recipeId));
        return recipeLikeRepository.save(recipeLike);
    }

    /**
     * Remove a RecipeLike for a given user ID and recipe ID.
     *
     * @param userId ID of the user.
     * @param recipeId ID of the recipe.
     * @return Boolean indicating whether the like was removed.
     */
    public Boolean removeLike(Long userId, Long recipeId) {
        RecipeLike recipeLike = recipeLikeRepository.findByUserIdAndRecipeId(userId, recipeId);
        if (recipeLike != null) {
            recipeLikeRepository.delete(recipeLike);
            return true;
        }
        return false;
    }
}

