package com.happytummy.happytummybackend.services;

import com.happytummy.happytummybackend.models.User;
import org.springframework.web.multipart.MultipartFile;

public interface UserService {

    Object getProfile(String id);
    Object login(User user);

    Object signup(User user);

    User updateProfile(User user);

    Object updateProfileImage(MultipartFile file,String id);
}
