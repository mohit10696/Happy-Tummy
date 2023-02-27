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

public class UserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/login")
    public Object login(User user) {
        User logged_in=userRepository.findByEmail(user.getEmail());
        if(user.getPassword().equals(logged_in.getPassword())){
            return "login successful";
        }
        else{
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("invalid password");
        }
    }

//    @PostMapping("/signup")
//    public String signup(@ModelAttribute User user, Model model) {
//        User existingUser = userRepository.findByUsername(user.getUsername());
//        if (existingUser != null) {
//            model.addAttribute("error", "Username already exists");
//            return "login";
//        }
//        userRepository.save(user);
//        return "redirect:/login";
//    }


}