package com.happytummy.happytummybackend.controllers;

import com.google.gson.Gson;
import com.happytummy.happytummybackend.models.Response;
import com.happytummy.happytummybackend.models.User;
import com.happytummy.happytummybackend.services.RecipeService;
import com.happytummy.happytummybackend.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


@SpringBootTest
class UserControllerTest {

    @Autowired
    private UserController userControllerMock;

    UserService userServiceMock;

    @BeforeEach
    public void setup(){
        userServiceMock = mock(UserService.class);
        userControllerMock.userService = userServiceMock;
    }
    @Test
    public void objectCreated() throws Exception {
        assertNotNull(userControllerMock);
    }
    @Test
    public void testGetProfile() throws Exception {
        when(userServiceMock.getProfile("Vidhi")).thenReturn("Vidhi");
        Object response = new Gson().toJson(userControllerMock.getProfile("Vidhi"));
        assertEquals(new Gson().toJson(new Response("success","Vidhi")),response);
    }

    @Test
    public void testGetProfileWithNull() throws Exception {
        when(userServiceMock.getProfile("Vidhi")).thenReturn(null);
        Object response = new Gson().toJson(userControllerMock.getProfile("Vidhi"));
        assertEquals(new Gson().toJson(new Response("error","user not found")),response);
    }

//    @Test
//    public void testGetProfileWithEmptyString() throws Exception {
//        when(userServiceMock.getProfile("Vidhi")).thenReturn("");
//        Object response = new Gson().toJson(userControllerMock.getProfile("Vidhi"));
//        assertEquals(new Gson().toJson(new Response("error","user not found")),response);
//    }

    @Test
    public void testGetProfileWithEmptyStringAndNull() throws Exception {
        when(userServiceMock.getProfile("Vidhi")).thenReturn("");
        Object response = new Gson().toJson(userControllerMock.getProfile(null));
        assertEquals(new Gson().toJson(new Response("error","user not found")),response);
    }

    @Test
    public void testLogin() throws Exception {
        User user = new User("Vidhi","123");
        when(userServiceMock.login(user)).thenReturn("Vidhi");
        Object response = new Gson().toJson(userControllerMock.login(user));
        assertEquals(new Gson().toJson("Vidhi"),response);
    }

    @Test
    public void testSignup() throws Exception{
        User user = new User("Vidhi","123");
        when(userServiceMock.signup(user)).thenReturn("Vidhi");
        Object response = new Gson().toJson(userControllerMock.signup(user));
        assertEquals(new Gson().toJson("Vidhi"),response);
    }

    @Test
    public void testUpdateProfileImage() throws Exception{
        MultipartFile file = new MockMultipartFile("image.jpg","image.jpg", "image/jpeg", "image".getBytes());
        when(userServiceMock.updateProfileImage(file,"1")).thenReturn("Vidhi");
        Object response = new Gson().toJson(userControllerMock.updateProfileImage(file,"1"));
        assertEquals(new Gson().toJson("Vidhi"),response);
    }

    @Test
    public void testUpdateProfile() throws Exception{
        User user = new User("Vidhi","123");
        when(userServiceMock.updateProfile(user)).thenReturn(user);
        Object response = new Gson().toJson(userControllerMock.updateProfile(user,"1"));
        assertEquals(new Gson().toJson(new Response("success",user)),response);
    }

    @Test
    public void testUpdateProfileWithNullUser() throws Exception{
        when(userServiceMock.updateProfile(null)).thenReturn(null);
        User user = new User();
        Object response = new Gson().toJson(userControllerMock.updateProfile(user,"1"));
        assertEquals(new Gson().toJson(new Response("error","user not found")),response);
    }

}