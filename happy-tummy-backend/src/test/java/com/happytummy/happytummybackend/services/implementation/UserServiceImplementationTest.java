package com.happytummy.happytummybackend.services.implementation;

import com.google.gson.Gson;
import com.happytummy.happytummybackend.controllers.UserController;
import com.happytummy.happytummybackend.models.Recipe;
import com.happytummy.happytummybackend.models.Response;
import com.happytummy.happytummybackend.models.User;
import com.happytummy.happytummybackend.repositories.RecipeRepository;
import com.happytummy.happytummybackend.repositories.UserRepository;
import com.happytummy.happytummybackend.services.UserService;
import com.happytummy.happytummybackend.utils.JwtUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.*;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.multipart.MultipartFile;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
class UserServiceImplementationTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private RecipeRepository recipeRepository;

    @InjectMocks
    private UserServiceImplementation userServiceImplementation;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetProfile() throws Exception {
        String name = "Vidhi";

        // Create a mock user and a mock recipe list
        User user = new User();
        user.setId(1L);
        user.setName(name);
        List<Recipe> recipeList = new ArrayList<>();
        Recipe recipe1 = new Recipe();
        recipe1.setId(1);
        recipe1.setUserId(1);
        Recipe recipe2 = new Recipe();
        recipe2.setId(2);
        recipe2.setUserId(1);
        recipeList.add(recipe1);
        recipeList.add(recipe2);

        when(userRepository.findByName(name)).thenReturn(Optional.of(user));
        when(recipeRepository.findByUserId(user.getId())).thenReturn(recipeList);

        Object result = userServiceImplementation.getProfile(name);
        assertNotNull(result);
        Map<String, Object> responseData = (Map<String, Object>) result;
        Optional<User> responseUser = (Optional<User>) responseData.get("user");

        assertTrue(responseUser.isPresent());
        assertEquals(user.getId(), responseUser.get().getId());
        assertEquals(user.getName(), responseUser.get().getName());
        assertEquals(recipeList, responseData.get("recipes"));
    }

    //
    @Test
    public void testLogin() throws Exception {
        User user = new User();
        user.setEmail("test@example.com");
        user.setPassword("password");

        // Configure userRepository mock
        when(userRepository.findByEmail(user.getEmail())).thenReturn(user);

        // Configure jwtUtils mock
        JwtUtils jwtUtils = mock(JwtUtils.class);
        when(jwtUtils.generateToken(user)).thenReturn("test-token");

        // Create a new instance of UserServiceImplementation without any constructor arguments
        UserServiceImplementation userServiceImplementation = new UserServiceImplementation();

        // Set the mock userRepository and jwtUtils in the userServiceImplementation instance
        ReflectionTestUtils.setField(userServiceImplementation, "userRepository", userRepository);
        ReflectionTestUtils.setField(userServiceImplementation, "jwtUtils", jwtUtils);

        // Call the login method of userServiceImplementation
        Object result = userServiceImplementation.login(user);

        // Verify the response
        assertNotNull(result);
        Response response = (Response) result;
        assertEquals("success", response.getStatus());
        assertNotNull(response.getData());
        Map<String, Object> responseData = (Map<String, Object>) response.getData();
        assertEquals(user, responseData.get("user"));
        assertEquals("test-token", responseData.get("token"));
    }

    @Test
    public void testLoginInvalidCredentials() throws Exception {
        User user = new User();
        user.setEmail("test@example.com");
        user.setPassword("password");

        when(userRepository.findByEmail(user.getEmail())).thenReturn(user);

        JwtUtils jwtUtils = mock(JwtUtils.class);
        UserServiceImplementation userServiceImplementation = new UserServiceImplementation();
        ReflectionTestUtils.setField(userServiceImplementation, "userRepository", userRepository);
        ReflectionTestUtils.setField(userServiceImplementation, "jwtUtils", jwtUtils);

        User userWithInvalidCredentials = new User();
        userWithInvalidCredentials.setEmail("test@example.com");
        userWithInvalidCredentials.setPassword("wrong-password");
        Object result = userServiceImplementation.login(userWithInvalidCredentials);

        assertNotNull(result);
        ResponseEntity response = (ResponseEntity) result;
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        Response body = (Response) response.getBody();
        assertEquals("error", body.getStatus());
        assertEquals("user already exists", body.getData());
    }

    @Test
    public void testRegister() throws Exception {
        User user = new User();
        user.setEmail("test@example.com");
        user.setPassword("password");

        when(userRepository.findByEmail(user.getEmail())).thenReturn(null);
        UserServiceImplementation userServiceImplementation = new UserServiceImplementation();
        ReflectionTestUtils.setField(userServiceImplementation, "userRepository", userRepository);
        Object result = userServiceImplementation.signup(user);
        assertNotNull(result);
        Response response = (Response) result;
        assertEquals("success", response.getStatus());
        assertEquals("signup successful", response.getData());
    }

    @Test
    public void testRegisterExistingUser() throws Exception {
        User user = new User();
        user.setEmail("test@example.com");
        user.setPassword("password");

        when(userRepository.findByEmail(user.getEmail())).thenReturn(user);
        UserServiceImplementation userServiceImplementation = new UserServiceImplementation();
        ReflectionTestUtils.setField(userServiceImplementation, "userRepository", userRepository);
        Object result = userServiceImplementation.signup(user);
        assertNotNull(result);
        ResponseEntity response = (ResponseEntity) result;
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        Response body = (Response) response.getBody();
        assertEquals("error", body.getStatus());
        assertEquals("user already exists", body.getData());

    }

    @Test
    public void testUpdateProfile() throws Exception {
        User existingUser = new User();
        existingUser.setId(1L);
        existingUser.setName("Vidhi");
        existingUser.setEmail("vidhi@dal.ca");
        existingUser.setBio("bio");
        existingUser.setLocation("halifax");
        existingUser.setAvatar("https://example.com/avatar.png");

        User updatedUser = new User();
        updatedUser.setId(existingUser.getId());
        updatedUser.setName("Demo");
        updatedUser.setEmail(existingUser.getEmail());
        updatedUser.setBio("Demo bio");
        updatedUser.setLocation("Halifax, NS");
        updatedUser.setAvatar("https://example.com/avatar2.png");

        when(userRepository.findByEmail(updatedUser.getEmail())).thenReturn(existingUser);
        when(userRepository.save(existingUser)).thenReturn(existingUser);

        UserServiceImplementation userServiceImplementation = new UserServiceImplementation();
        ReflectionTestUtils.setField(userServiceImplementation, "userRepository", userRepository);

        User result = userServiceImplementation.updateProfile(updatedUser);

        assertNotNull(result);
        assertEquals(existingUser.getId(), result.getId());
        assertEquals(updatedUser.getName(), result.getName());
        assertEquals(updatedUser.getBio(), result.getBio());
        assertEquals(updatedUser.getLocation(), result.getLocation());
        assertEquals(updatedUser.getAvatar(), result.getAvatar());

    }

    @Test
    public void testUpdateProfileWhenUserDoesNotExist() throws Exception {
        User updatedUser = new User();
        updatedUser.setId(1L);
        updatedUser.setName("Demo");
        updatedUser.setEmail("demo@example.com");
        updatedUser.setBio("Demo bio");
        updatedUser.setLocation("Halifax, NS");
        updatedUser.setAvatar("https://example.com/avatar2.png");

        when(userRepository.findByEmail(updatedUser.getEmail())).thenReturn(null);

        UserServiceImplementation userServiceImplementation = new UserServiceImplementation();
        ReflectionTestUtils.setField(userServiceImplementation, "userRepository", userRepository);

        User result = userServiceImplementation.updateProfile(updatedUser);

        assertNull(result);
    }

    @Test
    public void testUpdateProfileImage() throws Exception {
        String id = "1";
        MultipartFile mockMultipartFile = Mockito.mock(MultipartFile.class);
        when(mockMultipartFile.getBytes()).thenReturn("Test".getBytes());
        when(mockMultipartFile.getOriginalFilename()).thenReturn("test.jpg");

        User user = new User();
        user.setId(Long.parseLong(id));
        user.setAvatar("http://example.com/profile_images/test.jpg");

        Mockito.when(userRepository.findById(Long.parseLong(id))).thenReturn(Optional.of(user));

        Object result = userServiceImplementation.updateProfileImage(mockMultipartFile, id);
        assertNotNull(result);
        assertTrue(result instanceof Response);
        Response response = (Response) result;
        assertEquals("success", response.getStatus());
    }

    @Test
    public void testUpdateProfileImageWithError() throws Exception {
        String userId = "1";
        MockMultipartFile file = new MockMultipartFile("file", "test.txt", "text/plain", "test data".getBytes());
        when(userRepository.findById(Long.parseLong(userId))).thenThrow(new RuntimeException("Failed to save image"));

        Object response = userServiceImplementation.updateProfileImage(file, userId);
        Response result = (Response) response;
        assertEquals("error", result.getStatus());
        assertEquals("Failed to save image", result.getData());
    }

}