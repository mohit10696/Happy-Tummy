package com.happytummy.happytummybackend.services.implementation;

import com.happytummy.happytummybackend.models.User;
import com.happytummy.happytummybackend.models.UserFollower;
import com.happytummy.happytummybackend.repositories.UserFollowerRepository;
import com.happytummy.happytummybackend.services.UserFollowerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Implementation class for UserFollowerService interface.
 */
@Service
public class UserFollowerServiceImplementation implements UserFollowerService {
    @Autowired
    private UserFollowerRepository userFollowerRepository;

    /**
     * Follows a user by creating a UserFollower relationship in the repository.
     *
     * @param followerId The ID of the user who wants to follow another user.
     * @param followingId The ID of the user who is being followed.
     * @return The UserFollower object representing the relationship between the follower and the following user.
     */
    @Override
    public UserFollower followUser(String followerId, String followingId) {
        User follower = new User();
        follower.setId(Long.valueOf(followerId));

        User following = new User();
        following.setId(Long.valueOf(followingId));

        UserFollower userFollower = new UserFollower();
        userFollower.setFollower(follower);
        userFollower.setFollowing(following);

        return userFollowerRepository.save(userFollower);
    }

    /**
     * Unfollows a user by deleting the UserFollower relationship in the repository.
     *
     * @param followerId The ID of the user who wants to unfollow another user.
     * @param followingId The ID of the user who is being unfollowed.
     * @return The UserFollower object representing the relationship that was deleted.
     */
    public UserFollower unfollowUser(String followerId, String followingId) {
        User follower = new User();
        follower.setId(Long.valueOf(followerId));

        User following = new User();
        following.setId(Long.valueOf(followingId));

        UserFollower userFollower = new UserFollower();
        userFollower.setFollower(follower);
        userFollower.setFollowing(following);

        userFollowerRepository.delete(userFollower);
        return userFollower;
    }

    /**
     * Retrieves the list of followers for a given user ID.
     *
     * @param userId The ID of the user whose followers are to be retrieved.
     * @return A list of UserFollower objects representing the followers of the user.
     */
    public List<UserFollower> getFollowersList(String userId) {
        User user = new User();
        user.setId(Long.valueOf(userId));
        return userFollowerRepository.findByFollowing(user);
    }

    /**
     * Retrieves the list of followers that a given user is following.
     *
     * @param userId The ID of the user whose following list is to be retrieved.
     * @return A list of UserFollower objects representing the users that the user is following.
     */
    public List<UserFollower> getFollowingList(String userId) {
        User user = new User();
        user.setId(Long.valueOf(userId));
        return userFollowerRepository.findByFollower(user);
    }

    /**
     * Retrieves the number of followers for a given user ID.
     *
     * @param userId The ID of the user whose followers are to be retrieved.
     * @return A Long representing the number of followers of the user.
     */
    public Long getFollowersCount(String userId) {
        return userFollowerRepository.countByFollowing_Id(Long.valueOf(userId));
    }

    /**
     * Retrieves the number of users that a given user is following.
     *
     * @param userId The ID of the user whose following list is to be retrieved.
     * @return A Long representing the number of users that the user is following.
     */
    public Long getFollowingCount(String userId) {
        return userFollowerRepository.countByFollower_Id(Long.valueOf(userId));
    }
}
