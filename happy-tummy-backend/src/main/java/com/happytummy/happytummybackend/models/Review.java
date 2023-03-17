package com.happytummy.happytummybackend.models;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.Date;
@Entity
@Table(name="review")
public class Review {
    @Id
    private int reviewId;
    private String description;
    private int rating;
    private String imgURL;
    private Date date;
    private String recipeId;

    private long userId;

    public Review() {

    }

    public int getReviewId() {
        return reviewId;
    }
    public void setReviewId(int reviewId) {
        this.reviewId = reviewId;
    }
    public int getRating() {
        return rating;
    }
    public void setRating(int rating) {
        this.rating = rating;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public String getImgURL() {
        return imgURL;
    }
    public void setImgURL(String imgURL) {
        this.imgURL = imgURL;
    }
    public Date getDate() {
        return date;
    }
    public void setDate(Date date) {
        this.date = date;
    }
    public String getRecipeId() {
        return recipeId;
    }
    public void setRecipeId(String recipeId) {
        this.recipeId = recipeId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public Review(int reviewId, String description, int rating, String imgURL, Date date, String recipeId, long userId) {
        this.reviewId = reviewId;
        this.description = description;
        this.rating = rating;
        this.imgURL = imgURL;
        this.date = date;
        this.recipeId = recipeId;
        this.userId = userId;
    }
}
