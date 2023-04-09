package com.happytummy.happytummybackend.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

@Entity
@Table(name = "recipe")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Recipe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String intro;
    private Integer userId;
    private String name;
    private String serve;
    private String cookTime;
    private String prepTime;
    private String difficulty;
    private String imgURL;
    private String ingredients;
    private String tip;
    private String mealType;

    private String dietaryCategory;


    public Recipe() {
    }


    public String getDietaryCategory() {
        return dietaryCategory;
    }

    public void setDietaryCategory(String dietary_category) {
        this.dietaryCategory = dietary_category;
    }



    public Recipe(int id, Integer userId, String name) {
        this.id = id;
        this.userId = userId;
        this.name = name;
    }

    public void setUserId(Integer user_id) {
        this.userId = user_id;
    }

    public String getMealType() {
        return mealType;
    }

    public void setMealType(String mealType) {
        this.mealType = mealType;
    }


    public Recipe(int id, String intro, Integer userId, String name, String serve, String cookTime, String prepTime, String difficulty, String imgURL, String ingredients, String tip, String mealType, String dietaryCategory) {
        this.id = id;
        this.intro = intro;
        this.userId = userId;
        this.name = name;
        this.serve = serve;
        this.cookTime = cookTime;
        this.prepTime = prepTime;
        this.difficulty = difficulty;
        this.imgURL = imgURL;
        this.ingredients = ingredients;
        this.tip = tip;
        this.mealType = mealType;
        this.dietaryCategory = dietaryCategory;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public Integer getUserId() {
        return userId;
    }

    public void setUser_id(int user_id) {
        this.userId = user_id;
    }

    public String getServe() {
        return serve;
    }

    public void setServe(String serve) {
        this.serve = serve;
    }

    public String getCookTime() {
        return cookTime;
    }

    public void setCookTime(String cookTime) {
        this.cookTime = cookTime;
    }

    public String getPrepTime() {
        return prepTime;
    }

    public void setPrepTime(String prepTime) {
        this.prepTime = prepTime;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public String getImgURL() {
        return imgURL;
    }

    public void setImgURL(String imgURL) {
        this.imgURL = imgURL;
    }

    public String getIngredients() {
        return ingredients;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }

    public String getTip() {
        return tip;
    }

    public void setTip(String tip) {
        this.tip = tip;
    }


}
