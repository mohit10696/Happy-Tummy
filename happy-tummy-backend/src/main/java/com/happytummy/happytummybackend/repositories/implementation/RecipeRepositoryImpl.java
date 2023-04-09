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
    EntityManager entityManager;

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
        String sub_query_1 = "SELECT i.recipeId FROM Ingredient i WHERE i.plain_ingredient IN :ingredientNames";
        String sub_query_2 = " GROUP BY i.recipeId HAVING COUNT(i.recipeId) = :ingredientNamesLength";

        String ingredientQueryString = sub_query_1 + sub_query_2;

        //String ingredientQueryString = "SELECT i.recipeId FROM Ingredient i WHERE i.plain_ingredient IN :ingredientNames GROUP BY i.recipeId HAVING COUNT(i.recipeId) = :ingredientNamesLength";
        String baseQueryString = "SELECT p FROM Recipe p WHERE p.id IN (%s)";
        String dietaryCategoryQueryString = " AND p.dietaryCategory IN :dietaryCategory";

        String combinedIngredientQueryString = String.format(baseQueryString, ingredientQueryString);
        if (dietaryCategory.length > 0) {
            combinedIngredientQueryString += dietaryCategoryQueryString;
        }

        TypedQuery<Recipe> query = entityManager.createQuery(combinedIngredientQueryString, Recipe.class);
        if (ingredientNames.length > 0) {
            List<String> ingredientNamesList = Arrays.asList(ingredientNames);
            query.setParameter("ingredientNames", ingredientNamesList);
            query.setParameter("ingredientNamesLength", ingredientNamesList.size());

//            query.setParameter("ingredientNames", Arrays.asList(ingredientNames))
//                    .setParameter("ingredientNamesLength", ingredientNames.length);
        }
        if (dietaryCategory.length > 0) {
            query.setParameter("dietaryCategory", Arrays.asList(dietaryCategory));
        }

        int maxResults = limit;
        int firstResult = pageIndex * limit;
        query.setMaxResults(maxResults);
        query.setFirstResult(firstResult);

        return query.getResultList();


//        String queryString = "SELECT p FROM Recipe p WHERE p.id IN (SELECT i.recipeId FROM Ingredient i WHERE i.plain_ingredient IN :ingredientNames GROUP BY i.recipeId HAVING COUNT(i.recipeId) = :ingredientNamesLength)";
//        if (dietaryCategory.length > 0) {
//            queryString += " AND p.dietaryCategory IN :dietaryCategory";
//        }
//        Query query = entityManager.createQuery(queryString,Recipe.class);
//        if(ingredientNames.length > 0){
//            query.setParameter("ingredientNames", Arrays.asList(ingredientNames)).setParameter("ingredientNamesLength", ingredientNames.length);
//        }
//        if(dietaryCategory.length > 0){
//            query.setParameter("dietaryCategory", Arrays.asList(dietaryCategory));
//        }
//        return query.setMaxResults(limit).setFirstResult(pageIndex * limit).getResultList();
    }

    @Override
    public List<Recipe> findBySearch(String search, int limit, int pageIndex) {
        String queryString = "SELECT p FROM Recipe p WHERE p.name LIKE :search OR p.intro LIKE :search";
        TypedQuery<Recipe> query;
        if (search == null) {
            query = entityManager.createQuery("SELECT r FROM Recipe r", Recipe.class);
        } else {
            query = entityManager.createQuery(queryString, Recipe.class)
                    .setParameter("search", "%" + search + "%");
        }
        int firstResult = pageIndex * limit;
        query.setMaxResults(limit);
        query.setFirstResult(firstResult);


//        TypedQuery<Recipe> query = entityManager.createQuery(queryString, Recipe.class)
//                .setParameter("search", "%" + search + "%")
//                .setMaxResults(limit)
//                .setFirstResult(pageIndex * limit);
        return query.getResultList();

//        return entityManager.createQuery("SELECT p FROM Recipe p WHERE p.name LIKE :search OR p.intro LIKE :search",
//                Recipe.class).setParameter("search", "%" + search + "%").setMaxResults(limit).setFirstResult(pageIndex * limit).getResultList();
    }



}
