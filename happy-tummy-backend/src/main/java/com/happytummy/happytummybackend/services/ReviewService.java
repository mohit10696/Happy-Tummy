package com.happytummy.happytummybackend.services;

import com.happytummy.happytummybackend.models.ReviewQueryParam;

import java.util.List;

public interface ReviewService {

    List<Object> getReviewByRecipeId(String id);

    Object addReview(String recipeId, ReviewQueryParam reviewQueryParam, String userId);

}
