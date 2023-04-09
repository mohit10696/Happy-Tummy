package com.happytummy.happytummybackend.controllers;

import com.google.gson.Gson;
import com.happytummy.happytummybackend.controllers.RecipeController;
import com.happytummy.happytummybackend.models.*;
import com.happytummy.happytummybackend.services.RecipeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
public class RecipeControllerTest {
    @Autowired
    private RecipeController recipeControllerMock;

    RecipeService recipeServiceMock;

    @BeforeEach
    public void setup(){
        recipeServiceMock = mock(RecipeService.class);
        recipeControllerMock.recipeService = recipeServiceMock;
    }
    @Test
    public void objectCreated() throws Exception {
        assertNotNull(recipeControllerMock);
    }

   @Test
   public void testGetRecipes() throws Exception {
       Recipe recipe = new Recipe(1, "Tasty Chicken noodle", 1, "Crispy Chicken noodle", "3", "12", "3", "easy", "https://www.modernhoney.com/wp-content/uploads/2017/01/Thai-Chicken-Noodles-2.jpg", "chicken", "cook well", "lunch", "healthy");
       when(recipeServiceMock.getRecipes(any(RecipeQueryParam.class))).thenReturn(singletonList(recipe));
       Object response = recipeControllerMock.getAllRecipes(new RecipeQueryParam());
       assertNotNull(response);
   }

@Test
    public void testGetRecipesWithEmptyList() throws Exception {
        when(recipeServiceMock.getRecipes(any(RecipeQueryParam.class))).thenReturn(emptyList());
        Object response = recipeControllerMock.getAllRecipes(new RecipeQueryParam());
        assertNotNull(response);
    }

    @Test
    public void testGetRecipesWithNull() throws Exception {
        when(recipeServiceMock.getRecipes(any(RecipeQueryParam.class))).thenReturn(null);
        Object response = recipeControllerMock.getAllRecipes(new RecipeQueryParam());
        assertNotNull(response);
    }

    @Test
    public void testGetRecipesWithException() throws Exception {
        when(recipeServiceMock.getRecipes(any(RecipeQueryParam.class))).thenThrow(new RuntimeException());
        Object response = recipeControllerMock.getAllRecipes(new RecipeQueryParam());
        assertNotNull(response);
    }
    @Test
    public void testGetRecipeById() throws Exception {
        Recipe recipe = new Recipe(1, "Tasty Chicken noodle", 1, "Crispy Chicken noodle", "3", "12", "3", "easy", "https://www.modernhoney.com/wp-content/uploads/2017/01/Thai-Chicken-Noodles-2.jpg", "chicken", "cook well", "lunch", "healthy");
        when(recipeServiceMock.getRecipeById(any(String.class))).thenReturn(recipe);
        Object response = recipeControllerMock.getSingleRecipe("1");
        assertNotNull(response);
    }
    @Test
    public void testGetRecipeByIdWithNull() throws Exception {
        when(recipeServiceMock.getRecipeById(any(String.class))).thenReturn(null);
        Object response = recipeControllerMock.getSingleRecipe("1");
        assertNotNull(response);
    }
    @Test
    public void testGetRecipeByIdWithException() throws Exception {
        when(recipeServiceMock.getRecipeById(any(String.class))).thenThrow(new RuntimeException());
        Object response = recipeControllerMock.getSingleRecipe("1");
        assertNotNull(response);
    }
    @Test
    public void testAddRecipeByUser() throws Exception {
        Recipe recipe = new Recipe(1, "Tasty Chicken noodle", 1, "Crispy Chicken noodle", "3", "12", "3", "easy", "https://www.modernhoney.com/wp-content/uploads/2017/01/Thai-Chicken-Noodles-2.jpg", "chicken", "cook well", "lunch", "healthy");
        List<Ingredient> ingredients = singletonList(new Ingredient("1", "chicken"));
        List<Step> steps = singletonList(new Step("1", "cook well", "1"));
        List<Tag> tags = singletonList(new Tag("1", "healthy"));
        List<Nutrition> nutrition = singletonList(new Nutrition("1", "calories"));
        UserRecipeQueryParam userRecipeQueryParam = new UserRecipeQueryParam(recipe, tags, nutrition, ingredients, steps);
        when(recipeServiceMock.addRecipeByUser(any(UserRecipeQueryParam.class), any(String.class))).thenReturn(recipe);
        Object response = recipeControllerMock.addRecipeByUser(userRecipeQueryParam, "1");
        assertNotNull(response);
    }

