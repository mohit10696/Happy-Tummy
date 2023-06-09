package com.happytummy.happytummybackend.models;

public class RecipeQueryParam {

    Integer length;
    Integer pageIndex;
    String tag;
    String ingredients;
    String mealPreference;
    String q;

    public RecipeQueryParam(Integer length, Integer pageIndex, String tag, String ingredients, String mealPreference, String q) {
        this.length = length;
        this.pageIndex = pageIndex;
        this.tag = tag;
        this.ingredients = ingredients;
        this.mealPreference = mealPreference;
        this.q = q;
    }

    public RecipeQueryParam() {
    }

    public String getMealPreference() {
        return mealPreference;
    }

    public void setMealPreference(String mealPreference) {
        this.mealPreference = mealPreference;
    }

    public Integer getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(Integer pageIndex) {
        this.pageIndex = pageIndex;
    }

    public RecipeQueryParam(Integer length, String tag, String ingredients, String q, Integer pageIndex) {
        this.length = length;
        this.tag = tag;
        this.ingredients = ingredients;
        this.q = q;
        this.pageIndex = pageIndex;
    }

    public Integer getLength() {
        return length;
    }

    public void setLength(Integer length) {
        this.length = length;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getIngredients() {
        return ingredients;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }

    public String getQ() {
        return q;
    }

    public void setQ(String q) {
        this.q = q;
    }
}