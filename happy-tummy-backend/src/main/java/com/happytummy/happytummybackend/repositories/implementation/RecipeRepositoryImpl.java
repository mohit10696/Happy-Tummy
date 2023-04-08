package com.happytummy.happytummybackend.repositories.implementation;

import com.happytummy.happytummybackend.models.Recipe;
import com.happytummy.happytummybackend.repositories.RecipeRepositoryCustom;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;

@Repository
public class RecipeRepositoryImpl implements RecipeRepositoryCustom {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Recipe> findByLimit(int limit, int pageIndex) {
        String queryString = "SELECT p FROM Recipe p";
        Class<Recipe> resultType = Recipe.class;
        int maxResults = limit;
        int firstResult = pageIndex * limit;

        TypedQuery<Recipe> query = entityManager.createQuery(queryString, resultType);
        query.setMaxResults(maxResults);
        query.setFirstResult(firstResult);

        return query.getResultList();
//        return entityManager.createQuery("SELECT p FROM Recipe p",
//                Recipe.class).setMaxResults(limit).setFirstResult(pageIndex * limit).getResultList();
    }

    @Override
    public List<Recipe> findByTagName(String tagName, int limit, int pageIndex) {
        String queryString = "SELECT p FROM Recipe p WHERE p.id IN (SELECT t.recipeId FROM Tag t WHERE t.tag = :tagName)";
        Class<Recipe> resultType = Recipe.class;
        int maxResults = limit;
        int firstResult = pageIndex * limit;
        String parameterName = "tagName";

        TypedQuery<Recipe> query = entityManager.createQuery(queryString, resultType);
        query.setParameter(parameterName, tagName);
        query.setMaxResults(maxResults);
        query.setFirstResult(firstResult);

        return query.getResultList();

//        return entityManager.createQuery("SELECT p FROM Recipe p WHERE p.id IN (SELECT t.recipeId FROM Tag t WHERE t.tag = :tagName)",
//                Recipe.class).setParameter("tagName", tagName).setMaxResults(limit).setFirstResult(pageIndex * limit).getResultList();
    }

    @Override
    public List<Recipe> findByIngredientName(String[] ingredientNames,String[] dietaryCategory, int limit, int pageIndex) {
        String queryString = "SELECT p FROM Recipe p where true ";
        if(ingredientNames.length > 0){
            queryString += "AND p.id IN (SELECT i.recipeId FROM Ingredient i WHERE i.plain_ingredient IN :ingredientNames)";
        }
        if(dietaryCategory.length > 0){
            queryString += "AND p.dietaryCategory IN :dietaryCategory";
        }
        System.out.println(queryString);
        Query query = entityManager.createQuery(queryString,Recipe.class);
        if(ingredientNames.length > 0){
            query.setParameter("ingredientNames", Arrays.asList(ingredientNames));
        }
        if(dietaryCategory.length > 0){
            query.setParameter("dietaryCategory", Arrays.asList(dietaryCategory));
        }
        return query.setMaxResults(limit).setFirstResult(pageIndex * limit).getResultList();
    }

    @Override
    public List<Recipe> findByCombinedIngredientName(String[] ingredientNames,String[] dietaryCategory, int limit, int pageIndex) {
        String queryString = "SELECT p FROM Recipe p WHERE p.id IN (SELECT i.recipeId FROM Ingredient i WHERE i.plain_ingredient IN :ingredientNames GROUP BY i.recipeId HAVING COUNT(i.recipeId) = :ingredientNamesLength)";
        if (dietaryCategory.length > 0) {
            queryString += " AND p.dietaryCategory IN :dietaryCategory";
        }
        Query query = entityManager.createQuery(queryString,Recipe.class);
        if(ingredientNames.length > 0){
            query.setParameter("ingredientNames", Arrays.asList(ingredientNames)).setParameter("ingredientNamesLength", ingredientNames.length);
        }
        if(dietaryCategory.length > 0){
            query.setParameter("dietaryCategory", Arrays.asList(dietaryCategory));
        }
        return query.setMaxResults(limit).setFirstResult(pageIndex * limit).getResultList();
    }

    @Override
    public List<Recipe> findBySearch(String search, int limit, int pageIndex) {
        return entityManager.createQuery("SELECT p FROM Recipe p WHERE p.name LIKE :search OR p.intro LIKE :search",
                Recipe.class).setParameter("search", "%" + search + "%").setMaxResults(limit).setFirstResult(pageIndex * limit).getResultList();
    }



}