    @Test
    public void testAddRecipeByUserWithNameNull() throws Exception{
        Recipe recipe = new Recipe(1, null, 1, null, "3", "12", "3", "easy", "https://www.modernhoney.com/wp-content/uploads/2017/01/Thai-Chicken-Noodles-2.jpg", "chicken", "cook well", "lunch", "healthy");
        List<Ingredient> ingredients = singletonList(new Ingredient("1", "chicken"));
        List<Step> steps = singletonList(new Step("1", "cook well", "1"));
        List<Tag> tags = singletonList(new Tag("1", "healthy"));
        List<Nutrition> nutrition = singletonList(new Nutrition("1", "calories"));
        UserRecipeQueryParam userRecipeQueryParam = new UserRecipeQueryParam(recipe, tags, nutrition, ingredients, steps);
        when(recipeServiceMock.addRecipeByUser(any(UserRecipeQueryParam.class), any(String.class))).thenReturn(recipe);
        Object response = recipeControllerMock.addRecipeByUser(userRecipeQueryParam, "1");
        assertEquals("Recipe name is required", ((Response) response).getData());
    }

    @Test
    public void testAddRecipeByUserWithPrepTimeNull() throws Exception{
        Recipe recipe = new Recipe(1, "Tasty Chicken noodle", 1, "Butter Chicken", "3", "12", null, "easy", "https://www.modernhoney.com/wp-content/uploads/2017/01/Thai-Chicken-Noodles-2.jpg", "chicken", "cook well", "lunch", "healthy");
        List<Ingredient> ingredients = singletonList(new Ingredient("1", "chicken"));
        List<Step> steps = singletonList(new Step("1", "cook well", "1"));
        List<Tag> tags = singletonList(new Tag("1", "healthy"));
        List<Nutrition> nutrition = singletonList(new Nutrition("1", "calories"));
        UserRecipeQueryParam userRecipeQueryParam = new UserRecipeQueryParam(recipe, tags, nutrition, ingredients, steps);
        when(recipeServiceMock.addRecipeByUser(any(UserRecipeQueryParam.class), any(String.class))).thenReturn(recipe);
        Object response = recipeControllerMock.addRecipeByUser(userRecipeQueryParam, "1");
        assertEquals("Recipe prep time is required", ((Response) response).getData());
    }

    @Test
    public void testAddRecipeByUserWithCookTimeNull() throws Exception{
        Recipe recipe = new Recipe(1, "Tasty Chicken noodle", 1, "Butter Chicken", "3", null, "3", "easy", "https://www.modernhoney.com/wp-content/uploads/2017/01/Thai-Chicken-Noodles-2.jpg", "chicken", "cook well", "lunch", "healthy");
        List<Ingredient> ingredients = singletonList(new Ingredient("1", "chicken"));
        List<Step> steps = singletonList(new Step("1", "cook well", "1"));
        List<Tag> tags = singletonList(new Tag("1", "healthy"));
        List<Nutrition> nutrition = singletonList(new Nutrition("1", "calories"));
        UserRecipeQueryParam userRecipeQueryParam = new UserRecipeQueryParam(recipe, tags, nutrition, ingredients, steps);
        when(recipeServiceMock.addRecipeByUser(any(UserRecipeQueryParam.class), any(String.class))).thenReturn(recipe);
        Object response = recipeControllerMock.addRecipeByUser(userRecipeQueryParam, "1");
        assertEquals("Recipe cook time is required", ((Response) response).getData());
    }

    @Test
    public void testAddRecipeByUserWithStepsNull() throws Exception{
        Recipe recipe = new Recipe(1, "Tasty Chicken noodle", 1, "Butter Chicken", "3", "12", "3", "easy", "https://www.modernhoney.com/wp-content/uploads/2017/01/Thai-Chicken-Noodles-2.jpg", "chicken", "cook well", "lunch", "healthy");
        List<Ingredient> ingredients = singletonList(new Ingredient("1", "chicken"));
        List<Step> steps = new ArrayList<>();
        List<Tag> tags = singletonList(new Tag("1", "healthy"));
        List<Nutrition> nutrition = singletonList(new Nutrition("1", "calories"));
        UserRecipeQueryParam userRecipeQueryParam = new UserRecipeQueryParam(recipe, tags, nutrition, ingredients, steps);
        when(recipeServiceMock.addRecipeByUser(any(UserRecipeQueryParam.class), any(String.class))).thenReturn(recipe);
        Object response = recipeControllerMock.addRecipeByUser(userRecipeQueryParam, "1");
        assertEquals("Recipe steps are required", ((Response) response).getData());
    }

