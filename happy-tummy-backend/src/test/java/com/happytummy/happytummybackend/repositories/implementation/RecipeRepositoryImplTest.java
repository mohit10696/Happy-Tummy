package com.happytummy.happytummybackend.repositories.implementation;

import com.google.gson.Gson;
import com.happytummy.happytummybackend.models.Recipe;
import com.happytummy.happytummybackend.services.RecipeService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
class RecipeRepositoryImplTest {

    @Mock
    private EntityManager entityManager;

    @InjectMocks
    private RecipeRepositoryImpl recipeRepositoryImpl;


    @Test
    void findByLimitTest() {
        // create mock Query object
        TypedQuery<Recipe> mockQuery = mock(TypedQuery.class);

        // create list of recipes to be returned
        List<Recipe> recipes = new ArrayList<>();
        recipes.add(new Recipe(1,1,"Intro"));
        recipes.add(new Recipe(2,2,"Intro"));

        // set up mock behavior for entityManager.createQuery()
        when(entityManager.createQuery("SELECT p FROM Recipe p", Recipe.class)).thenReturn(mockQuery);
        when(mockQuery.setMaxResults(2)).thenReturn(mockQuery);
        when(mockQuery.setFirstResult(0)).thenReturn(mockQuery);
        when(mockQuery.getResultList()).thenReturn(recipes);

        // call the method and check the output
        List<Recipe> result = recipeRepositoryImpl.findByLimit(2, 0);
        assertEquals(2, result.size());
        assertEquals(1, result.get(0).getId());
        assertEquals(2, result.get(1).getId());
        assertEquals("Intro", result.get(0).getName());
        assertEquals("Intro", result.get(1).getName());
        assertEquals(1, result.get(0).getUserId());
        assertEquals(2, result.get(1).getUserId());
    }

    @Test
    void findByTagNameTest() {
        // create mock Query object
        TypedQuery<Recipe> mockQuery = mock(TypedQuery.class);

        // create list of recipes to be returned
        List<Recipe> recipes = new ArrayList<>();
        recipes.add(new Recipe(1,1,"Intro"));
        recipes.add(new Recipe(2,2,"Intro"));

        // set up mock behavior for entityManager.createQuery()
        when(entityManager.createQuery("SELECT p FROM Recipe p WHERE p.id IN (SELECT t.recipeId FROM Tag t WHERE t.tag = :tagName)", Recipe.class)).thenReturn(mockQuery);
        when(mockQuery.setParameter("tagName", "tag")).thenReturn(mockQuery);
        when(mockQuery.setMaxResults(2)).thenReturn(mockQuery);
        when(mockQuery.setFirstResult(0)).thenReturn(mockQuery);
        when(mockQuery.getResultList()).thenReturn(recipes);

        // call the method and check the output
        List<Recipe> result = recipeRepositoryImpl.findByTagName("tag", 2, 0);
        assertEquals(2, result.size());
        assertEquals(1, result.get(0).getId());
        assertEquals(2, result.get(1).getId());
        assertEquals("Intro", result.get(0).getName());
        assertEquals("Intro", result.get(1).getName());
        assertEquals(1, result.get(0).getUserId());
        assertEquals(2, result.get(1).getUserId());
    }

