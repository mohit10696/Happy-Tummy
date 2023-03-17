package com.happytummy.happytummybackend.controllers;

import com.happytummy.happytummybackend.models.Response;
import com.happytummy.happytummybackend.models.Review;
import com.happytummy.happytummybackend.services.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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

    @PostMapping("/{recipeId}/writeReview")
    public ResponseEntity<?> addReview(@PathVariable String recipeId, @RequestParam("reviewText") String reviewText, @RequestParam("rating") int rating, @RequestParam("image") MultipartFile image) {
        // call the review service to create a new review and return the response
        Review review = reviewService.addReview(recipeId, reviewText, rating, image);
        return ResponseEntity.ok().body(review);
    }
}
