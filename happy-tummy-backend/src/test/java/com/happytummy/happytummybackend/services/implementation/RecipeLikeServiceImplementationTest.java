package com.happytummy.happytummybackend.services.implementation;

import com.happytummy.happytummybackend.models.Recipe;
import com.happytummy.happytummybackend.models.RecipeLike;
import com.happytummy.happytummybackend.repositories.RecipeLikeRepository;
import com.happytummy.happytummybackend.repositories.RecipeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class RecipeLikeServiceImplementationTest {
    @InjectMocks
    private RecipeLikeServiceImplementation recipeLikeService;

    @Mock
    private RecipeLikeRepository recipeLikeRepository;

    @Mock
    private RecipeRepository recipeRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }


    @Test
    public void testGetAllLikesForRecipe() {
        Recipe recipe = new Recipe();
        recipe.setId(1);
        // Create a list of mock recipe likes
        List<RecipeLike> recipeLikes = new ArrayList<>();
        RecipeLike recipeLike1 = new RecipeLike();
        recipeLike1.setId(1L);
        recipeLike1.setRecipe(recipe);
        RecipeLike recipeLike2 = new RecipeLike();
        recipeLike2.setId(2L);
        recipeLike2.setRecipe(recipe);
        recipeLikes.add(recipeLike1);
        recipeLikes.add(recipeLike2);

        // Mock the recipeLikeRepository to return the list of mock recipe likes
        when(recipeLikeRepository.findByRecipeId(1L)).thenReturn(recipeLikes);

        // Call the getAllLikesForRecipe method
        List<RecipeLike> result = recipeLikeService.getAllLikesForRecipe(1L);

        // Assert that the result is not null
        assertNotNull(result);

        // Assert that the result has the same size as the mock recipe likes list
        assertEquals(recipeLikes.size(), result.size());

        // Assert that the result contains the same recipe likes as the mock recipe likes list
        assertTrue(result.containsAll(recipeLikes));

    }

    @Test
    public void testGetAllLikesForRecipe_NoLikesFound() {
        // Mock the recipeLikeRepository to return an empty list
        when(recipeLikeRepository.findByRecipeId(1L)).thenReturn(new ArrayList<>());

        // Call the getAllLikesForRecipe method
        List<RecipeLike> result = recipeLikeService.getAllLikesForRecipe(1L);

        // Assert that the result is not null
        assertNotNull(result);

        // Assert that the result is an empty list
        assertTrue(result.isEmpty());
    }

    @Test
    public void testGetNumLikesForUser() {
        // Mock the recipeLikeRepository to return 2
        when(recipeLikeRepository.countByUserId(1L)).thenReturn(2L);

        // Call the getNumLikesForUser method
        Long result = recipeLikeService.getNumLikesForUser(1L);

        // Assert that the result is not null
        assertNotNull(result);

        // Assert that the result is 2
        assertEquals(2L, result);
    }

    @Test
    public void testGetNumLikesForRecipe() {
        // Mock the recipeLikeRepository to return 2
        when(recipeLikeRepository.countByRecipeId(1L)).thenReturn(2L);

        // Call the getNumLikesForRecipe method
        Long result = recipeLikeService.getNumLikesForRecipe(1L);

        // Assert that the result is not null
        assertNotNull(result);

        // Assert that the result is 2
        assertEquals(2L, result);
    }

    @Test
    public void testAddLike() {
        // Create a mock recipe
        Recipe recipe = new Recipe();
        recipe.setId(1);
        when(recipeRepository.getOne(1)).thenReturn(recipe);

        // Create a mock recipeLike
        RecipeLike recipeLike = new RecipeLike();
        recipeLike.setId(1L);
        recipeLike.setUserId(1L);
        recipeLike.setRecipe(recipe);
        when(recipeLikeRepository.save(any(RecipeLike.class))).thenReturn(recipeLike);

        // Call the addLike method
        Long userId = 1L;
        Integer recipeId = 1;
        RecipeLike result = recipeLikeService.addLike(userId, recipeId);

        // Assert that the result is not null
        assertNotNull(result);

        // Assert that the result has the same user ID as the input user ID
        assertEquals(userId, result.getUserId());

        // Assert that the result has the same recipe as the mocked recipe
        assertEquals(recipe, result.getRecipe());
    }

    @Test
    public void testRemoveLike() {
        // Create a mock recipe
        Recipe recipe = new Recipe();
        recipe.setId(1);

        // Create a mock recipeLike
        RecipeLike recipeLike = new RecipeLike();
        recipeLike.setId(1L);
        recipeLike.setUserId(1L);
        recipeLike.setRecipe(recipe);

        // Set up the recipeLikeRepository to return the mock RecipeLike object
        when(recipeLikeRepository.findByUserIdAndRecipeId(1L, 1L)).thenReturn(recipeLike);

        // Call the removeLike method
        Long userId = 1L;
        Long recipeId = 1L;
        Boolean result = recipeLikeService.removeLike(userId, recipeId);

        // Assert that the result is true, indicating that the like was removed
        assertTrue(result);

    }

    @Test
    public void testRemoveLike_LikeNotFound() {
        // Set up the recipeLikeRepository to return null
        when(recipeLikeRepository.findByUserIdAndRecipeId(1L, 1L)).thenReturn(null);

        // Call the removeLike method
        Long userId = 1L;
        Long recipeId = 1L;
        Boolean result = recipeLikeService.removeLike(userId, recipeId);

        // Assert that the result is false, indicating that the like was not removed
        assertFalse(result);
    }

}