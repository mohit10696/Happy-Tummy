package com.happytummy.happytummybackend.services.implementation;

import com.happytummy.happytummybackend.models.RecipeQueryParam;
import com.happytummy.happytummybackend.repositories.IngredientRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
public class IngredientServiceImplementationTest {
    @InjectMocks
    private IngredientServiceImplementation ingredientService;

    @Mock
    private IngredientRepository ingredientRepository;

    @Test
    public void testGetIngredients_Success() {
        // Mock the ingredientRepository to return a list of distinct ingredient names
        List<String> distinctIngredientNames = Arrays.asList("Ingredient1", "Ingredient2", "Ingredient3");
        when(ingredientRepository.findDistinctByIngredientName()).thenReturn(distinctIngredientNames);

        // Call the getIngredients() method and capture the result
        List<String> result = ingredientService.getIngredients(new RecipeQueryParam());

        // Assert that the result is not null
        assertNotNull(result);

        // Assert that the result size matches the size of the mocked distinct ingredient names list
        assertEquals(distinctIngredientNames.size(), result.size());

        // Assert that each ingredient name in the result matches the corresponding ingredient name in the mocked list
        for (int i = 0; i < result.size(); i++) {
            assertEquals(distinctIngredientNames.get(i), result.get(i));
        }
    }

    @Test
    public void testGetIngredients_EmptyList() {
        // Mock the ingredientRepository to return an empty list
        when(ingredientRepository.findDistinctByIngredientName()).thenReturn(Collections.emptyList());

        // Call the getIngredients() method and capture the result
        List<String> result = ingredientService.getIngredients(new RecipeQueryParam());

        // Assert that the result is not null
        assertNotNull(result);

        // Assert that the result is an empty list
        assertTrue(result.isEmpty());
    }
}
