package org.hyperskill.gradleapp.dao;

import org.hyperskill.gradleapp.entity.Recipe;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecipeRepository extends CrudRepository<Recipe, Long> {
    List<Recipe> findByCategoryIgnoreCaseOrderByDateDesc(String category);

    List<Recipe> findByNameIgnoreCaseContainingOrderByDateDesc(String name);
}
