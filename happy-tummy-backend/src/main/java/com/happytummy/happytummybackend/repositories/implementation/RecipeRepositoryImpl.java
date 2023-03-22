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
    EntityManager entityManager;

    @Override
    public List<Recipe> findByLimit(int limit, int pageIndex) {
        return entityManager.createQuery("SELECT p FROM Recipe p",
                Recipe.class).setMaxResults(limit).setFirstResult(pageIndex * limit).getResultList();
    }

    @Override
    public List<Recipe> findByTagName(String tagName, int limit, int pageIndex) {
        return entityManager.createQuery("SELECT p FROM Recipe p WHERE p.id IN (SELECT t.recipeId FROM Tag t WHERE t.tag = :tagName)",
                Recipe.class).setParameter("tagName", tagName).setMaxResults(limit).setFirstResult(pageIndex * limit).getResultList();
    }

    @Override
    public List<Recipe> findByIngredientName(String[] ingredientNames, int limit, int pageIndex) {
        System.out.println(limit);
        System.out.println(pageIndex * limit);
        return entityManager.createQuery("SELECT p FROM Recipe p WHERE p.id IN (SELECT i.recipeId FROM Ingredient i WHERE i.plain_ingredient IN :ingredientNames)",
                Recipe.class).setParameter("ingredientNames", Arrays.asList(ingredientNames)).setMaxResults(limit).setFirstResult(pageIndex * limit).getResultList();
    }

    @Override
    public List<Recipe> findByCombinedIngredientName(String[] ingredientNames, int limit, int pageIndex) {
        System.out.println(Arrays.asList(ingredientNames));
        return entityManager.createQuery("SELECT p FROM Recipe p WHERE p.id IN (SELECT i.recipeId FROM Ingredient i WHERE i.plain_ingredient IN :ingredientNames GROUP BY i.recipeId HAVING COUNT(i.recipeId) = :ingredientNamesLength) ",
                Recipe.class).setParameter("ingredientNames", Arrays.asList(ingredientNames)).setParameter("ingredientNamesLength", ingredientNames.length).setMaxResults(limit).setFirstResult(pageIndex * limit).getResultList();
    }

    @Override
    public List<Recipe> findBySearch(String search, int limit, int pageIndex) {
        return entityManager.createQuery("SELECT p FROM Recipe p WHERE p.name LIKE :search OR p.intro LIKE :search",
                Recipe.class).setParameter("search", "%" + search + "%").setMaxResults(limit).setFirstResult(pageIndex * limit).getResultList();
    }


}
