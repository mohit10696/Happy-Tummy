package com.happytummy.happytummybackend.controllers;

import com.happytummy.happytummybackend.models.Response;
import com.happytummy.happytummybackend.models.UserFollower;
import com.happytummy.happytummybackend.services.UserFollowerService;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
public class UserFollowerControllerTest {

    @InjectMocks
    private UserFollowerController userFollowerController;

    @Mock
    private UserFollowerService userFollowerService;

    @Test
    public void testFollowUserSuccess() {
        // Create a mock followingId and user ID
        String followingId = "2";
        String userId = "1";

        // Set up HttpServletRequest to return the user ID as an attribute
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getAttribute("claims")).thenReturn(userId);

        // Set up userFollowerService to return a mock UserFollower object when followUser method is called
        UserFollower userFollower = new UserFollower();
        when(userFollowerService.followUser(userId, followingId)).thenReturn(userFollower);

        // Call the followUser method
        Response result = userFollowerController.followUser(request, followingId);

        // Assert that the result is not null
        assertNotNull(result);

        // Assert that the result has a status of "success"
        assertEquals("success", result.getStatus());

        // Assert that the result contains the userFollower object
        assertEquals(userFollower, result.getData());
    }

    @Test
    public void testFollowUserUserNotFound() {
        // Create a mock followingId
        String followingId = "2";

        // Set up HttpServletRequest to return null as the user ID attribute
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getAttribute("claims")).thenReturn(null);

        // Call the followUser method
        Response result = userFollowerController.followUser(request, followingId);

        // Assert that the result is not null
        assertNotNull(result);

        // Assert that the result has a status of "error"
        assertEquals("error", result.getStatus());

        // Assert that the result contains an error message
        assertNotNull(result.getData());
        assertEquals("User not found", result.getData());
    }

    @Test
    public void testUnfollowUserSuccess() {
        // Create a mock followingId and user ID
        String followingId = "2";
        String userId = "1";

        // Set up HttpServletRequest to return the user ID as an attribute
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getAttribute("claims")).thenReturn(userId);

        // Set up userFollowerService to return a mock UserFollower object when unfollowUser method is called
        UserFollower userFollower = new UserFollower();
        when(userFollowerService.unfollowUser(userId, followingId)).thenReturn(userFollower);

        // Call the unfollowUser method
        Response result = userFollowerController.unfollowUser(request, followingId);

        // Assert that the result is not null
        assertNotNull(result);

        // Assert that the result has a status of "success"
        assertEquals("success", result.getStatus());

        // Assert that the result contains the userFollower object
        assertEquals(userFollower, result.getData());
    }

    @Test
    public void testUnfollowUserUserNotFound() {
        // Create a mock followingId
        String followingId = "2";

        // Set up HttpServletRequest to return null as the user ID attribute
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getAttribute("claims")).thenReturn(null);

        // Call the unfollowUser method
        Response result = userFollowerController.unfollowUser(request, followingId);

        // Assert that the result is not null
        assertNotNull(result);

        // Assert that the result has a status of "error"
        assertEquals("error", result.getStatus());

        // Assert that the result contains an error message
        assertNotNull(result.getData());
        assertEquals("User not found", result.getData());
    }

    @Test
    public void testGetFollowersListSuccess() {
        // Create a mock user ID
        String userId = "1";

        // Set up HttpServletRequest to return the user ID as an attribute
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getAttribute("claims")).thenReturn(userId);

        // Set up userFollowerService to return a mock list of UserFollower objects when getFollowersList method is called
        List<UserFollower> followersList = new ArrayList<>();
        followersList.add(new UserFollower());
        followersList.add(new UserFollower());
        when(userFollowerService.getFollowersList(userId)).thenReturn(followersList);

        // Call the getFollowersList method
        Response result = userFollowerController.getFollowersList(request);

        // Assert that the result is not null
        assertNotNull(result);

        // Assert that the result has a status of "success"
        assertEquals("success", result.getStatus());

        // Assert that the result contains the followersList object
        assertEquals(followersList, result.getData());
    }

    @Test
    public void testGetFollowersListNull(){
        // Create a mock user ID
        String userId = null;

        // Set up HttpServletRequest to return the user ID as an attribute
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getAttribute("claims")).thenReturn(userId);

        when(userFollowerService.getFollowersList(userId)).thenReturn(null);

        // Call the getFollowersList method
        Response result = userFollowerController.getFollowersList(request);
        assertNull(result);

    }

    @Test
    public void testGetFollowingListSuccess() {
        // Create a mock user ID
        String userId = "1";

        // Set up HttpServletRequest to return the user ID as an attribute
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getAttribute("claims")).thenReturn(userId);

        // Set up userFollowerService to return a mock list of UserFollower objects when getFollowingList method is called
        List<UserFollower> followingList = new ArrayList<>();
        followingList.add(new UserFollower());
        followingList.add(new UserFollower());
        when(userFollowerService.getFollowingList(userId)).thenReturn(followingList);

        // Call the getFollowingList method
        Response result = userFollowerController.getFollowingList(request);

        // Assert that the result is not null
        assertNotNull(result);

        // Assert that the result has a status of "success"
        assertEquals("success", result.getStatus());

        // Assert that the result contains the followingList object
        assertEquals(followingList, result.getData());
    }

    @Test
    public void testGetFollowingListNull() {
        // Create a mock user ID
        String userId = null;

        // Set up HttpServletRequest to return the user ID as an attribute
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getAttribute("claims")).thenReturn(userId);

        // Set up userFollowerService to return null when getFollowingList method is called
        when(userFollowerService.getFollowingList(userId)).thenReturn(null);

        // Call the getFollowingList method
        Response result = userFollowerController.getFollowingList(request);

        assertNull(result);
    }

    @Test
    public void testGetFollowersCountSuccess() {
        // Create a mock user ID
        String userId = "1";

        // Set up HttpServletRequest to return the user ID as an attribute
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getAttribute("claims")).thenReturn(userId);

        // Set up userFollowerService to return a mock follower count when getFollowersCount method is called
        Long followerCount = 5L;
        when(userFollowerService.getFollowersCount(userId)).thenReturn(followerCount);

        // Call the getFollowersCount method
        Response result = userFollowerController.getFollowersCount(request);

        // Assert that the result is not null
        assertNotNull(result);

        // Assert that the result has a status of "success"
        assertEquals("success", result.getStatus());

        // Assert that the result contains the followerCount as data
        assertEquals(followerCount, result.getData());
    }

    @Test
    public void testGetFollowersCountNull() {
        // Create a mock user ID
        String userId = null;

        // Set up HttpServletRequest to return the user ID as an attribute
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getAttribute("claims")).thenReturn(userId);

        // Set up userFollowerService to return a null follower count when getFollowersCount method is called
        when(userFollowerService.getFollowersCount(userId)).thenReturn(null);

        // Call the getFollowersCount method
        Response result = userFollowerController.getFollowersCount(request);

        assertNull(result);
    }

    @Test
    public void testGetFollowingCountSuccess() {
        // Create a mock user ID
        String userId = "1";

        // Set up HttpServletRequest to return the user ID as an attribute
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getAttribute("claims")).thenReturn(userId);

        // Set up userFollowerService to return a mock following count when getFollowingCount method is called
        Long followingCount = 1L;
        when(userFollowerService.getFollowingCount(userId)).thenReturn(followingCount);

        // Call the getFollowingCount method
        Response result = userFollowerController.getFollowingCount(request);

        // Assert that the result is not null
        assertNotNull(result);

        // Assert that the result has a status of "success"
        assertEquals("success", result.getStatus());

        // Assert that the result contains the followingCount as data
        assertEquals(followingCount, result.getData());
    }

    @Test
    public void testGetFollowingCountNull() {
        // Create a mock user ID
        String userId = null;
        // Set up HttpServletRequest to return the user ID as an attribute
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getAttribute("claims")).thenReturn(userId);
        Response result = userFollowerController.getFollowingCount(request);
        assertNull(result);


    }

}