package com.happytummy.happytummybackend.services.implementation;

import com.happytummy.happytummybackend.models.RecipeLike;
import com.happytummy.happytummybackend.repositories.RecipeLikeRepository;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class RecipeLikeServiceImplementationTest {
    @InjectMocks
    private RecipeLikeServiceImplementation recipeLikeService;

    @Mock
    private RecipeLikeRepository recipeLikeRepository;

    @Test
    public void testGetAllLikesForRecipe() {
        Long recipeId = 1L;

        // Create a list of mock recipe likes
        List<RecipeLike> recipeLikes = new ArrayList<>();
        RecipeLike recipeLike1 = new RecipeLike();
        recipeLike1.setId(1L);
        recipeLike1.setRecipe();
        RecipeLike recipeLike2 = new RecipeLike();
        recipeLike2.setId(2L);
        recipeLike2.(recipeId);
        recipeLikes.add(recipeLike1);
        recipeLikes.add(recipeLike2);

        // Mock the recipeLikeRepository to return the list of mock recipe likes
        when(recipeLikeRepository.findByRecipeId(recipeId)).thenReturn(recipeLikes);

        // Call the getAllLikesForRecipe method
        List<RecipeLike> result = recipeLikeService.getAllLikesForRecipe(recipeId);

        // Assert that the result is not null
        assertNotNull(result);

        // Assert that the result has the same size as the mock recipe likes list
        assertEquals(recipeLikes.size(), result.size());

        // Assert that the result contains the same recipe likes as the mock recipe likes list
        assertTrue(result.containsAll(recipeLikes));
    }
}