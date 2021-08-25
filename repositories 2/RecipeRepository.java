package com.homeaid.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.homeaid.models.Household;
import com.homeaid.models.Recipe;

@Repository
public interface RecipeRepository extends CrudRepository<Recipe, Long> {
	List<Recipe> findAll();

	Recipe save(Long id, Recipe recipe);

	List<Household> removeMemberRecipe(Long id, Recipe recipe);

}
