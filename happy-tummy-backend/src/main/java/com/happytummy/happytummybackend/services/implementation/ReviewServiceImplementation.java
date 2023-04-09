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

/**
 * Implementation of ReviewService interface
 */
@Service
public class ReviewServiceImplementation implements ReviewService {

    @Autowired
    ReviewRepository reviewRepository;

    @Autowired
    UserRepository userRepository;

    /**
     * Get reviews for a recipe by recipeId
     * @param recipeId The recipeId for which reviews are to be retrieved
     * @return List of reviews and corresponding user details as a list of objects
     */
    public List<Object> getReviewByRecipeId(String recipeId) {
        List<Object> resBody = new ArrayList<>();
        List<Review> reviews = reviewRepository.findReviewsByRecipeId(recipeId);
        System.out.println(reviews);
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

    /**
     * Add a review for a recipe
     * @param recipeId The recipeId for which review is to be added
     * @param reviewQueryParam The review details
     * @param userId The userId of the user adding the review
     * @return Response object
     */
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
                //CONSTANT constant = new CONSTANT();
                File dir = new File(CONSTANT.getBaseFolderPath() + "/review_images");
                if(!dir.exists()){
                    dir.mkdirs();
                }

                File uploadedFile = new File(dir.getAbsolutePath() + File.separator + fileName);
                BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(uploadedFile));
                stream.write(bytes);
                stream.close();

                review.setImgURL(CONSTANT.getBaseUrl() + CONSTANT.getBaseFolderPath() +"/review_images/" + fileName);
                review = reviewRepository.save(review);


                return new Response("success", fileName);
            } catch (Exception e) {
                return new Response("error", e.getMessage());
            }
        } else {
            return new Response("success", "Review added successfully");
        }

    }


}
