package com.happytummy.happytummybackend.controllers;

import com.happytummy.happytummybackend.models.Response;
import com.happytummy.happytummybackend.services.UserFollowerService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller class for handling user follower related operations.
 */
@RestController
@RequestMapping("/users")
@CrossOrigin()
public class UserFollowerController {
    @Autowired
    private UserFollowerService userFollowerService;

    /**
     * Endpoint for following a user.
     *
     * @param request     HttpServletRequest object for accessing request related information.
     * @param followingId ID of the user to be followed.
     * @return Response object containing the status and result of the operation.
     */
    @PostMapping("/follow/{followingId}")
    public Response followUser(HttpServletRequest request, @PathVariable String followingId) {
        String userId = (String) request.getAttribute("claims");
        if(userId == null)
            return new Response("error", "User not found");
        return new Response("success", userFollowerService.followUser(userId, followingId));
    }

    /**
     * Endpoint for unfollowing a user.
     *
     * @param request     HttpServletRequest object for accessing request related information.
     * @param followingId ID of the user to be unfollowed.
     * @return Response object containing the status and result of the operation.
     */
    @PostMapping("/unfollow/{followingId}")
    public Response unfollowUser(HttpServletRequest request, @PathVariable String followingId) {
        String userId = (String) request.getAttribute("claims");
        if(userId == null)
            return new Response("error", "User not found");
        return new Response("success", userFollowerService.unfollowUser(userId, followingId));
    }

    /**
     * Endpoint for getting the list of followers for the authenticated user.
     *
     * @param request HttpServletRequest object for accessing request related information.
     * @return Response object containing the status and result of the operation.
     */
    @GetMapping("/followers")
    public Response getFollowersList(HttpServletRequest request) {
        String userId = (String) request.getAttribute("claims");
        if(userId == null)
            return null;
        return new Response("success", userFollowerService.getFollowersList(userId));
    }

    /**
     * Endpoint for getting the list of users followed by the authenticated user.
     *
     * @param request HttpServletRequest object for accessing request related information.
     * @return Response object containing the status and result of the operation.
     */
    @GetMapping("/following")
    public Response getFollowingList(HttpServletRequest request) {
        String userId = (String) request.getAttribute("claims");
        if(userId == null)
            return null;
        return new Response("success", userFollowerService.getFollowingList(userId));
    }

    /**
     * Endpoint for getting the count of followers for the authenticated user.
     *
     * @param request HttpServletRequest object for accessing request related information.
     * @return Response object containing the status and result of the operation.
     */
    @GetMapping("/followers/count")
    public Response getFollowersCount(HttpServletRequest request) {
        String userId = (String) request.getAttribute("claims");
        if(userId == null)
            return null;
        return new Response("success", userFollowerService.getFollowersCount(userId));
    }

    /**
     * Endpoint for getting the count of users followed by the authenticated user.
     *
     * @param request HttpServletRequest object for accessing request related information.
     * @return Response object containing the status and result of the operation.
     */
    @GetMapping("/following/count")
    public Response getFollowingCount(HttpServletRequest request) {
        String userId = (String) request.getAttribute("claims");
        if(userId == null)
            return null;
        return new Response("success", userFollowerService.getFollowingCount(userId));
    }
}

