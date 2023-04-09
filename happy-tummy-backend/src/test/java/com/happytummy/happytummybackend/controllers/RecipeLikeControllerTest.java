package com.happytummy.happytummybackend.controllers;

import com.happytummy.happytummybackend.models.Recipe;
import com.happytummy.happytummybackend.models.RecipeLike;
import com.happytummy.happytummybackend.models.Response;
import com.happytummy.happytummybackend.services.RecipeLikeService;
import com.happytummy.happytummybackend.services.ReviewService;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
public class RecipeLikeControllerTest {
    @InjectMocks
    private RecipeLikeController recipeLikeController;

    @Mock
    private RecipeLikeService recipeLikeService;

    @Test
    public void testGetAllLikesForRecipe() {
        // Create a mock recipe ID
        Long recipeId = 1L;

        // Create a list of recipe likes
        List<RecipeLike> recipeLikes = new ArrayList<>();
        RecipeLike recipeLike1 = new RecipeLike();
        recipeLike1.setId(1L);
        Recipe recipe1 = new Recipe();
        recipe1.setId(1); // Set up a mock recipe ID
        recipeLike1.setRecipe(recipe1);
        recipeLikes.add(recipeLike1);
        RecipeLike recipeLike2 = new RecipeLike();
        recipeLike2.setId(2L);
        Recipe recipe2 = new Recipe();
        recipe2.setId(2); // Set up a mock recipe ID
        recipeLike2.setRecipe(recipe2);
        recipeLikes.add(recipeLike2);

        // Set up recipeLikeService to return the list of recipe likes when getAllLikesForRecipe method is called
        when(recipeLikeService.getAllLikesForRecipe(Long.valueOf(recipe1.getId()))).thenReturn(recipeLikes);

        // Call the getAllLikesForRecipe method
        Response result = recipeLikeController.getAllLikesForRecipe(recipeId);

        // Assert that the result is not null
        assertNotNull(result);

        // Assert that the result has a status of "success"
        assertNotNull(result.getStatus());
        assertEquals("success", result.getStatus());

        // Assert that the result contains the list of recipe likes
        assertNotNull(result.getData());
        assertEquals(recipeLikes, result.getData());
    }

    @Test
    public void testGetNumLikesForRecipe() {
        // Create a mock recipe ID
        Long recipeId = 1L;

        // Create a mock number of likes
        Long numLikes = 2L;

        // Set up recipeLikeService to return the number of likes when getNumLikesForRecipe method is called
        when(recipeLikeService.getNumLikesForRecipe(recipeId)).thenReturn(numLikes);

        // Call the getNumLikesForRecipe method
        Response result = recipeLikeController.getNumLikesForRecipe(recipeId);

        // Assert that the result is not null
        assertNotNull(result);

        // Assert that the result has a status of "success"
        assertNotNull(result.getStatus());
        assertEquals("success", result.getStatus());

        // Assert that the result contains the number of likes
        assertNotNull(result.getData());
        assertEquals(numLikes, result.getData());
    }

    @Test
    public void testAddLikeSuccess() {
        // Create a mock recipe ID and user ID
        Integer recipeId = 1;
        String userId = "2";

        // Set up HttpServletRequest to return the user ID as an attribute
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getAttribute("claims")).thenReturn(userId);

        // Set up recipeLikeService to return a mock RecipeLike object when addLike method is called
        RecipeLike recipeLike = new RecipeLike();
        when(recipeLikeService.addLike(Long.parseLong(userId), recipeId)).thenReturn(recipeLike);

        // Call the addLike method
        Object result = recipeLikeController.addLike(request, recipeId);

        // Assert that the result is not null
        assertNotNull(result);
        assertEquals("success", ((Response) result).getStatus());

        assertEquals(recipeLike, ((Response) result).getData());
    }

    @Test
    public void testAddLikeUserNotFound() {
        // Create a mock recipe ID
        Integer recipeId = 1;

        // Set up HttpServletRequest to return null as the user ID attribute
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getAttribute("claims")).thenReturn(null);

        // Call the addLike method
        Object result = recipeLikeController.addLike(request, recipeId);

        // Assert that the result is not null
        assertNotNull(result);

        // Assert that the result has a status of "error"
        assertEquals("error", ((Response) result).getStatus());

        // Assert that the result contains an error message
        assertNotNull(((Response) result).getData());
        assertEquals("User not found", ((Response) result).getData());
    }


    @Test
    public void testRemoveLikeSuccess() {
        // Create a mock recipe ID and user ID

        Long recipeId = 1L;
        String userId = "2";

        // Set up HttpServletRequest to return the user ID as an attribute
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getAttribute("claims")).thenReturn(userId);

        // Set up recipeLikeService to return true when removeLike method is called
        when(recipeLikeService.removeLike(Long.parseLong(userId), recipeId)).thenReturn(true);

        // Call the removeLike method
        Object result = recipeLikeController.removeLike(request, recipeId);

        // Assert that the result is not null
        assertNotNull(result);
        assertEquals("success", ((Response) result).getStatus());
    }

    @Test
    public void testRemoveLikeUserNotFound() {
        // Create a mock recipe ID
        Long recipeId = 1L;

        // Set up HttpServletRequest to return null as the user ID attribute
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getAttribute("claims")).thenReturn(null);

        // Call the removeLike method
        Object result = recipeLikeController.removeLike(request, recipeId);

        // Assert that the result is not null
        assertNotNull(result);

        // Assert that the result has a status of "error"
        assertEquals("error", ((Response) result).getStatus());

        // Assert that the result contains an error message
        assertNotNull(((Response) result).getData());
        assertEquals("User not found", ((Response) result).getData());
    }
}