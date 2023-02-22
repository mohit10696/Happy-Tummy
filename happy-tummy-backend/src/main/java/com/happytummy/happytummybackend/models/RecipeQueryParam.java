package com.happytummy.happytummybackend.models;

public class RecipeQueryParam {


    Integer length;
    String tag;

    public RecipeQueryParam(Integer length, String tag) {
        this.length = length;
        this.tag = tag;
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

}