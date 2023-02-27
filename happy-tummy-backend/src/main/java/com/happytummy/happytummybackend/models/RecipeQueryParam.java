package com.happytummy.happytummybackend.models;

public class RecipeQueryParam {


    Integer length;
    String tag;
    String ingredients;
    String search;

    public String getIngredients() {
        return ingredients;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }



    public RecipeQueryParam(Integer length, String tag, String ingredients, String search) {
        this.length = length;
        this.tag = tag;
        this.ingredients = ingredients;
        this.search = search;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public Integer getLength() {
        return length;
    }

    public void setLength(Integer length) {
        this.length = length;
    }

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }
}