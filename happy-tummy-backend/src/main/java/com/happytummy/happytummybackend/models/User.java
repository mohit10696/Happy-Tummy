package com.happytummy.happytummybackend.models;

import jakarta.persistence.*;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String password;
    @Column(nullable = false)
    private String email;

    private String avatar;
    private String location;
    private String bio;

    private String latitude;
    private String longitude;

    public User(String name, String password) {
        this.name = name;
        this.password = password;
    }

    public String getLatitude() {
        return latitude;
    }

    public String coverImage;

    public String getCoverImage() {
        return coverImage;
    }

    public User(Long id, String name, String password, String email, String avatar, String location, String bio, String latitude, String longitude, String coverImage) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.email = email;
        this.avatar = avatar;
        this.location = location;
        this.bio = bio;
        this.latitude = latitude;
        this.longitude = longitude;
        this.coverImage = coverImage;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public User(Long id, String name, String password, String email, String avatar, String location, String bio, String latitude, String longitude) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.email = email;
        this.avatar = avatar;
        this.location = location;
        this.bio = bio;
        this.latitude = latitude;
        this.longitude = longitude;
    }


    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public User() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    public String getAvatar(){
        return avatar;
    }
    public void setAvatar(String avatar){this.avatar=avatar;}

    public User(Long id, String name, String password, String email, String avatar, String location, String bio) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.email = email;
        this.avatar = avatar;
        this.location = location;
        this.bio = bio;
    }
}