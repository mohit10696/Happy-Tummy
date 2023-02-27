package com.happytummy.happytummybackend.services;

import com.happytummy.happytummybackend.models.Review;

import java.util.List;

public interface ReviewService {

    List<Review> getReviewByRecipeId(int id);
}
