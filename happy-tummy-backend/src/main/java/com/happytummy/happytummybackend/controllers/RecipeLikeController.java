package com.happytummy.happytummybackend.controllers;

import com.happytummy.happytummybackend.models.Response;
import com.happytummy.happytummybackend.services.RecipeLikeService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/recipes/likes")
@CrossOrigin()
public class RecipeLikeController {

    @Autowired
    private RecipeLikeService recipeLikeService;

    /**
     * Get all likes for a recipe by recipeId.
     *
     * @param recipeId The id of the recipe.
     * @return A response object containing the status and list of likes for the recipe.
     */
    @GetMapping("/{recipeId}")
    public Response getAllLikesForRecipe(@PathVariable Long recipeId) {
        return new Response("success", recipeLikeService.getAllLikesForRecipe(recipeId));
    }

    /**
     * Get the number of likes for a recipe by recipeId.
     *
     * @param recipeId The id of the recipe.
     * @return A response object containing the status and the number of likes for the recipe.
     */
    @GetMapping("/{recipeId}/count")
    public Response getNumLikesForRecipe(@PathVariable Long recipeId) {
        return new Response("success", recipeLikeService.getNumLikesForRecipe(recipeId));
    }

    /**
     * Add a like for a recipe by recipeId.
     *
     * @param request   The HTTP servlet request object.
     * @param recipeId  The id of the recipe.
     * @return A response object containing the status and the result of adding a like for the recipe.
     */
    @PostMapping("/{recipeId}")
    public Object addLike(HttpServletRequest request, @PathVariable Integer recipeId) {
        String userId = (String) request.getAttribute("claims");
        if(userId == null)
            return new Response("error", "User not found");
        return new Response("success", recipeLikeService.addLike(Long.parseLong(userId), recipeId));
    }

    /**
     * Remove a like for a recipe by recipeId.
     *
     * @param request   The HTTP servlet request object.
     * @param recipeId  The id of the recipe.
     * @return A response object containing the status and the result of removing a like for the recipe.
     */
    @DeleteMapping("/{recipeId}")
    public Response removeLike(HttpServletRequest request, @PathVariable Long recipeId) {
        String userId = (String) request.getAttribute("claims");
        if(userId == null)
            return new Response("error", "User not found");
        return new Response("success", recipeLikeService.removeLike(Long.parseLong(userId), recipeId));
    }

}
