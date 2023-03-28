package com.happytummy.happytummybackend.controllers;
import com.happytummy.happytummybackend.controllers.IngredientController;
import com.happytummy.happytummybackend.models.Ingredient;
import com.happytummy.happytummybackend.models.RecipeQueryParam;
import com.happytummy.happytummybackend.models.Response;
import com.happytummy.happytummybackend.services.IngredientService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import com.google.gson.Gson;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import static org.junit.jupiter.api.Assertions.assertEquals;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//@ContextConfiguration(classes = {IngredientController.class})
//@ExtendWith(SpringExtension.class)
//@WebMvcTest(IngredientController.class)
//@Import(IngredientController.class)
//@AutoConfigureMockMvc(addFilters = false)
//@TestInstance(TestInstance.Lifecycle.PER_CLASS)

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

//    public static Stream<Arguments> testGetIngredientsDatasource() {
//
//        Ingredient ingredient = new Ingredient("1", "banana");
//
//        return Stream.of(
////                Arguments.of(
////                        status().isOk(),
////                        new Gson().toJson(emptyList()),
////                        emptyList()
////                )
////                ,
////                Arguments.of(
////                        status().isOk(),
////                        new Gson().toJson(emptyList()),
////                        new Response("success",emptyList())
////                ),
//                Arguments.of(
//                        status().isOk(),
//                        new Gson().toJson(new Response("success", singletonList(ingredient))),
//                        singletonList(ingredient)
//                )
//        );
//    }

//    @ParameterizedTest(name = "{index}: testGetIngredients()")
//    @MethodSource("testGetIngredientsDatasource")


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

//    @Test
//    public void testGetIngredientsNull() throws Exception {
//        when(ingredientServiceMock.getIngredients(any(RecipeQueryParam.class))).thenReturn(null);
//        String response = new Gson().toJson(new Response("success", null));
//        assertEquals(response, new Gson().toJson(ingredientControllerMock.getAllIngredients(new RecipeQueryParam())));
//    }

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
