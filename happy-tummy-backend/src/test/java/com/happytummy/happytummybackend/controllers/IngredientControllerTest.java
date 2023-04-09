package com.happytummy.happytummybackend.controllers;
import com.happytummy.happytummybackend.models.Ingredient;
import com.happytummy.happytummybackend.models.RecipeQueryParam;
import com.happytummy.happytummybackend.models.Response;
import com.happytummy.happytummybackend.services.IngredientService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import com.google.gson.Gson;
import static org.junit.jupiter.api.Assertions.assertEquals;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


@SpringBootTest
public class IngredientControllerTest {

    @Autowired
    private IngredientController ingredientControllerMock;

    IngredientService ingredientServiceMock;

    @BeforeEach
    public void setup(){
        ingredientServiceMock = mock(IngredientService.class);
        ingredientControllerMock.ingredientService = ingredientServiceMock;
    }


    @Test
    public void objectCreated() throws Exception {
        assertNotNull(ingredientControllerMock);
    }

    @Test
    public void testGetIngredients() throws Exception {
        Ingredient ingredient = new Ingredient("1", "banana");
        when(ingredientServiceMock.getIngredients(any(RecipeQueryParam.class))).thenReturn(singletonList(ingredient.getPlain_ingredient()));
        String response = new Gson().toJson(new Response("success", singletonList(ingredient.getPlain_ingredient())));
        assertEquals(response, new Gson().toJson(ingredientControllerMock.getAllIngredients(new RecipeQueryParam())));
    }

    @Test
    public void testGetIngredientsEmpty() throws Exception {
        when(ingredientServiceMock.getIngredients(any(RecipeQueryParam.class))).thenReturn(emptyList());
        String response = new Gson().toJson(new Response("success", emptyList()));
        assertEquals(response, new Gson().toJson(ingredientControllerMock.getAllIngredients(new RecipeQueryParam())));
    }


    @Test
    public void testGetIngredientsException() throws Exception {
        when(ingredientServiceMock.getIngredients(any(RecipeQueryParam.class))).thenThrow(new RuntimeException());
        String response = new Gson().toJson(new Response("error", null));
        assertEquals(response, new Gson().toJson(ingredientControllerMock.getAllIngredients(new RecipeQueryParam())));
    }

    @Test
    public void testGetIngredientsException2() throws Exception {
        when(ingredientServiceMock.getIngredients(any(RecipeQueryParam.class))).thenThrow(new RuntimeException());
        String response = new Gson().toJson(new Response("error", null));
        assertEquals(response, new Gson().toJson(ingredientControllerMock.getAllIngredients(new RecipeQueryParam())));
    }

    @Test
    public void testGetIngredientsException3() throws Exception {
        when(ingredientServiceMock.getIngredients(any(RecipeQueryParam.class))).thenThrow(new RuntimeException());
        String response = new Gson().toJson(new Response("error", null));
        assertEquals(response, new Gson().toJson(ingredientControllerMock.getAllIngredients(new RecipeQueryParam())));
    }

}
