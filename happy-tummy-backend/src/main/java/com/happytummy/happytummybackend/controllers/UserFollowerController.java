package com.happytummy.happytummybackend.controllers;

import com.happytummy.happytummybackend.models.Response;
import com.happytummy.happytummybackend.models.UserFollower;
import com.happytummy.happytummybackend.services.UserFollowerService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@CrossOrigin()
public class UserFollowerController {
    @Autowired
    private UserFollowerService userFollowerService;

    @PostMapping("/follow/{followingId}")
    public Response followUser(HttpServletRequest request, @PathVariable String followingId) {
        String userId = (String) request.getAttribute("claims");
        if(userId == null)
            return new Response("error", "User not found");
        return new Response("success", userFollowerService.followUser(userId, followingId));
    }

    @PostMapping("/unfollow/{followingId}")
    public Response unfollowUser(HttpServletRequest request, @PathVariable String followingId) {
        String userId = (String) request.getAttribute("claims");
        if(userId == null)
            return new Response("error", "User not found");
        return new Response("success", userFollowerService.unfollowUser(userId, followingId));
    }

    @GetMapping("/followers")
    public Response getFollowersList(HttpServletRequest request) {
        String userId = (String) request.getAttribute("claims");
        if(userId == null)
            return null;
        return new Response("success", userFollowerService.getFollowersList(userId));
    }

    @GetMapping("/following")
    public Response getFollowingList(HttpServletRequest request) {
        String userId = (String) request.getAttribute("claims");
        if(userId == null)
            return null;
        return new Response("success", userFollowerService.getFollowingList(userId));
    }

    @GetMapping("/followers/count")
    public Response getFollowersCount(HttpServletRequest request) {
        String userId = (String) request.getAttribute("claims");
        if(userId == null)
            return null;
        return new Response("success", userFollowerService.getFollowersCount(userId));
    }

    @GetMapping("/following/count")
    public Response getFollowingCount(HttpServletRequest request) {
        String userId = (String) request.getAttribute("claims");
        if(userId == null)
            return null;
        return new Response("success", userFollowerService.getFollowingCount(userId));
    }
}

