package com.happytummy.happytummybackend.services;

import com.happytummy.happytummybackend.models.Review;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ReviewService {

    List<Object> getReviewByRecipeId(String id);

    Review addReview(String recipeId, String reviewText, int rating, MultipartFile image);

}
