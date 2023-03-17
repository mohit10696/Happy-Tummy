package com.happytummy.happytummybackend.services.implementation;

import com.happytummy.happytummybackend.models.Review;
import com.happytummy.happytummybackend.models.User;
import com.happytummy.happytummybackend.repositories.ReviewRepository;
import com.happytummy.happytummybackend.repositories.UserRepository;
import com.happytummy.happytummybackend.services.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

@Service
public class ReviewServiceImplementation implements ReviewService {

    @Autowired
    ReviewRepository reviewRepository;

    @Autowired
    UserRepository userRepository;

    public List<Object> getReviewByRecipeId(String recipeId) {
        List<Object> resBody = new ArrayList<>();
        List<Review> reviews = reviewRepository.findReviewsByRecipeId(recipeId);
//        System.out.println(reviews);
        for (Review review : reviews) {
            Map<String, Object> result = new HashMap<>();
            Optional<User> user = userRepository.findById(review.getUserId());
            System.out.println(review.getUserId());
            if (user.isPresent()) {
                result.put("review", review);
                result.put("user", user.get());
            }

            resBody.add(result);
        }
        return resBody;
    }

    public Review addReview(String recipeId, String reviewText, int rating, MultipartFile image) {
        // create a new review object
        Review review = new Review();
        review.setRecipeId(recipeId);
        review.setDescription(reviewText);
        review.setRating(rating);
        // save the review object to the database
        review = reviewRepository.save(review);
        // return the saved review object
        return review;
    }

//    @Override
//    public Object getReviewByRecipeId(String id) {
//        Map<String, Object> responseData = new HashMap<>();
//        Optional<Review> reviewOptional = ReviewRepository.findById(Integer.valueOf(id));
//        if (reviewOptional.isPresent()) {
//            Review review = reviewOptional.get();
//            responseData.put("review", review);
//
//            responseData.put("nutrition", ReviewRepository.findByReviewId(id).toArray());
//            return responseData;
//        } else {
//            return "No reviews yet";
//        }
//    }
}
