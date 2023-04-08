package com.happytummy.happytummybackend.services.implementation;

import com.happytummy.happytummybackend.CONSTANT;
import com.happytummy.happytummybackend.repositories.*;
import com.happytummy.happytummybackend.services.ReviewService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import com.happytummy.happytummybackend.models.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;;


@SpringBootTest
public class RecipeServiceImplementationTest {

    @InjectMocks
    private RecipeServiceImplementation recipeService;

    @Mock
    private RecipeRepository recipeRepository;

    @Mock
    TagRepository tagRepository;

    @Mock
    IngredientRepository ingredientRepository;

    @Mock
    StepRepository stepRepository;

    @Mock
    NutritionRepository nutritionRepository;

    @Mock
    ReviewRepository reviewRepository;

    @Mock
    ReviewService reviewService;

    @Mock
    RecipeLikeRepository recipeLikeRepository;

    @Mock
    UserRepository userRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }


    @Test
    public void testGetRecipesWithTag() {

        RecipeQueryParam queryParam = new RecipeQueryParam();
        queryParam.setLength(10);
        queryParam.setPageIndex(0);
        queryParam.setTag("tag");

        when(recipeRepository.findByTagName(anyString(), anyInt(), anyInt())).thenReturn(Arrays.asList(new Recipe(), new Recipe()));

        List<Recipe> result = recipeService.getRecipes(queryParam);

        assertNotNull(result);
        assertEquals(2, result.size());

    }

    @Test
    public void testGetRecipesWithIngredientsOr() {

        RecipeQueryParam queryParam = new RecipeQueryParam();
        queryParam.setLength(10);
        queryParam.setPageIndex(0);
        queryParam.setIngredients("ingredient1||ingredient2");

        when(recipeRepository.findByIngredientName(any(String[].class),any(String[].class) ,anyInt(), anyInt())).thenReturn(Arrays.asList(new Recipe(), new Recipe()));

        List<Recipe> result = recipeService.getRecipes(queryParam);

        assertNotNull(result);
        assertEquals(2, result.size());

    }

    @Test
    public void testGetRecipesWithIngredientsAnd() {

        RecipeQueryParam queryParam = new RecipeQueryParam();
        queryParam.setLength(10);
        queryParam.setPageIndex(0);
        queryParam.setIngredients("ingredient1&&ingredient2");

        when(recipeRepository.findByCombinedIngredientName(any(String[].class),any(String[].class), anyInt(), anyInt())).thenReturn(Arrays.asList(new Recipe(), new Recipe()));

        List<Recipe> result = recipeService.getRecipes(queryParam);

        assertNotNull(result);
        assertEquals(2, result.size());

    }

    @Test
    public void testGetRecipesWithIngredients() {

        RecipeQueryParam queryParam = new RecipeQueryParam();
        queryParam.setLength(10);
        queryParam.setPageIndex(0);
        queryParam.setIngredients("ingredient1,ingredient2");

        when(recipeRepository.findByIngredientName(any(String[].class), any(String[].class),anyInt(), anyInt())).thenReturn(Arrays.asList(new Recipe(), new Recipe()));

        List<Recipe> result = recipeService.getRecipes(queryParam);

        assertNotNull(result);
        assertEquals(2, result.size());
    }

    @Test
    public void testGetRecipesWithSearchQuery() {

        RecipeQueryParam queryParam = new RecipeQueryParam();
        queryParam.setLength(10);
        queryParam.setPageIndex(0);
        queryParam.setQ("searchQuery");

        when(recipeRepository.findBySearch(anyString(), anyInt(), anyInt())).thenReturn(Arrays.asList(new Recipe(), new Recipe()));

        List<Recipe> result = recipeService.getRecipes(queryParam);

        assertNotNull(result);
        assertEquals(2, result.size());
    }

    @Test
    public void testGetRecipesWithLimit() {

        RecipeQueryParam queryParam = new RecipeQueryParam();
        queryParam.setLength(10);
        queryParam.setPageIndex(0);

        when(recipeRepository.findByLimit(anyInt(), anyInt())).thenReturn(Arrays.asList(new Recipe(), new Recipe()));

        List<Recipe> result = recipeService.getRecipes(queryParam);

        assertNotNull(result);
        assertEquals(0, result.size());
    }

    @Test
    public void testGetRecipesWithTagAndIngredients() {

        RecipeQueryParam queryParam = new RecipeQueryParam();
        queryParam.setLength(10);
        queryParam.setPageIndex(0);
        queryParam.setTag("tag");
        queryParam.setIngredients("ingredient1||ingredient2");

        when(recipeRepository.findByTagName(eq("tag"), anyInt(), anyInt())).thenReturn(Arrays.asList(new Recipe(), new Recipe()));

        List<Recipe> result = recipeService.getRecipes(queryParam);

        assertNotNull(result);
        assertEquals(2, result.size());
    }

    @Test
    public void testGetRecipesWithTagAndSearchQuery() {

        RecipeQueryParam queryParam = new RecipeQueryParam();
        queryParam.setLength(10);
        queryParam.setPageIndex(0);
        queryParam.setTag("tag");
        queryParam.setQ("searchQuery");

        when(recipeRepository.findByTagName(eq("tag"), anyInt(), anyInt())).thenReturn(Arrays.asList(new Recipe(), new Recipe()));

        List<Recipe> result = recipeService.getRecipes(queryParam);

        assertNotNull(result);
        assertEquals(2, result.size());
    }

    @Test
    public void testGetRecipesWithTagAndLimit() {

        RecipeQueryParam queryParam = new RecipeQueryParam();
        queryParam.setLength(10);
        queryParam.setPageIndex(0);
        queryParam.setTag("tag");

        when(recipeRepository.findByTagName(eq("tag"), anyInt(), anyInt())).thenReturn(Arrays.asList(new Recipe(), new Recipe()));

        List<Recipe> result = recipeService.getRecipes(queryParam);

        assertNotNull(result);
        assertEquals(2, result.size());
    }


    @Test
    public void testAddRecipeByUser_SaveRecipe_Success() {

        UserRecipeQueryParam recipe_details = new UserRecipeQueryParam();
        Recipe recipe = new Recipe();
        String id = "1";

        recipe_details.setRecipe(recipe);
        when(recipeRepository.save(any(Recipe.class))).thenReturn(recipe);

        Object result = recipeService.addRecipeByUser(recipe_details, id);

        assertNotNull(result);
        assertTrue(result instanceof Response);
        Response response = (Response) result;
        assertEquals("success", response.getStatus());
        assertNotNull(response.getData());
    }

    @Test
    public void testAddRecipeByUser_SaveNutrition_Success() {

        UserRecipeQueryParam recipe_details = new UserRecipeQueryParam();
        Recipe recipe = new Recipe();
        String id = "1";

        recipe_details.setRecipe(recipe);
        List<Nutrition> nutritionList = new ArrayList<>();
        Nutrition nutrition1 = new Nutrition();
        nutritionList.add(nutrition1);
        recipe_details.setNutrition(nutritionList);

        when(recipeRepository.save(any(Recipe.class))).thenReturn(recipe);
        when(nutritionRepository.save(any(Nutrition.class))).thenReturn(nutrition1);

        Object result = recipeService.addRecipeByUser(recipe_details, id);

        assertNotNull(result);
        assertTrue(result instanceof Response);
        Response response = (Response) result;
        assertEquals("success", response.getStatus());
        assertNotNull(response.getData());
    }

    @Test
    public void testAddRecipeByUser_SaveIngredient_Success() {

        UserRecipeQueryParam recipe_details = new UserRecipeQueryParam();
        Recipe recipe = new Recipe();
        String id = "1";

        recipe_details.setRecipe(recipe);
        List<Ingredient> ingredientList = new ArrayList<>();
        Ingredient ingredient1 = new Ingredient();
        ingredientList.add(ingredient1);
        recipe_details.setIngredients(ingredientList);

        when(recipeRepository.save(any(Recipe.class))).thenReturn(recipe);
        when(ingredientRepository.save(any(Ingredient.class))).thenReturn(ingredient1);

        Object result = recipeService.addRecipeByUser(recipe_details, id);

        assertNotNull(result);
        assertTrue(result instanceof Response);
        Response response = (Response) result;
        assertEquals("success", response.getStatus());
        assertNotNull(response.getData());
    }

    @Test
    public void testAddRecipeByUser_SaveStep_Success() {

        Recipe recipe = new Recipe();
        String id = "1";

        Step step1 = new Step("1", "Description", "12345");

        UserRecipeQueryParam recipe_details = new UserRecipeQueryParam();
        recipe_details.setRecipe(recipe);
        List<Step> stepList = new ArrayList<>();
        stepList.add(step1);
        recipe_details.setSteps(stepList);

        when(recipeRepository.save(any(Recipe.class))).thenReturn(recipe);
        when(stepRepository.save(any(Step.class))).thenReturn(step1);

        Object result = recipeService.addRecipeByUser(recipe_details, id);

        assertNotNull(result);
        assertTrue(result instanceof Response);

        Response response = (Response) result;
        assertEquals("success", response.getStatus());

        assertNotNull(response.getData());
    }

    @Test
    public void testAddRecipeByUser_SaveTag_Success() {

        Recipe recipe = new Recipe();
        String id = "1";

        Tag tag1 = new Tag("DemoTag", "1");

        UserRecipeQueryParam recipe_details = new UserRecipeQueryParam();
        recipe_details.setRecipe(recipe);
        List<Tag> tagList = new ArrayList<>();
        tagList.add(tag1);
        recipe_details.setTag(tagList);

        when(recipeRepository.save(any(Recipe.class))).thenReturn(recipe);
        when(tagRepository.save(any(Tag.class))).thenReturn(tag1);

        Object result = recipeService.addRecipeByUser(recipe_details, id);

        assertNotNull(result);
        assertTrue(result instanceof Response);

        Response response = (Response) result;
        assertEquals("success", response.getStatus());

        assertNotNull(response.getData());
    }

    @Test
    public void testAddRecipeByUser_AllProvided_Success() {

        UserRecipeQueryParam recipe_details = new UserRecipeQueryParam();
        Recipe recipe = new Recipe();
        List<Nutrition> nutrition = new ArrayList<>();
        nutrition.add(new Nutrition());
        List<Step> steps = new ArrayList<>();
        steps.add(new Step("1", "Description", "1"));
        List<Tag> tags = new ArrayList<>();
        tags.add(new Tag("Tag", "1"));
        List<Ingredient> ingredients = new ArrayList<>();
        ingredients.add(new Ingredient());
        String id = "1";

        recipe_details.setRecipe(recipe);
        recipe_details.setNutrition(nutrition);
        recipe_details.setSteps(steps);
        recipe_details.setTag(tags);
        recipe_details.setIngredients(ingredients);
        when(recipeRepository.save(any(Recipe.class))).thenReturn(recipe);
        when(nutritionRepository.save(any(Nutrition.class))).thenReturn(new Nutrition());
        when(stepRepository.save(any(Step.class))).thenReturn(steps.get(0)); // Modify to return the same Step object that was passed
        when(tagRepository.save(any(Tag.class))).thenReturn(tags.get(0)); // Modify to return the same Tag object that was passed
        when(ingredientRepository.save(any(Ingredient.class))).thenReturn(new Ingredient());

        Object result = recipeService.addRecipeByUser(recipe_details, id);

        assertNotNull(result);
        assertTrue(result instanceof Response);
        Response response = (Response) result;
        assertEquals("success", response.getStatus());
        assertNotNull(response.getData());
    }

    @Test
    public void testAddRecipeByUser_Exception_Error() {

        UserRecipeQueryParam recipe_details = new UserRecipeQueryParam();
        Recipe recipe = new Recipe();
        String id = "1";

        recipe_details.setRecipe(recipe);
        when(recipeRepository.save(any(Recipe.class))).thenReturn(null);

        Object result = recipeService.addRecipeByUser(recipe_details, id);

        assertNotNull(result);
        assertTrue(result instanceof Response);
        Response response = (Response) result;
        assertEquals("error", response.getStatus());
        assertNotNull(response.getData());
    }

