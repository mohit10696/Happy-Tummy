package com.happytummy.happytummybackend.repositories;

import com.happytummy.happytummybackend.models.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TagRepository extends JpaRepository<Tag, Integer> {
    List<Tag> findByRecipeId(String recipeId);

}
