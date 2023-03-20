package com.happytummy.happytummybackend.models;

import org.springframework.web.multipart.MultipartFile;

public class ReviewQueryParam {
    String reviewText;
    int rating;

    MultipartFile image;

    public String getReviewText() {
        return reviewText;
    }

    public void setReviewText(String reviewText) {
        this.reviewText = reviewText;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public MultipartFile getImage() {
        return image;
    }

    public void setImage(MultipartFile image) {
        this.image = image;
    }

    public ReviewQueryParam(String reviewText, int rating, MultipartFile image) {
        this.reviewText = reviewText;
        this.rating = rating;
        this.image = image;
    }
}
