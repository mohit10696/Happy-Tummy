package com.happytummy.happytummybackend.repositories;

import com.happytummy.happytummybackend.models.Step;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StepRepository extends JpaRepository<Step, Integer> {
    List<Step> findByRecipeId(String recipeId);
}