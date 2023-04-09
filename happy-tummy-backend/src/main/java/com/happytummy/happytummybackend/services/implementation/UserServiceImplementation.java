package com.happytummy.happytummybackend.services.implementation;

import com.happytummy.happytummybackend.CONSTANT;
import com.happytummy.happytummybackend.models.Recipe;
import com.happytummy.happytummybackend.models.Response;
import com.happytummy.happytummybackend.models.User;
import com.happytummy.happytummybackend.models.UserFollower;
import com.happytummy.happytummybackend.repositories.RecipeRepository;
import com.happytummy.happytummybackend.repositories.UserRepository;
import com.happytummy.happytummybackend.services.RecipeLikeService;
import com.happytummy.happytummybackend.services.RecipeService;
import com.happytummy.happytummybackend.services.UserFollowerService;
import com.happytummy.happytummybackend.services.UserService;
import com.happytummy.happytummybackend.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

/**
 * Implementation of UserService interface
 */
@Service
public class UserServiceImplementation implements UserService{
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RecipeRepository recipeRepository;

    @Autowired
    private UserFollowerService userFollowerService;

    @Autowired
    private RecipeLikeService recipeLikeService;

    @Autowired
    private RecipeService recipeService;

    @Autowired
    private JwtUtils jwtUtils;

    /**
     * Retrieves the profile data for a user with the given name.
     *
     * @param name The name of the user whose profile data is to be retrieved.
     * @return A map containing the profile data, including user information, recipe details, follower count,
     * following count, and number of likes.
     */
    @Override
    public Object getProfile(String name) {
        final int MAX_RECIPES_TO_DISPLAY = 20;
        Map<String, Object> responseData = new HashMap<>();
        System.out.println(name);
        Optional<User> user = userRepository.findByName(name);
        if (user.isPresent()) {
            responseData.put("user", user);
            List<Recipe> recipe = recipeRepository.findByUserId(user.get().getId());
            Integer totalRecipes = recipe.size();
            responseData.put("totalRecipes", totalRecipes);
            if(recipe.size() > MAX_RECIPES_TO_DISPLAY){
                recipe = recipe.subList(0, MAX_RECIPES_TO_DISPLAY);
            }
            List<Object> recipeDetails = new ArrayList<Object>();
            recipe.forEach(r -> {
                recipeDetails.add(recipeService.getRecipeById(String.valueOf(r.getId())));
            });

            responseData.put("recipes", recipeDetails);
            List<UserFollower> followers = userFollowerService.getFollowersList(String.valueOf(user.get().getId()));
            responseData.put("followers", followers);
            List<UserFollower> following = userFollowerService.getFollowingList(String.valueOf(user.get().getId()));
            responseData.put("following", following);
            Long likes = recipeLikeService.getNumLikesForUser(user.get().getId());
            responseData.put("likes", likes);
            return responseData;
        }
        return null;
    }

    /**
     * Authenticates a user by checking the provided email and password against the stored user data.
     *
     * @param user The user object containing the email and password for authentication.
     * @return A response object containing the authentication status, user data, and token if successful,
     * or an error message if unsuccessful.
     */
    @Override
    public Object login(User user) {
        User logged_in=userRepository.findByEmail(user.getEmail());
        HashMap<String, Object> responseData = new HashMap<>();
        String password = getMd5(user.getPassword());
        if(logged_in != null && logged_in.getPassword().equals(password)){
            responseData.put("user", logged_in);
            responseData.put("token", jwtUtils.generateToken(logged_in));
            return new Response("success", responseData);
        }
        else{
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new Response("error", "Invalid Password"));
        }
    }

    /**
     * Registers a new user by checking if the user with the provided email already exists,
     * and saves the new user to the database if not.
     *
     * @param user The user object containing the user information for registration.
     * @return A response object containing the registration status and user data if successful,
     * or an error message if the user with the provided email already exists.
     */
    @Override
    public Object signup(User user) {

        User isExist=userRepository.findByEmail(user.getEmail());
        if(isExist==null){
            user.setPassword(getMd5(user.getPassword()));
            userRepository.save(user);
            return new Response("success", user);
        }
        else{
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new Response("error", "user already exists"));
        }
    }

    /**
     * Calculates the MD5 hash value of the input string.
     *
     * @param input The input string to calculate the MD5 hash for.
     * @return The MD5 hash value of the input string.
     * @throws RuntimeException If the MD5 algorithm is not available.
     */
    public String getMd5(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] messageDigest = md.digest(input.getBytes());
            BigInteger no = new BigInteger(1, messageDigest);
            String hashtext = no.toString(16);
            while (hashtext.length() < 32) {
                hashtext = "0" + hashtext;
            }
            return hashtext;
        }
        catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Updates the profile of the user with the given information.
     *
     * @param user The user object containing the updated profile information.
     * @return The updated user object if the user exists, otherwise null.
     */
    @Override
    public User updateProfile(User user) {
        User isExist=userRepository.findByEmail(user.getEmail());
        if(isExist!=null){
            isExist.setName(user.getName());
            isExist.setBio(user.getBio());
            isExist.setEmail(user.getEmail());
            userRepository.save(isExist);
            return isExist;
        }
        else{
            return null;
        }
    }

    /**
     * Updates the profile image of the user with the provided image file.
     *
     * @param file The MultipartFile object representing the image file to upload.
     * @param id The ID of the user whose profile image is being updated.
     * @return A Response object indicating the result of the profile image update operation.
     */
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

                //CONSTANT constant = new CONSTANT();
                File dir = new File(CONSTANT.getBaseFolderPath() + "/profile_images");
                if (!dir.exists()) {
                    dir.mkdirs();
                }

                File uploadedFile = new File(dir.getAbsolutePath() + File.separator + fileName);
                BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(uploadedFile));
                stream.write(bytes);
                stream.close();

                User user = userRepository.findById(Long.parseLong(id)).get();
                user.setAvatar(CONSTANT.getBaseUrl() + CONSTANT.getBaseFolderPath() +"/profile_images/" + fileName);
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
