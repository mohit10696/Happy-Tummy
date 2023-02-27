package com.happytummy.happytummybackend.repositories.implementation;

import com.happytummy.happytummybackend.models.Recipe;
import com.happytummy.happytummybackend.repositories.RecipeRepositoryCustom;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;

@Repository
public class RecipeRepositoryImpl implements RecipeRepositoryCustom {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Recipe> findByLimit(int limit) {
        return entityManager.createQuery("SELECT p FROM Recipe p",
                Recipe.class).setMaxResults(limit).getResultList();
    }

    @Override
    public List<Recipe> findByTagName(String tagName, int limit) {
        return entityManager.createQuery("SELECT p FROM Recipe p WHERE p.id IN (SELECT t.recipeId FROM Tag t WHERE t.tag = :tagName)",
                Recipe.class).setParameter("tagName", tagName).setMaxResults(limit).getResultList();
    }

    @Override
    public List<Recipe> findByIngredientName(String[] ingredientNames, int limit) {
        return entityManager.createQuery("SELECT p FROM Recipe p WHERE p.id IN (SELECT i.recipeId FROM Ingredient i WHERE i.plain_ingredient IN :ingredientNames)",
                Recipe.class).setParameter("ingredientNames", Arrays.asList(ingredientNames)).setMaxResults(limit).getResultList();
    }

    @Override
    public List<Recipe> findBySearch(String search, int limit) {
        return entityManager.createQuery("SELECT p FROM Recipe p WHERE p.name LIKE :search OR p.intro LIKE :search",
                Recipe.class).setParameter("search", "%" + search + "%").setMaxResults(limit).getResultList();
    }


}
