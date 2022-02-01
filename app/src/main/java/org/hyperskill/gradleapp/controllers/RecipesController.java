package org.hyperskill.gradleapp.controllers;

import org.hyperskill.gradleapp.service.RecipeService;
import org.hyperskill.gradleapp.service.UserDetailsServiceImpl;
import org.hyperskill.gradleapp.entity.Recipe;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.*;

@RestController
public class RecipesController {

    @Autowired
    RecipeService recipeService;
    @Autowired
    UserDetailsServiceImpl userDetailsServiceImpl;

    @PostMapping("/api/recipe/new")
    public Map<String, Long> addRecipe(@RequestBody @Valid Recipe recipe, @AuthenticationPrincipal UserDetails userDetails) {
        String email = userDetails.getUsername();
        long userId = userDetailsServiceImpl.getUser(email).getId();    // get the logged in user's id
        LocalDateTime date = LocalDateTime.now();
        recipe.setDate(date);
        recipe.setUserId(userId);

        long saveId = recipeService.saveRecipe(recipe);
        return Collections.singletonMap("id", saveId);
    }

    @PutMapping("/api/recipe/{id}")
    public Map<String, Object> updateRecipe(@RequestBody @Valid Recipe recipe, @PathVariable long id, @AuthenticationPrincipal UserDetails userDetails){
        String email = userDetails.getUsername();
        long userId = userDetailsServiceImpl.getUser(email).getId();    // get the logged in user's id
        System.out.println(userId);

        Recipe recipeTemp = recipeService.getRecipe(id);
        if(recipeTemp != null){
            if(recipeTemp.getUserId() == userId){
                LocalDateTime date = LocalDateTime.now();
                recipeTemp.setDate(date);
                recipeTemp.setName(recipe.getName());
                recipeTemp.setCategory(recipe.getCategory());
                recipeTemp.setDescription(recipe.getDescription());
                recipeTemp.setIngredients(recipe.getIngredients());
                recipeTemp.setDirections(recipe.getDirections());
                recipeService.saveRecipe(recipeTemp);
                throw new ResponseStatusException(HttpStatus.NO_CONTENT);
            }
            else{
                throw new ResponseStatusException(HttpStatus.FORBIDDEN);
            }
        }
        else if(recipeTemp == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/api/recipe/{id}")
    public Recipe getRecipe(@PathVariable long id) {
        if(recipeService.getRecipe(id) != null){
            return recipeService.getRecipe(id);
        }
        else{
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/api/recipe/search/")
    public List<Recipe> getRecipeByCategoryName(@RequestParam(required = false, name = "category") String category, @RequestParam(required = false, name = "name") String name){
        if(category != null){
            return recipeService.getByCategory(category);
        }
        else if(name != null){
            return recipeService.getByName(name);
        }
        else{
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/api/recipe/{id}")
    public ResponseEntity<?> deleteRecipe(@PathVariable long id, @AuthenticationPrincipal UserDetails userDetails){
        String email = userDetails.getUsername();
        long userId = userDetailsServiceImpl.getUser(email).getId();    // get the logged in user's id

        Recipe recipe = recipeService.getRecipe(id);

        if(recipe != null){
            if(userId == recipe.getUserId()){
                recipeService.deleteRecipe(id);
                return ResponseEntity.noContent().build();
            }
            else{
                throw new ResponseStatusException(HttpStatus.FORBIDDEN);
            }
        }
        return ResponseEntity.notFound().build();
    }
}
