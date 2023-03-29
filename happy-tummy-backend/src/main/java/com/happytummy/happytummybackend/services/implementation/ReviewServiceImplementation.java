package com.happytummy.happytummybackend.services.implementation;

import com.happytummy.happytummybackend.CONSTANT;
import com.happytummy.happytummybackend.models.*;
import com.happytummy.happytummybackend.repositories.ReviewRepository;
import com.happytummy.happytummybackend.repositories.UserRepository;
import com.happytummy.happytummybackend.services.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
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

    public Object addReview(String recipeId, ReviewQueryParam reviewQueryParam, String userId) {

        Review review = new Review();
        review.setRecipeId(recipeId);
        if (!reviewQueryParam.getReviewText().isEmpty()) {
            review.setDescription(reviewQueryParam.getReviewText());
        }
        review.setRating(reviewQueryParam.getRating());
        review.setUserId(Long.parseLong(userId));
        review = reviewRepository.save(review);


        if(reviewQueryParam.getImage() != null && !reviewQueryParam.getImage().isEmpty()){
            MultipartFile image = reviewQueryParam.getImage();
            try {
                byte[] bytes =image.getBytes();
                String extension = "";
                int i = image.getOriginalFilename().lastIndexOf('.');
                if (i > 0) {
                    extension = image.getOriginalFilename().substring(i + 1);
                }


                String fileName = review.getReviewId() + "." + extension;

                File dir = new File(CONSTANT.BASE_FOLDER_PATH + "/review_images");
                if(!dir.exists()){
                    dir.mkdirs();
                }

                File uploadedFile = new File(dir.getAbsolutePath() + File.separator + fileName);
                BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(uploadedFile));
                stream.write(bytes);
                stream.close();

                review.setImgURL(CONSTANT.BASE_URL + CONSTANT.BASE_FOLDER_PATH +"/review_images/" + fileName);
                review = reviewRepository.save(review);


                return new Response("success", fileName);
            } catch (Exception e) {
                return new Response("error", e.getMessage());
            }
        } else {
            return new Response("success", "Review added successfully");
        }

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
