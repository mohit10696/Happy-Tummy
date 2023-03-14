package com.happytummy.happytummybackend.services.implementation;

import com.happytummy.happytummybackend.models.Response;
import com.happytummy.happytummybackend.models.User;
import com.happytummy.happytummybackend.repositories.UserRepository;
import com.happytummy.happytummybackend.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;

@Service
public class UserServiceImplementation implements UserService{
    @Autowired
    private UserRepository userRepository;


    @Override
    public Object login(User user) {
        User logged_in=userRepository.findByEmail(user.getEmail());
        if(logged_in != null && user.getPassword().equals(logged_in.getPassword())){
            return new Response("success", "login successful");
        }
        else{
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new Response("error", "user already exists"));
        }
    }

    @Override
    public Object signup(User user) {
        User isExist=userRepository.findByEmail(user.getEmail());
        if(isExist==null){
            userRepository.save(user);
            return new Response("success", "signup successful");
        }
        else{
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new Response("error", "user already exists"));
        }
    }

    @Override
    public Object updateProfile(String avatar, Long id) {
        return null;
    }

    @Override
    public Object updateProfileImage(MultipartFile file,String id) {
        if (!file.isEmpty()) {
            try {
                byte[] bytes = file.getBytes();
                String extension = "";
                int i = file.getOriginalFilename().lastIndexOf('.');
                if (i > 0) {
                    extension = file.getOriginalFilename().substring(i + 1);
                }
                String fileName = id + "." + extension;

                File dir = new File("/assets/profile_images");
                if (!dir.exists()) {
                    dir.mkdirs();
                }

                File uploadedFile = new File(dir.getAbsolutePath() + File.separator + fileName);
                BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(uploadedFile));
                stream.write(bytes);
                stream.close();

                User user = userRepository.findById(Long.parseLong(id)).get();
                user.setAvatar("profile_images/" + fileName);
                userRepository.save(user);

                return new Response("success", fileName);
            } catch (Exception e) {
                return new Response("error", e.getMessage());
            }
        } else {
            return new Response("error", "You failed to upload " + file.getOriginalFilename() + " because the file was empty.");
        }
    }
}
