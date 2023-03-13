package com.happytummy.happytummybackend.controllers;

import com.happytummy.happytummybackend.models.Recipe;
import com.happytummy.happytummybackend.models.RecipeQueryParam;
import com.happytummy.happytummybackend.models.Response;
import com.happytummy.happytummybackend.models.User;
import com.happytummy.happytummybackend.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
@CrossOrigin()
public class UserController {

    @Autowired
    private UserRepository userRepository;


    @PostMapping("/login")
    public Object login(@RequestBody User user) {
        User logged_in=userRepository.findByEmail(user.getEmail());
        if(logged_in != null && user.getPassword().equals(logged_in.getPassword())){
            return new Response("success", "login successful");
        }
        else{
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new Response("error", "user already exists"));
        }
    }

    @PostMapping("/signup")
    public Object signup(@RequestBody User user) {
        User isExist=userRepository.findByEmail(user.getEmail());
        if(isExist==null){
            userRepository.save(user);
            return new Response("success", "signup successful");
        }
        else{
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new Response("error", "user already exists"));
        }
    }


}