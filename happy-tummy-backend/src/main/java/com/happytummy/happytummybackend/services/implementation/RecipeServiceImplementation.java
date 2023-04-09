package com.happytummy.happytummybackend.services.implementation;

import com.happytummy.happytummybackend.CONSTANT;
import com.happytummy.happytummybackend.models.*;
import com.happytummy.happytummybackend.repositories.*;
import com.happytummy.happytummybackend.services.RecipeService;
import com.happytummy.happytummybackend.services.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class RecipeServiceImplementation implements RecipeService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    RecipeRepository recipeRepository;

    @Autowired
    TagRepository tagRepository;

    @Autowired
    IngredientRepository ingredientRepository;

    @Autowired
    StepRepository stepRepository;

    @Autowired
    NutritionRepository nutritionRepository;


    @Autowired
    RecipeLikeRepository recipeLikeRepository;


    @Autowired
    ReviewRepository reviewRepository;

    @Autowired
    ReviewService reviewService;
    private UserRecipeQueryParam recipe_det;

    /**
     * Get recipes based on the provided query parameters.
     *
     * @param queryParam The query parameters for filtering recipes.
     * @return A list of recipes that match the query parameters.
     */
    @Override
    public List<Recipe> getRecipes(RecipeQueryParam queryParam) {
        int default_length = 10;
        int length = queryParam.getLength() != null ? queryParam.getLength() : default_length;
        int pageIndex = queryParam.getPageIndex() != null ? queryParam.getPageIndex() : 0;
        String[] mealPreference;
        if (queryParam.getMealPreference() != null) {
            mealPreference = queryParam.getMealPreference().split(",");
        } else {
            mealPreference = new String[0];
        }
        String[] ingredients = queryParam.getIngredients() != null ? queryParam.getIngredients().split(",") : new String[0];
        if (queryParam.getTag() != null) {
            return recipeRepository.findByTagName(queryParam.getTag(), length, pageIndex);
        }
        if (queryParam.getIngredients() != null) {
            String ingredientsStr = queryParam.getIngredients();

            if (ingredientsStr.contains("||")) {
                String[] ingredientGroups = ingredientsStr.split("\\|\\|");
                return recipeRepository.findByIngredientName(ingredientGroups, mealPreference, length, pageIndex);
            }

            if (ingredientsStr.contains("&&")) {
                String[] ingredientNames = ingredientsStr.split("&&");
                return recipeRepository.findByCombinedIngredientName(ingredientNames, mealPreference, length, pageIndex);
            }

            String[] ingredientNames = ingredientsStr.split(",");
            return recipeRepository.findByIngredientName(ingredientNames, mealPreference, length, pageIndex);
        }
        if (queryParam.getQ() != null) {
            return recipeRepository.findBySearch(queryParam.getQ(), length, pageIndex);
        }
        return recipeRepository.findByIngredientName(ingredients,mealPreference, length, pageIndex);
    }

    /**
     * Get recipe details by recipe ID.
     *
     * @param id The ID of the recipe.
     * @return A map containing various details of the recipe, including recipe details, likes, tags, ingredients, steps,
     *         nutrition information, reviews, and user details.
     *         If the recipe with the given ID is found, the map will contain all the details.
     *         If the recipe with the given ID is not found, the method will return a string "Recipe not found".
     */
    @Override
    public Object getRecipeById(String id) {
        Map<String, Object> responseData = new HashMap<>();
        Optional<Recipe> recipeOptional = recipeRepository.findById(Integer.valueOf(id));
        if (recipeOptional.isPresent()) {
            Recipe recipe = recipeOptional.get();
            responseData.put("recipe", recipe);
            responseData.put("likes", recipeLikeRepository.findByRecipeId(Long.valueOf(id)).toArray());
            responseData.put("tags", tagRepository.findByRecipeId(id).toArray());
            responseData.put("ingredients", ingredientRepository.findByRecipeId(id).toArray());
            responseData.put("steps", stepRepository.findByRecipeId(id).toArray());
            responseData.put("nutrition", nutritionRepository.findByRecipeId(id).toArray());
            responseData.put("reviews", reviewService.getReviewByRecipeId(id).toArray());
            responseData.put("user", userRepository.findById(Long.valueOf(recipe.getUserId())).get());
            return responseData;
        } else {
            return "Recipe not found";
        }
    }

    /**
     * Saves a recipe in the recipe repository and sets the user ID.
     *
     * @param recipe The recipe object to be saved.
     * @param id     The ID of the user associated with the recipe.
     * @return The saved recipe object if successful, null otherwise.
     */
    private Recipe saveRecipe(Recipe recipe, String id) {
        if (recipe != null) {
            recipe.setUser_id(Integer.parseInt(id));
            return recipeRepository.save(recipe);
        }
        return null;
    }

    /**
     * Saves nutrition information for a recipe in the nutrition repository.
     *
     * @param recipe_id  The ID of the recipe.
     * @param nutrition  The list of nutrition objects to be saved.
     */
    private void saveNutrition(int recipe_id, List<Nutrition> nutrition) {
        if (nutrition != null && !nutrition.isEmpty()) {
            for (Nutrition nutrition1 : nutrition) {
                nutrition1.setRecipeId(String.valueOf(recipe_id));
                nutritionRepository.save(nutrition1);
            }
        }
    }

    /**
     * Saves steps for a recipe in the step repository.
     *
     * @param recipe_id The ID of the recipe.
     * @param steps     The list of step objects to be saved.
     */
    private void saveSteps(int recipe_id, List<Step> steps) {
        if (steps != null && !steps.isEmpty()) {
            for (Step step : steps) {
                step.setRecipeId(String.valueOf(recipe_id));
                stepRepository.save(step);
            }
        }
    }

    /**
     * Saves tags for a recipe in the tag repository.
     *
     * @param recipe_id The ID of the recipe.
     * @param tags      The list of tag objects to be saved.
     */
    private void saveTags(int recipe_id, List<Tag> tags) {
        if (tags != null && !tags.isEmpty()) {
            for (Tag tag : tags) {
                tag.setRecipeId(String.valueOf(recipe_id));
                tagRepository.save(tag);
            }
        }
    }

    /**
     * Saves ingredients for a recipe in the ingredient repository.
     *
     * @param recipe_id   The ID of the recipe.
     * @param ingredients The list of ingredient objects to be saved.
     */
    private void saveIngredients(int recipe_id, List<Ingredient> ingredients) {
        if (ingredients != null && !ingredients.isEmpty()) {
            for (Ingredient ingredient : ingredients) {
                ingredient.setRecipeId(String.valueOf(recipe_id));
                ingredientRepository.save(ingredient);
            }
        }
    }

    /**
     * Adds a recipe to the recipe repository.
     *
     * @param recipe_details The recipe details to be added.
     * @param id             The ID of the user associated with the recipe.
     * @return A response object containing the status of the operation and the recipe object if successful.
     *         If the operation is successful, the status will be "success" and the recipe object will be returned.
     *         If the operation is unsuccessful, the status will be "error" and the error message will be returned.
     */
    @Override
    public Object addRecipeByUser(UserRecipeQueryParam recipe_details, String id) {
        try {
            Recipe recipe = saveRecipe(recipe_details.getRecipe(), id);
            saveNutrition(recipe.getId(), recipe_details.getNutrition());
            saveSteps(recipe.getId(), recipe_details.getSteps());
            saveTags(recipe.getId(), recipe_details.getTag());
            saveIngredients(recipe.getId(), recipe_details.getIngredients());

            return new Response("success", recipe);
        } catch (Exception e) {
            return new Response("error", e.getMessage());
        }
    }

    /**
     * Uploads a recipe image file and saves it to the file system. Updates the recipe's image URL in the recipe repository.
     *
     * @param id   The ID of the recipe associated with the image.
     * @param file The MultipartFile object representing the image file to be uploaded.
     * @return A response object indicating the result of the upload operation.
     */
    @Override
    public Object uploadRecipeImage(String id, MultipartFile file) {
        if(!file.isEmpty()){
            try {
                byte[] bytes = file.getBytes();
                String extension = "";
                int i = file.getOriginalFilename().lastIndexOf('.');
                if (i > 0) {
                    extension = file.getOriginalFilename().substring(i + 1);
                }
                String fileName = id + "." + extension;

                //CONSTANT constant = new CONSTANT();
                File dir = new File(CONSTANT.getBaseUrl() + "/recipes_images");
                if (!dir.exists()) {
                    dir.mkdirs();
                }

                File uploadedFile = new File(dir.getAbsolutePath() + File.separator + fileName);
                BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(uploadedFile));
                stream.write(bytes);
                stream.close();

                Recipe recipe = recipeRepository.findById(Integer.valueOf(id)).get();
                recipe.setImgURL(CONSTANT.getBaseUrl() + CONSTANT.getBaseFolderPath() +"/recipes_images/" + fileName);
                recipeRepository.save(recipe);

                return new Response("success", fileName);
            } catch (Exception e) {
                return new Response("error", e.getMessage());
            }
        } else {
            return new Response("error", "You failed to upload " + file.getOriginalFilename() + " because the file was empty.");
        }
    }


}

