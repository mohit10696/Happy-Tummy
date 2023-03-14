package com.happytummy.happytummybackend.services;

import java.util.List;

public interface ReviewService {

    List<Object> getReviewByRecipeId(String id);
}
