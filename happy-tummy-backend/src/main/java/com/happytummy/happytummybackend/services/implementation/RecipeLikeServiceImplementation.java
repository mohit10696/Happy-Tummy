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

    public List<RecipeLike> getAllLikesForRecipe(Long recipeId) {
        return recipeLikeRepository.findByRecipeId(recipeId);
    }

    public Long getNumLikesForUser(Long userId) {
        return recipeLikeRepository.countByUserId(userId);
    }

    public Long getNumLikesForRecipe(Long recipeId) {
        return recipeLikeRepository.countByRecipeId(recipeId);
    }

    public RecipeLike addLike(Long userId, Integer recipeId) {
        RecipeLike recipeLike = new RecipeLike();
        recipeLike.setUserId(userId);
        recipeLike.setRecipe(recipeRepository.getOne(recipeId));
        return recipeLikeRepository.save(recipeLike);
    }

    public Boolean removeLike(Long userId, Long recipeId) {
        RecipeLike recipeLike = recipeLikeRepository.findByUserIdAndRecipeId(userId, recipeId);
        if (recipeLike != null) {
            recipeLikeRepository.delete(recipeLike);
            return true;
        }
        return false;
    }
}

