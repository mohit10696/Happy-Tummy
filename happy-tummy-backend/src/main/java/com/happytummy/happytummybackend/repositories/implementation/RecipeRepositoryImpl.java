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

/**
 * Implementation of RecipeRepositoryCustom.
 */
@Repository
public class RecipeRepositoryImpl implements RecipeRepositoryCustom {
    @PersistenceContext
    private EntityManager entityManager;

    /**
     * Find recipes with limit and pageIndex.
     *
     * @param limit     maximum number of results to return
     * @param pageIndex index of the page to retrieve
     * @return list of Recipe objects
     */
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
    }

    /**
     * Find recipes by tag name, with limit and pageIndex.
     *
     * @param tagName   tag name to search for
     * @param limit     maximum number of results to return
     * @param pageIndex index of the page to retrieve
     * @return list of Recipe objects
     */
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
    }

    /**
     * Find recipes by ingredient names, dietary category, with limit and pageIndex.
     *
     * @param ingredientNames   array of ingredient names to search for
     * @param dietaryCategory   array of dietary categories to filter by
     * @param limit             maximum number of results to return
     * @param pageIndex         index of the page to retrieve
     * @return list of Recipe objects
     */
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

    /**
     * Retrieves a list of recipes that contain a combination of ingredient names and match the given dietary categories.
     *
     * @param ingredientNames   An array of ingredient names to search for in the recipes.
     * @param dietaryCategory   An array of dietary categories to filter the recipes by.
     * @param limit             The maximum number of results to retrieve.
     * @param pageIndex         The index of the page of results to retrieve.
     * @return                  A list of recipes that match the given ingredient names and dietary categories.
     */
    @Override
    public List<Recipe> findByCombinedIngredientName(String[] ingredientNames,String[] dietaryCategory, int limit, int pageIndex) {
        String sub_query_1 = "SELECT i.recipeId FROM Ingredient i WHERE i.plain_ingredient IN :ingredientNames";
        String sub_query_2 = " GROUP BY i.recipeId HAVING COUNT(i.recipeId) = :ingredientNamesLength";

        String ingredientQueryString = sub_query_1 + sub_query_2;

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

        }
        if (dietaryCategory.length > 0) {
            query.setParameter("dietaryCategory", Arrays.asList(dietaryCategory));
        }

        int maxResults = limit;
        int firstResult = pageIndex * limit;
        query.setMaxResults(maxResults);
        query.setFirstResult(firstResult);

        return query.getResultList();

    }

    /**
     * Retrieves a list of recipes that match the given search term in recipe name or introduction.
     *
     * @param search        The search term to match in recipe name or introduction. Use "%" as a wildcard for partial matching.
     * @param limit         The maximum number of results to retrieve.
     * @param pageIndex     The index of the page of results to retrieve.
     * @return              A list of recipes that match the given search term.
     */
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

        return query.getResultList();
    }
}