    @Test
    void findByIngredientNameTest() {
        // create mock Query object
        TypedQuery<Recipe> mockQuery = mock(TypedQuery.class);

        // create list of recipes to be returned
        List<Recipe> recipes = new ArrayList<>();
        recipes.add(new Recipe(1,1,"Intro"));
        recipes.add(new Recipe(2,2,"Intro"));

        // set up mock behavior for entityManager.createQuery()
        when(entityManager.createQuery("SELECT p FROM Recipe p where true AND p.id IN (SELECT i.recipeId FROM Ingredient i WHERE i.plain_ingredient IN :ingredientNames)", Recipe.class)).thenReturn(mockQuery);
        when(mockQuery.setParameter("ingredientNames", Arrays.asList("ingredient"))).thenReturn(mockQuery);
        when(mockQuery.setMaxResults(2)).thenReturn(mockQuery);
        when(mockQuery.setFirstResult(0)).thenReturn(mockQuery);
        when(mockQuery.getResultList()).thenReturn(recipes);

        // call the method and check the output
        List<Recipe> result = recipeRepositoryImpl.findByIngredientName(new String[]{"ingredient"},new String[]{},2, 0);
        assertEquals(2, result.size());
        assertEquals(1, result.get(0).getId());
        assertEquals(2, result.get(1).getId());
        assertEquals("Intro", result.get(0).getName());
        assertEquals("Intro", result.get(1).getName());
        assertEquals(1, result.get(0).getUserId());
        assertEquals(2, result.get(1).getUserId());
    }

    @Test
    void findByCombinedIngredientNameTest() {
        // create mock Query object
        TypedQuery<Recipe> mockQuery = mock(TypedQuery.class);

        // create list of recipes to be returned
        List<Recipe> recipes = new ArrayList<>();
        recipes.add(new Recipe(1,1,"Intro"));
        recipes.add(new Recipe(2,2,"Intro"));

        // set up mock behavior for entityManager.createQuery()
        when(entityManager.createQuery("SELECT p FROM Recipe p WHERE p.id IN (SELECT i.recipeId FROM Ingredient i WHERE i.plain_ingredient IN :ingredientNames GROUP BY i.recipeId HAVING COUNT(i.recipeId) = :ingredientNamesLength) AND p.dietaryCategory IN :dietaryCategory", Recipe.class)).thenReturn(mockQuery);
        when(mockQuery.setParameter("ingredientNames", Arrays.asList("ingredient"))).thenReturn(mockQuery);
        when(mockQuery.setParameter("ingredientNamesLength", 1)).thenReturn(mockQuery);
        when(mockQuery.setMaxResults(2)).thenReturn(mockQuery);
        when(mockQuery.setFirstResult(0)).thenReturn(mockQuery);
        when(mockQuery.getResultList()).thenReturn(recipes);

        // call the method and check the output
        List<Recipe> result = recipeRepositoryImpl.findByCombinedIngredientName(new String[]{"ingredient"},new String[]{"veg"}, 2, 0);
        assertEquals(2, result.size());
        assertEquals(1, result.get(0).getId());
        assertEquals(2, result.get(1).getId());
        assertEquals("Intro", result.get(0).getName());
        assertEquals("Intro", result.get(1).getName());
        assertEquals(1, result.get(0).getUserId());
        assertEquals(2, result.get(1).getUserId());
    }

    @Test
    void findBySearchTest() {
        // create mock Query object
        TypedQuery<Recipe> mockQuery = mock(TypedQuery.class);

        // create list of recipes to be returned
        List<Recipe> recipes = new ArrayList<>();
        recipes.add(new Recipe(1,1,"Intro"));
        recipes.add(new Recipe(2,2,"Intro"));

        // set up mock behavior for entityManager.createQuery()
        when(entityManager.createQuery("SELECT p FROM Recipe p WHERE p.name LIKE :search OR p.intro LIKE :search", Recipe.class)).thenReturn(mockQuery);
        when(mockQuery.setParameter("search", "%" + "search" + "%")).thenReturn(mockQuery);
        when(mockQuery.setMaxResults(2)).thenReturn(mockQuery);
        when(mockQuery.setFirstResult(0)).thenReturn(mockQuery);
        when(mockQuery.getResultList()).thenReturn(recipes);

        // call the method and check the output
        List<Recipe> result = recipeRepositoryImpl.findBySearch("search", 2, 0);
        assertEquals(2, result.size());
        assertEquals(1, result.get(0).getId());
        assertEquals(2, result.get(1).getId());
        assertEquals("Intro", result.get(0).getName());
        assertEquals("Intro", result.get(1).getName());
        assertEquals(1, result.get(0).getUserId());
        assertEquals(2, result.get(1).getUserId());
    }





}