package org.hyperskill.gradleapp.service;

import org.hyperskill.gradleapp.dao.RecipeRepository;
import org.hyperskill.gradleapp.entity.Recipe;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RecipeService {
    @Autowired
    RecipeRepository recipeRepository;

    public List<Recipe> getAllRecipes(){
        List<Recipe> allRecipes = new ArrayList<>();
        recipeRepository.findAll().forEach(allRecipes::add);
        return allRecipes;
    }

    // will return null if object not found
    public Recipe getRecipe(long id){
        if(recipeRepository.findById(id).isPresent()){
            return recipeRepository.findById(id).get();
        }
        else {
            return null;
        }
    }

    // return recipes by category, sort by date as newest first
    public List<Recipe> getByCategory(String category){
        List<Recipe> recipeLis = new ArrayList<>();
        recipeRepository.findByCategoryIgnoreCaseOrderByDateDesc(category).forEach(recipeLis::add);
        for(Recipe r : recipeLis){
            System.out.println(r.getName());
        }
        return recipeLis;
    }

    // return recipes by name, sort by date as newest first
    public List<Recipe> getByName(String name){
        List<Recipe> recipeLis = new ArrayList<>();
        recipeRepository.findByNameIgnoreCaseContainingOrderByDateDesc(name).forEach(recipeLis::add);
        for(Recipe r : recipeLis){
            System.out.println(r.getName());
        }
        return recipeLis;
    }

    // save recipe, return id of saved recipe
    public long saveRecipe(Recipe recipe){
        Recipe savedRecipe = recipeRepository.save(recipe);
        return savedRecipe.getId();
    }

    public void deleteRecipe(long id){
        recipeRepository.deleteById(id);
    }


}
