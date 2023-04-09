package com.happytummy.happytummybackend.controllers;

import com.happytummy.happytummybackend.models.Response;
import com.happytummy.happytummybackend.models.ReviewQueryParam;
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

    /**
     * Get reviews for a specific recipe by recipeId.
     *
     * @param recipeId The recipeId for which reviews are to be retrieved.
     * @return Response object containing the reviews on success, or an error message on failure.
     */
    @GetMapping("/{recipeId}")
    public Object getReviews(@PathVariable String recipeId) {
        try {
            List<Object> reviews  = reviewService.getReviewByRecipeId(recipeId);
            return new Response("success", reviews);
        } catch (Exception e) {
            return new Response("error", e.getMessage());
        }
    }

    /**
     * Add a new review for a specific recipe and user.
     *
     * @param reviewQueryParam The review details to be added.
     * @param recipeId         The recipeId for which the review is to be added.
     * @param userId           The userId of the user who is adding the review.
     * @return Response object containing the newly created review on success, or an error message on failure.
     */
    @PostMapping("/{recipeId}/{userId}")
    public Object addReview( @ModelAttribute ReviewQueryParam reviewQueryParam,@PathVariable String recipeId, @PathVariable String userId) {
        // call the review service to create a new review and return the response
        return reviewService.addReview(recipeId, reviewQueryParam, userId);
    }
}