    @Test
    public void testAddRecipeByUserWithIngredientsNull() throws Exception{
        Recipe recipe = new Recipe(1, "Tasty Chicken noodle", 1, "Butter Chicken", "3", "12", "3", "easy", "https://www.modernhoney.com/wp-content/uploads/2017/01/Thai-Chicken-Noodles-2.jpg", "chicken", "cook well", "lunch", "healthy");
        List<Ingredient> ingredients = new ArrayList<>();
        List<Step> steps = singletonList(new Step("1", "cook well", "1"));
        List<Tag> tags = singletonList(new Tag("1", "healthy"));
        List<Nutrition> nutrition = singletonList(new Nutrition("1", "calories"));
        UserRecipeQueryParam userRecipeQueryParam = new UserRecipeQueryParam(recipe, tags, nutrition, ingredients, steps);
        when(recipeServiceMock.addRecipeByUser(any(UserRecipeQueryParam.class), any(String.class))).thenReturn(recipe);
        Object response = recipeControllerMock.addRecipeByUser(userRecipeQueryParam, "1");
        assertEquals("Recipe ingredients are required", ((Response) response).getData());
    }

//    @Test
//    public void testAddRecipeByUserWithNull() throws Exception {
//        Recipe recipe = new Recipe(1, "Tasty Chicken noodle", 1, "Crispy Chicken noodle", "3", "12", "3", "easy", "https://www.modernhoney.com/wp-content/uploads/2017/01/Thai-Chicken-Noodles-2.jpg", "chicken", "cook well", "lunch", "healthy");
//        List<Ingredient> ingredients = singletonList(new Ingredient("1", "chicken"));
//        List<Step> steps = singletonList(new Step("1", "cook well", "1"));
//        List<Tag> tags = singletonList(new Tag("1", "healthy"));
//        List<Nutrition> nutrition = singletonList(new Nutrition("1", "calories"));
//        UserRecipeQueryParam userRecipeQueryParam = new UserRecipeQueryParam(recipe, tags, nutrition, ingredients, steps);
//        when(recipeServiceMock.addRecipeByUser(any(UserRecipeQueryParam.class), any(String.class))).thenReturn(null);
//        Object response = recipeControllerMock.addRecipeByUser(userRecipeQueryParam, "1");
//        assertNotNull(response);
//    }

//    @Test
//    public void testAddRecipeByUserWithException() throws Exception {
//        Recipe recipe = new Recipe(1, "Tasty Chicken noodle", 1, "Crispy Chicken noodle", "3", "12", "3", "easy", "https://www.modernhoney.com/wp-content/uploads/2017/01/Thai-Chicken-Noodles-2.jpg", "chicken", "cook well", "lunch", "healthy");
//        List<Ingredient> ingredients = singletonList(new Ingredient("1", "chicken"));
//        List<Step> steps = singletonList(new Step("1", "cook well", "1"));
//        List<Tag> tags = singletonList(new Tag("1", "healthy"));
//        List<Nutrition> nutrition = singletonList(new Nutrition("1", "calories"));
//        UserRecipeQueryParam userRecipeQueryParam = new UserRecipeQueryParam(recipe, tags, nutrition, ingredients, steps);
//        when(recipeServiceMock.addRecipeByUser(any(UserRecipeQueryParam.class), any(String.class))).thenThrow(new RuntimeException());
//        Object response = recipeControllerMock.addRecipeByUser(userRecipeQueryParam, "1");
//        assertNotNull(response);
//    }

    @Test
    public void testUploadRecipeImage() throws Exception {
        MockMultipartFile file = new MockMultipartFile("image.jpg", "image.jpg", "image/jpeg", "image".getBytes());
        when(recipeServiceMock.uploadRecipeImage("1",file)).thenReturn("https://www.modernhoney.com/wp-content/uploads/2017/01/Thai-Chicken-Noodles-2.jpg");
        Object response = recipeControllerMock.uploadRecipeImage("1", file);
        assertEquals("https://www.modernhoney.com/wp-content/uploads/2017/01/Thai-Chicken-Noodles-2.jpg", response);
    }
}
