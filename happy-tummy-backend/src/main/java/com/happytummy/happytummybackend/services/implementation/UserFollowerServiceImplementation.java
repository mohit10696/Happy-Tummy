package com.happytummy.happytummybackend.services.implementation;

import com.happytummy.happytummybackend.models.User;
import com.happytummy.happytummybackend.models.UserFollower;
import com.happytummy.happytummybackend.repositories.UserFollowerRepository;
import com.happytummy.happytummybackend.services.UserFollowerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class UserFollowerServiceImplementation implements UserFollowerService {
    @Autowired
    private UserFollowerRepository userFollowerRepository;


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

    public List<UserFollower> getFollowersList(String userId) {
        User user = new User();
        user.setId(Long.valueOf(userId));
        return userFollowerRepository.findByFollowing(user);
    }

    public List<UserFollower> getFollowingList(String userId) {
        User user = new User();
        user.setId(Long.valueOf(userId));
        return userFollowerRepository.findByFollower(user);
    }

    public Long getFollowersCount(String userId) {
        return userFollowerRepository.countByFollowing_Id(Long.valueOf(userId));
    }

    public Long getFollowingCount(String userId) {
        return userFollowerRepository.countByFollower_Id(Long.valueOf(userId));
    }
}
