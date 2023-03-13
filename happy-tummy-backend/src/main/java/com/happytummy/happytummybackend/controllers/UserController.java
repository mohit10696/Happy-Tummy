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

    @RequestMapping(method = RequestMethod.PUT,value="/user/{id}/update_profile")
    public Object updateProfile(@RequestBody String avatar,@PathVariable Long id){
        User current_user=userRepository.findById(id);
        if(current_user==null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new Response("error", "No such user"));
        }
        current_user.setAvatar(avatar);
        userRepository.save(current_user);

        try {
            // get the path to the folder where you want to save the file
            Path uploadsDir = Paths.get("src/main/resources/static/uploads");
            // create the folder if it doesn't exist
            if (!Files.exists(uploadsDir)) {
                Files.createDirectories(uploadsDir);
            }
            // get the filename from the URL
            String filename = id;

            // create a Path object for the new file
            Path targetPath = uploadsDir.resolve(filename);

            // if the file already exists, delete it
            if (Files.exists(targetPath)) {
                Files.delete(targetPath);
            }

            // retrieve the image data from the URL
            byte[] imageBytes = restTemplate.getForObject(imageUrl, byte[].class);

            // save the file to the target path
            Files.write(targetPath, imageBytes);


        } catch (IOException ex) {
            ex.printStackTrace();

        }
    }

        return new Response("success", "update successful");


    }


}