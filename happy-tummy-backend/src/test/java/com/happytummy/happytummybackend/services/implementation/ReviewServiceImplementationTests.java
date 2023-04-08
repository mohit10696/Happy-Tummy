package com.happytummy.happytummybackend.services.implementation;

import com.happytummy.happytummybackend.CONSTANT;
import com.happytummy.happytummybackend.models.*;
import com.happytummy.happytummybackend.repositories.RecipeRepository;
import com.happytummy.happytummybackend.repositories.ReviewRepository;
import com.happytummy.happytummybackend.repositories.UserRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
public class ReviewServiceImplementationTests {

    @InjectMocks
    private ReviewServiceImplementation reviewService;

    @Mock
    private ReviewRepository reviewRepository;

    @Mock
    private RecipeRepository recipeRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private CONSTANT constant;

    @Mock
    private FileOutputStream fileOutputStream;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetReviewByRecipeIdWithValidRecipeIdAndReviewsAvailable() {

        String recipeId = "recipeId";
        List<Review> reviews = Arrays.asList(new Review(), new Review(), new Review());
        when(reviewRepository.findReviewsByRecipeId(recipeId)).thenReturn(reviews);
        when(userRepository.findById(any())).thenReturn(Optional.of(new User()));

        List<Object> result = reviewService.getReviewByRecipeId(recipeId);

        assertNotNull(result);
        assertEquals(3, result.size()); // Expecting 3 reviews
        for (Object obj : result) {
            assertTrue(obj instanceof Map); // Expecting each object in the list to be a Map
            Map<String, Object> map = (Map<String, Object>) obj;
            assertTrue(map.containsKey("review")); // Expecting Map to contain "review" key
            assertTrue(map.containsKey("user")); // Expecting Map to contain "user" key
            assertNotNull(map.get("review")); // Expecting "review" value to be not null
            assertNotNull(map.get("user")); // Expecting "user" value to be not null
        }
    }

    @Test
    public void testGetReviewByRecipeIdWithValidRecipeIdAndNoReviewsAvailable() {

        String recipeId = "recipeId";
        List<Review> reviews = Collections.emptyList();
        when(reviewRepository.findReviewsByRecipeId(recipeId)).thenReturn(reviews);

        List<Object> result = reviewService.getReviewByRecipeId(recipeId);

        assertNotNull(result);
        assertTrue(result.isEmpty()); // Expecting an empty list
    }

    @Test
    public void testGetReviewByRecipeIdWithNullRecipeId() {

        String recipeId = null;

        List<Object> result = reviewService.getReviewByRecipeId(recipeId);

        assertNotNull(result);
        assertTrue(result.isEmpty()); // Expecting an empty list
    }


    @Test
    public void testAddReview() throws Exception {

        String recipeId = "recipeId";
        String reviewText = "Test review";
        int rating = 5;
        String userId = "1";
        ReviewQueryParam reviewQueryParam = new ReviewQueryParam(reviewText, rating, null);
        Review savedReview = new Review();
        savedReview.setReviewId(1);

        when(reviewRepository.save(any(Review.class))).thenReturn(savedReview);

        Object result = reviewService.addReview(recipeId, reviewQueryParam, userId);

        assertNotNull(result);
        assertEquals(Response.class, result.getClass());
        Response response = (Response) result;
        assertEquals("success", response.getStatus());
        assertEquals("Review added successfully", ((Response) result).getData()); // Corrected assertion

    }

    @Test
    public void testAddReviewWithoutImage() throws Exception {

        String recipeId = "recipeId";
        String reviewText = "Test review";
        int rating = 5;
        String userId = "1";
        ReviewQueryParam reviewQueryParam = new ReviewQueryParam(reviewText, rating, null); // Set image to null
        Review savedReview = new Review();
        savedReview.setReviewId(1);

        when(reviewRepository.save(any(Review.class))).thenReturn(savedReview);

        Response response = (Response) reviewService.addReview(recipeId, reviewQueryParam, userId);
        assertEquals("success", response.getStatus());
        assertEquals("Review added successfully", response.getData());

    }

    @Test
    public void testAddReviewWithError() throws Exception {
        String recipeId = "recipeId";
        String userId = "1";
        ReviewQueryParam reviewQueryParam = new ReviewQueryParam(null, 5, null); // Set reviewText to null

        when(reviewRepository.save(any(Review.class))).thenThrow(new RuntimeException("Error occurred while saving review"));

        assertThrows(RuntimeException.class, () -> {
            reviewService.addReview(recipeId, reviewQueryParam, userId);
        });

    }

//    @Test
//    public void testAddReviewWithImage_Success() throws Exception {
//        String recipeId = "recipeId";
//        String reviewText = "Test review";
//        int rating = 5;
//        String userId = "1";
//        MultipartFile image = mock(MultipartFile.class);
//        ReviewQueryParam reviewQueryParam = new ReviewQueryParam(reviewText, rating, image);
//
//        Review savedReview = new Review();
//        savedReview.setReviewId(1);
//
//        when(reviewRepository.save(any(Review.class))).thenReturn(savedReview);
//        when(image.getBytes()).thenReturn(new byte[]{});
//        when(image.getOriginalFilename()).thenReturn("test.jpg");
//
//        Object result = reviewService.addReview(recipeId, reviewQueryParam, userId);
//
//        assertNotNull(result);
//        assertEquals(Response.class, result.getClass());
//        Response response = (Response) result;
//        assertEquals("success", response.getStatus());
//        assertNotNull(((Response) result).getData()); // Assert that response data is not null
//    }

    @Test
    public void testAddReview_ErrorWithImage() throws Exception {
        String reviewText = "Test review";
        int rating = 5;
        String userId = "1";

        // Mock the MultipartFile
        MultipartFile image = mock(MultipartFile.class);
        ReviewQueryParam reviewQueryParam = new ReviewQueryParam(reviewText, rating, image);

        when(image.isEmpty()).thenReturn(false);
        when(image.getOriginalFilename()).thenReturn("test.jpg");
        doThrow(new IOException("Failed to process image")).when(image).getBytes();

        // Call the addReview() method and capture the result
        Object result = reviewService.addReview("1", reviewQueryParam, userId);

        // Assert that the result is not null
        assertNotNull(result);

        // Assert that the result is an instance of Response class
        assertTrue(result instanceof Response);

        // Assert that the response has the expected error message
        Response response = (Response) result;
        assertEquals("error", response.getStatus());
        assertEquals("Failed to process image", response.getData());
    }

}