package com.happytummy.happytummybackend.services.implementation;

import com.happytummy.happytummybackend.models.Review;
import com.happytummy.happytummybackend.repositories.ReviewRepository;
import com.happytummy.happytummybackend.services.ReviewService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewServiceImplementation implements ReviewService {

//    @PersistenceContext
//    private EntityManager entityManager;

    @Autowired
    ReviewRepository reviewRepository;

    public List<Review> getReviewByRecipeId(String recipeId) {
//        String query = "SELECT r FROM Review r WHERE r.recipeId = :recipeId";
//        System.out.println(recipeId);
//        List<Review> reviews = entityManager.createQuery(query, Review.class)
//                .setParameter("recipeId", recipeId)
//                .getResultList();
        return reviewRepository.findReviewsByRecipeId(recipeId);
    }

//    public List<Review> getReviewByRecipeId(String recipeId) {
//        return entityManager.createQuery("SELECT p FROM Review p WHERE p.recipeId = recipeId").getResultList();
//        int length = .getLength() != null ? queryParam.getLength() : 10;
//        if(queryParam.getTag() != null){
//            return recipeRepository.findByTagName(queryParam.getTag(),length);
//        }
//        else{
//            return recipeRepository.findByLimit(length);
//        }

//    }

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
