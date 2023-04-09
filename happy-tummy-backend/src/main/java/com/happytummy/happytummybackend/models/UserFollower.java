package com.happytummy.happytummybackend.models;

import jakarta.persistence.*;

import java.io.Serializable;

@Entity
@Table(name = "user_followers")
@IdClass(UserFollower.class)
public class UserFollower implements Serializable {

    @ManyToOne
    @JoinColumn(name = "follower_id")
    @Id
    private User follower;

    @ManyToOne
    @JoinColumn(name = "following_id")
    @Id
    private User following;

    public UserFollower( User follower, User following) {
        this.follower = follower;
        this.following = following;
    }

    public UserFollower() {

    }

    public User getFollower() {
        return follower;
    }

    public void setFollower(User follower) {
        this.follower = follower;
    }

    public User getFollowing() {
        return following;
    }

    public void setFollowing(User following) {
        this.following = following;
    }
}
