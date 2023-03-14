package com.happytummy.happytummybackend.controllers;

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
    private UserService userService;

    @PostMapping("/login")
    public Object login(@RequestBody User user) {
        return userService.login(user);
    }

    @PostMapping("/signup")
    public Object signup(@RequestBody User user) {
        return userService.signup(user);
    }

    @PatchMapping("/updateProfileImage/{id}")
    public Object updateProfileImage(@RequestParam("file") MultipartFile file,@PathVariable String id){
        return userService.updateProfileImage(file,id);
    }

}