package com.happytummy.happytummybackend.services;

import com.happytummy.happytummybackend.models.UserFollower;

import java.util.List;

public interface UserFollowerService {

     UserFollower followUser(String followerId, String followingId);
     UserFollower unfollowUser(String followerId, String followingId);
     List<UserFollower> getFollowersList(String userId);
     List<UserFollower> getFollowingList(String userId);
     Long getFollowersCount(String userId);
     Long getFollowingCount(String userId);


}
