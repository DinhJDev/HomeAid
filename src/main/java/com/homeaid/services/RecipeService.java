package com.homeaid.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.homeaid.models.Recipe;
import com.homeaid.repositories.RecipeRepository;

@Service
public class RecipeService {
	@Autowired
	RecipeRepository recipeRepository;
	
	public List<Recipe> allRecipe(){
		return this.recipeRepository.findAll();
	}
	
	public Recipe createRecipe (Recipe recipe) {
		return this.recipeRepository.save(recipe);
	}
	
	public Recipe getOneRecipe(Long id) {
		return this.recipeRepository.findById(id).orElse(null);
	}
	
	public Recipe updateRecipe (Recipe recipe) {
		return this.recipeRepository.save(recipe);
	}
	
	public void deleteRecipe(Long id) {
		this.recipeRepository.deleteById(id);
	}
	
	
}
