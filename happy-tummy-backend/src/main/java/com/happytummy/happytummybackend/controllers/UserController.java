package com.happytummy.happytummybackend.controllers;

import com.happytummy.happytummybackend.models.Response;
import com.happytummy.happytummybackend.models.User;
import com.happytummy.happytummybackend.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


@RestController
@RequestMapping("/user")
@CrossOrigin()
public class UserController {


    @Autowired
    UserService userService;

    /**
     * Get user profile by name.
     *
     * @param name The name of the user for which profile is to be retrieved.
     * @return Response object containing the user profile on success, or an error message on failure.
     */
    @GetMapping("/getProfile/{name}")
    public Object getProfile(@PathVariable String name){
        Object response = userService.getProfile(name);
        if (response!=null){
            return new Response("success", response);
        }
        else{
            return new Response("error", "user not found");
        }
    }

    /**
     * Perform user login.
     *
     * @param user The User object containing login credentials.
     * @return Response object containing the user details on successful login, or an error message on failure.
     */
    @PostMapping("/login")
    public Object login(@RequestBody User user) {
        return userService.login(user);
    }

    /**
     * Perform user signup.
     *
     * @param user The User object containing signup details.
     * @return Response object containing the user details on successful signup, or an error message on failure.
     */
    @PostMapping("/signup")
    public Object signup(@RequestBody User user) {
        return userService.signup(user);
    }

    /**
     * Update user profile image.
     *
     * @param file The MultipartFile containing the new profile image.
     * @param id   The id of the user for which profile image is to be updated.
     * @return Response object containing the updated user details on success, or an error message on failure.
     */
    @PatchMapping("/updateProfileImage/{id}")
    public Object updateProfileImage(@RequestParam("file") MultipartFile file,@PathVariable String id){
        return userService.updateProfileImage(file,id);
    }

    /**
     * Update user profile.
     *
     * @param user The User object containing the updated profile details.
     * @param id   The id of the user for which profile is to be updated.
     * @return Response object containing the updated user details on success, or an error message on failure.
     */
    @PatchMapping("/updateProfile/{id}")
    public Object updateProfile(@RequestBody User user,@PathVariable String id){
        user.setId(Long.valueOf(id));
        User newUser=userService.updateProfile(user);
        if (newUser!=null){
            return new Response("success", newUser);
        }
        else{
            return new Response("error", "user not found");
        }
    }

}