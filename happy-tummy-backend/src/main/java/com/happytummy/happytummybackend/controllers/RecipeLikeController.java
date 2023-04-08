package com.happytummy.happytummybackend.controllers;

import com.happytummy.happytummybackend.models.RecipeLike;
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

    @GetMapping("/{recipeId}")
    public Response getAllLikesForRecipe(@PathVariable Long recipeId) {
        return new Response("success", recipeLikeService.getAllLikesForRecipe(recipeId));
    }

    @GetMapping("/{recipeId}/count")
    public Response getNumLikesForRecipe(@PathVariable Long recipeId) {
        return new Response("success", recipeLikeService.getNumLikesForRecipe(recipeId));
    }

    @PostMapping("/{recipeId}")
    public Object addLike(HttpServletRequest request, @PathVariable Integer recipeId) {
        String userId = (String) request.getAttribute("claims");
        if(userId == null)
            return new Response("error", "User not found");
        return new Response("success", recipeLikeService.addLike(Long.parseLong(userId), recipeId));
    }

    @DeleteMapping("/{recipeId}")
    public Response removeLike(HttpServletRequest request, @PathVariable Long recipeId) {
        String userId = (String) request.getAttribute("claims");
        if(userId == null)
            return new Response("error", "User not found");
        return new Response("success", recipeLikeService.removeLike(Long.parseLong(userId), recipeId));
    }

}
