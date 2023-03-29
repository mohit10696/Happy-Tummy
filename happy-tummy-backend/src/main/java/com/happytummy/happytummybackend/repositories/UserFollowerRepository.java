package com.happytummy.happytummybackend.repositories;

import com.happytummy.happytummybackend.models.User;
import com.happytummy.happytummybackend.models.UserFollower;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserFollowerRepository extends JpaRepository<UserFollower, Long> {
    List<UserFollower> findByFollower(User follower);
    List<UserFollower> findByFollowing(User following);

    @Query("SELECT COUNT(u) FROM UserFollower u WHERE u.following.id = :userId")
    Long countByFollowing_Id(@Param("userId") Long userId);

    @Query("SELECT COUNT(u) FROM UserFollower u WHERE u.follower.id = :userId")
    Long countByFollower_Id(@Param("userId") Long userId);
}
