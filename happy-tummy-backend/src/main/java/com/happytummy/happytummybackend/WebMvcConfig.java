package com.happytummy.happytummybackend;

import com.happytummy.happytummybackend.interceptors.JwtInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Autowired
    private JwtInterceptor jwtInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //this function will be used to add interceptors to the application for authentication



        registry.addInterceptor(jwtInterceptor)
                .excludePathPatterns("/recipes/**", "/ingredients/**", "/user/**", "/auth/**");

        registry.addInterceptor(jwtInterceptor)
                .addPathPatterns("/recipes/likes/**","/user/follow/**", "/user/unfollow/**");

    }
}