//    @Test
//    public void testUploadRecipeImage_Success() throws Exception {
//        String id = "1";
//        MultipartFile file = mock(MultipartFile.class); // Create a mock MultipartFile object for testing
//        byte[] fileBytes = new byte[1]; // Create a byte array for file bytes
//
//        when(file.isEmpty()).thenReturn(false); // Mock the file.isEmpty() method to return false
//        when(file.getBytes()).thenReturn(fileBytes); // Mock the file.getBytes() method to return the file bytes
//        when(file.getOriginalFilename()).thenReturn("image.jpg"); // Mock the file.getOriginalFilename() method to return a file name
//
//        File dir = mock(File.class);
//        when(dir.exists()).thenReturn(false); // Mock the dir.exists() method to return false
//        when(dir.mkdirs()).thenReturn(true); // Mock the dir.mkdirs() method to return true
//
//        File uploadedFile = mock(File.class);
//        when(uploadedFile.getAbsolutePath()).thenReturn("/path/to/recipes_images/1.jpg"); // Mock the uploadedFile.getAbsolutePath() method to return a file path
//
//        BufferedOutputStream stream = mock(BufferedOutputStream.class);
//        doNothing().when(stream).write(any(byte[].class)); // Mock the stream.write() method to do nothing
//        doNothing().when(stream).close(); // Mock the stream.close() method to do nothing
//
//        Recipe recipe = new Recipe();
//        when(recipeRepository.findById(Integer.valueOf(id))).thenReturn(Optional.of(recipe)); // Mock the recipeRepository.findById() method to return the recipe object
//        when(recipeRepository.save(recipe)).thenReturn(recipe); // Mock the recipeRepository.save() method to return the recipe object
//
//        Object result = recipeService.uploadRecipeImage(id, file); // Call the uploadRecipeImage() method on recipeService
//
//        assertNotNull(result);
//        assertTrue(result instanceof Response);
//        Response response = (Response) result;
//        assertEquals("success", response.getStatus()); // Assert that the response status is "success"
//        assertNotNull(response.getData()); // Assert that the response data is not null
//    }

    @Test
    public void testUploadRecipeImage_Error_FileEmpty() throws Exception {
        String id = "1";
        MultipartFile file = mock(MultipartFile.class); // Create a mock MultipartFile object for testing

        when(file.isEmpty()).thenReturn(true); // Mock the file.isEmpty() method to return true

        Object result = recipeService.uploadRecipeImage(id, file); // Call the uploadRecipeImage() method on recipeService

        assertNotNull(result);
        assertTrue(result instanceof Response);
        Response response = (Response) result;
        assertEquals("error", response.getStatus()); // Assert that the response status is "error"
        assertNotNull(response.getData()); // Assert that the response data is not null
        assertTrue(response.getData().toString().contains("file was empty")); // Assert that the response data contains the error message
    }

    @Test
    public void testUploadRecipeImage_Error_Exception() throws Exception {
        String id = "1";
        MultipartFile file = mock(MultipartFile.class); // Create a mock MultipartFile object for testing

        when(file.isEmpty()).thenReturn(false); // Mock the file.isEmpty() method to return false
        when(file.getBytes()).thenThrow(IOException.class); // Mock the file.getBytes() method to throw an IOException

        Object result = recipeService.uploadRecipeImage(id, file); // Call the uploadRecipeImage() method on recipeService

        assertNotNull(result);
        assertTrue(result instanceof Response);
        Response response = (Response) result;
        assertEquals("error", response.getStatus()); // Assert that the response status is "error"
        assertNull(response.getData()); // Assert that the response data is null
    }

    @Test
    public void testGetRecipeById_Success() {
        String id = "1";
        Recipe recipe = new Recipe();
        recipe.setId(Integer.valueOf(id));
        recipe.setUser_id(Integer.valueOf(id));
        List<Tag> tags = Arrays.asList(new Tag("Tag1", "1"), new Tag("Tag2","2"));
        List<Ingredient> ingredients = Arrays.asList(new Ingredient(), new Ingredient());
        List<Step> steps = Arrays.asList(new Step("1","Description1","1"), new Step("2","Description2","2"));
        List<Nutrition> nutrition = Arrays.asList(new Nutrition(), new Nutrition());
        List<Object> reviews = Arrays.asList(new Review(), new Review());
        List<RecipeLike> likes = Arrays.asList(new RecipeLike(), new RecipeLike());

        when(recipeRepository.findById(Integer.valueOf(id))).thenReturn(Optional.of(recipe)); // Mock the recipeRepository.findById() method to return the recipe object
        when(tagRepository.findByRecipeId(id)).thenReturn(tags); // Mock the tagRepository.findByRecipeId() method to return the tags list
        when(ingredientRepository.findByRecipeId(id)).thenReturn(ingredients); // Mock the ingredientRepository.findByRecipeId() method to return the ingredients list
        when(stepRepository.findByRecipeId(id)).thenReturn(steps); // Mock the stepRepository.findByRecipeId() method to return the steps list
        when(nutritionRepository.findByRecipeId(id)).thenReturn(nutrition); // Mock the nutritionRepository.findByRecipeId() method to return the nutrition list
        when(reviewService.getReviewByRecipeId(id)).thenReturn(reviews); // Mock the reviewService.getReviewByRecipeId() method to return the reviews list
        when(recipeLikeRepository.findByRecipeId(Long.valueOf(id))).thenReturn(likes); // Mock the recipeLikeRepository.countByRecipeId() method to return 2
        when(userRepository.findById(Long.valueOf(recipe.getUserId()))).thenReturn(Optional.of(new User())); // Mock the userRepository.findById() method to return a user object
        Object result = recipeService.getRecipeById(id); // Call the getRecipeById() method on recipeService

        assertNotNull(result);
        assertTrue(result instanceof Map);
        Map<String, Object> responseData = (Map<String, Object>) result;
        assertEquals(8, responseData.size()); // Assert that the response data contains 6 entries
        assertNotNull(responseData.get("recipe")); // Assert that the "recipe" entry is not null
        assertNotNull(responseData.get("tags")); // Assert that the "tags" entry is not null
        assertNotNull(responseData.get("ingredients")); // Assert that the "ingredients" entry is not null
        assertNotNull(responseData.get("steps")); // Assert that the "steps" entry is not null
        assertNotNull(responseData.get("nutrition")); // Assert that the "nutrition" entry is not null
        assertNotNull(responseData.get("reviews")); // Assert that the "reviews" entry is not null
        assertNotNull(responseData.get("likes")); // Assert that the "likes" entry is not null
        assertNotNull(responseData.get("user")); // Assert that the "user" entry is not null
    }

    @Test
    public void testGetRecipeById_RecipeNotFound() {
        String id = "1";
        when(recipeRepository.findById(Integer.valueOf(id))).thenReturn(Optional.empty()); // Mock the recipeRepository.findById() method to return Optional.empty()

        Object result = recipeService.getRecipeById(id); // Call the getRecipeById() method on recipeService

        assertEquals("Recipe not found", result); // Assert that the result is the expected error message
    }

}

