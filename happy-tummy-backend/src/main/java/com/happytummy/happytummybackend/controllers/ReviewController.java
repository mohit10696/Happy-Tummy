package com.happytummy.happytummybackend.controllers;

import com.happytummy.happytummybackend.models.Response;
import com.happytummy.happytummybackend.models.Review;
import com.happytummy.happytummybackend.services.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/reviews")

public class ReviewController {

    @Autowired
    ReviewService reviewService;


    @GetMapping("/{recipeId}")
    public Object getReviews(@PathVariable String recipeId) {
        try {
            int id= Integer.parseInt(recipeId);
            List<Review> reviews  = reviewService.getReviewByRecipeId(id);
            return new Response("success", reviews);
        } catch (Exception e) {
            return new Response("error", e.getMessage());
        }
    }
}
