package com.happytummy.happytummybackend.controllers;

import com.google.gson.Gson;
import com.happytummy.happytummybackend.models.*;
import com.happytummy.happytummybackend.services.RecipeService;
import com.happytummy.happytummybackend.services.ReviewService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;

import java.util.Date;

import static java.util.Collections.singletonList;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
public class ReviewControllerTest {
    @Autowired
    private ReviewController reviewControllerMock;

    ReviewService reviewServiceMock;
    @BeforeEach
    public void setup(){
        reviewServiceMock = mock(ReviewService.class);
        reviewControllerMock.reviewService = reviewServiceMock;
    }
    @Test
    public void objectCreated() throws Exception {
        assertNotNull(reviewControllerMock);
    }

    @Test
    public void testGetReviews() throws Exception {
        Review review = new Review(1, "Perfect", 4, "https://th.bing.com/th/id/R.d98ab74232adb70ca55a77538c881561?rik=vZ2QbeGy7Et2nw&riu=http%3a%2f%2fwww.eatgood4life.com%2fwp-content%2fuploads%2f2014%2f03%2fpao-600x651.jpg&ehk=JFNuwvshEo%2f%2fHvHbOv5cSb3rkErts4TtntGL8%2fLqhMw%3d&risl=&pid=ImgRaw&r=0", new Date(), "1", 1);
        when(reviewServiceMock.getReviewByRecipeId("1")).thenReturn(singletonList(review));
        Object response = new Gson().toJson(reviewControllerMock.getReviews("1"));
        assertEquals(new Gson().toJson(new Response("success",singletonList(review))), response);
    }

@Test
    public void testGetReviewsWithEmptyList() throws Exception {
        when(reviewServiceMock.getReviewByRecipeId("1")).thenReturn(null);
        Object response = new Gson().toJson(reviewControllerMock.getReviews("1"));
        assertEquals(new Gson().toJson(new Response("success",null)), response);
    }

    @Test
    public void testGetReviewsWithNull() throws Exception {
        when(reviewServiceMock.getReviewByRecipeId("1")).thenReturn(null);
        Object response = new Gson().toJson(reviewControllerMock.getReviews("1"));
        assertEquals(new Gson().toJson(new Response("success",null)), response);
    }

    @Test
    public void testGetReviewsWithEmptyString() throws Exception {
        when(reviewServiceMock.getReviewByRecipeId("")).thenReturn(null);
        Object response = new Gson().toJson(reviewControllerMock.getReviews(""));
        assertEquals(new Gson().toJson(new Response("success",null)), response);
    }

    @Test
    public void testGetReviewsWithException() throws Exception{
        String recipeId = "1";
        when(reviewServiceMock.getReviewByRecipeId(recipeId)).thenThrow(new RuntimeException("Reviews not found"));
        Object response = reviewControllerMock.getReviews(recipeId);
        assertEquals(new Gson().toJson(new Response("error", "Reviews not found")), new Gson().toJson(response));
    }

    @Test
    public void testAddReview() throws Exception{
        ReviewQueryParam reviewQueryParam = new ReviewQueryParam("Perfect", 4, new MockMultipartFile("image.jpg", "image.jpg", "image/jpeg", "image".getBytes()));
        when(reviewServiceMock.addReview("1", reviewQueryParam, "1")).thenReturn(new Response("success", "Review added successfully"));
        Object response = new Gson().toJson(reviewControllerMock.addReview(reviewQueryParam, "1", "1"));
        assertEquals(new Gson().toJson(new Response("success", "Review added successfully")), response);
    }

    @Test
    public void testAddReviewWithNull() throws Exception{

        when(reviewServiceMock.addReview("1", null, "1")).thenReturn(new Response("error", "Review cannot be null"));
        Object response = new Gson().toJson(reviewControllerMock.addReview(null, "1", "1"));
        assertEquals(new Gson().toJson(new Response("error", "Review cannot be null")), response);
    }

    @Test
    public void testAddReviewWithEmptyString() throws Exception{
        when(reviewServiceMock.addReview("", null, "")).thenReturn(new Response("error", "Review cannot be null"));
        Object response = new Gson().toJson(reviewControllerMock.addReview(null, "", ""));
        assertEquals(new Gson().toJson(new Response("error", "Review cannot be null")), response);
    }



}