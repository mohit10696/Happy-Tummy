package com.happytummy.happytummybackend.controllers;

import com.happytummy.happytummybackend.models.Response;
import com.happytummy.happytummybackend.models.Review;
import com.happytummy.happytummybackend.services.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reviews")
@CrossOrigin()
public class ReviewController {

    @Autowired
    ReviewService reviewService;


    @GetMapping("/{recipeId}")
    public Object getReviews(@PathVariable String recipeId) {
        try {
            List<Object> reviews  = reviewService.getReviewByRecipeId(recipeId);
            return new Response("success", reviews);
        } catch (Exception e) {
            return new Response("error", e.getMessage());
        }
    }
}
