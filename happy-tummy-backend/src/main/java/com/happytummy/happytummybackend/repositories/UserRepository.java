package com.happytummy.happytummybackend.repositories;

import com.happytummy.happytummybackend.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findByEmail(String email);
    User findById(Long id);

}