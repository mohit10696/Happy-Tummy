package com.happytummy.happytummybackend.services.implementation;

import com.happytummy.happytummybackend.models.User;
import com.happytummy.happytummybackend.models.UserFollower;
import com.happytummy.happytummybackend.repositories.UserFollowerRepository;
import com.happytummy.happytummybackend.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

class UserFollowerServiceImplementationTest {

    @InjectMocks
    private UserFollowerServiceImplementation userFollowerService;

    @Mock
    private UserFollowerRepository userFollowerRepository;

    @Mock
    private UserRepository userRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }


    @Test
    public void testFollowUser() {
        // Create follower and following users
        User follower = new User();
        follower.setId(1L);

        User following = new User();
        following.setId(2L);

        // Set up userFollower repository to return the userFollower object when save method is called
        when(userFollowerRepository.save(any(UserFollower.class))).thenAnswer(invocation -> {
            UserFollower userFollower = invocation.getArgument(0);
            userFollower.setFollower(follower); // Set a mock ID for the userFollower object
            return userFollower;
        });

        // Call the followUser method
        String followerId = "1";
        String followingId = "2";
        UserFollower result = userFollowerService.followUser(followerId, followingId);

        // Assert that the result is not null
        assertNotNull(result);
        // Assert that the follower and following objects in the result are the same as the input follower and following objects
        assertEquals(follower.getId(), result.getFollower().getId());
        assertEquals(following.getId(), result.getFollowing().getId());

    }


    @Test
    public void testUnfollowUser() {
        // Create follower and following users
        User follower = new User();
        follower.setId(1L);

        User following = new User();
        following.setId(2L);

        // Create userFollower object
        UserFollower userFollower = new UserFollower();
        userFollower.setFollower(follower);
        userFollower.setFollowing(following);

        // Set up userFollower repository to delete the userFollower object when delete method is called
        doNothing().when(userFollowerRepository).delete(userFollower);

        // Call the unfollowUser method
        String followerId = "1";
        String followingId = "2";
        UserFollower result = userFollowerService.unfollowUser(followerId, followingId);

        // Assert that the result is not null
        assertNotNull(result);

        // Assert that the follower and following objects in the result are the same as the input follower and following objects
        assertEquals(follower.getId(), result.getFollower().getId());
        assertEquals(following.getId(), result.getFollowing().getId());

    }

    @Test
    public void testGetFollowersList() {
        // Create a user
        User user = new User();
        user.setId(1L);

        // Set up userFollower repository to return a list of userFollowers when findByFollowing method is called
        when(userFollowerRepository.findByFollowing(any(User.class))).thenAnswer(invocation -> {
            User following = invocation.getArgument(0);
            List<UserFollower> userFollowers = new ArrayList<>();
            // Create some mock UserFollower objects and add them to the list
            UserFollower userFollower1 = new UserFollower();
            User follower1 = new User();
            follower1.setId(2L);
            userFollower1.setFollower(follower1);
            userFollower1.setFollowing(following);
            userFollowers.add(userFollower1);

            UserFollower userFollower2 = new UserFollower();
            User follower2 = new User();
            follower2.setId(3L);
            userFollower2.setFollower(follower2);
            userFollower2.setFollowing(following);
            userFollowers.add(userFollower2);

            return userFollowers;
        });

        // Call the getFollowersList method
        String userId = "1";
        List<UserFollower> result = userFollowerService.getFollowersList(userId);

        // Assert that the result is not null
        assertNotNull(result);

        // Assert that the size of the result list is as expected
        assertEquals(2, result.size());

        // Assert that the followers in the result list have the correct follower IDs
        assertEquals(2L, result.get(0).getFollower().getId());
        assertEquals(3L, result.get(1).getFollower().getId());
    }

    @Test
    public void testGetFollowingList() {
        // Create a user
        User user = new User();
        user.setId(1L);

        // Set up userFollower repository to return a list of userFollowers when findByFollower method is called
        when(userFollowerRepository.findByFollower(any(User.class))).thenAnswer(invocation -> {
            User follower = invocation.getArgument(0);
            List<UserFollower> userFollowers = new ArrayList<>();
            // Create some mock UserFollower objects and add them to the list
            UserFollower userFollower1 = new UserFollower();
            User following1 = new User();
            following1.setId(2L);
            userFollower1.setFollower(follower);
            userFollower1.setFollowing(following1);
            userFollowers.add(userFollower1);

            UserFollower userFollower2 = new UserFollower();
            User following2 = new User();
            following2.setId(3L);
            userFollower2.setFollower(follower);
            userFollower2.setFollowing(following2);
            userFollowers.add(userFollower2);

            return userFollowers;
        });

        // Call the getFollowingList method
        String userId = "1";
        List<UserFollower> result = userFollowerService.getFollowingList(userId);

        // Assert that the result is not null
        assertNotNull(result);

        // Assert that the size of the result list is as expected
        assertEquals(2, result.size());

        // Assert that the following users in the result list have the correct following IDs
        assertEquals(2L, result.get(0).getFollowing().getId());
        assertEquals(3L, result.get(1).getFollowing().getId());
    }

    @Test
    public void testGetFollowersCount() {
        // Set up userFollower repository to return a count when countByFollowing_Id method is called
        when(userFollowerRepository.countByFollowing_Id(anyLong())).thenReturn(5L);

        // Call the getFollowersCount method
        String userId = "1";
        Long result = userFollowerService.getFollowersCount(userId);

        // Assert that the result is not null
        assertNotNull(result);

        // Assert that the result is equal to the expected count value
        assertEquals(5L, result.longValue());
    }

    @Test
    public void testGetFollowingCount() {
        // Set up userFollower repository to return a count when countByFollower_Id method is called
        when(userFollowerRepository.countByFollower_Id(anyLong())).thenReturn(3L);

        // Call the getFollowingCount method
        String userId = "1";
        Long result = userFollowerService.getFollowingCount(userId);

        // Assert that the result is not null
        assertNotNull(result);

        // Assert that the result is equal to the expected count value
        assertEquals(3L, result.longValue());
    }


}