package com.happytummy.happytummybackend.utils;

import com.happytummy.happytummybackend.models.User;

public class JwtService {
    private JwtUtils jwtUtils;

    public JwtService(JwtUtils jwtUtils) {
        this.jwtUtils = jwtUtils;
    }

    public String generateToken(User user) {
        return jwtUtils.generateToken(user);
    }

    public String validateToken(String token) {
        return jwtUtils.validateToken(token);
    }
}
