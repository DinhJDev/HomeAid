package com.homeaid.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.homeaid.models.Household;
import com.homeaid.models.Item;
import com.homeaid.models.Member;
import com.homeaid.models.Recipe;
import com.homeaid.repositories.HouseholdRepository;

@Service
public class HouseholdService {
	private HouseholdRepository houseRepo;
	public HouseholdService(HouseholdRepository houseRepo) {
		this.houseRepo = houseRepo;
	}
	
	// Create
	public Household createHousehold(Household household) {
		return this.houseRepo.save(household);
	}	
	// Read
	public List<Household> allHouseHold(){
		return houseRepo.findAll();
	}
	public Household findbyMember(String username) {
		return houseRepo.findByMembersUsername(username);
	}
	// Update
	public Household updateHousehold(Household household) {
		return this.houseRepo.save(household);
	}
	// Delete
	public void deleteHousehold(Long id) {
		this.houseRepo.deleteById();
	}
	
	public void addMember(Member member, Household household) {
		List<Member> currentMembers = household.getMembers();
		currentMembers.add(member);
		this.houseRepo.save(household);
	}
	public void removeMember(Member member, Household household) {
		List<Member> currentMembers = household.getMembers();
		currentMembers.remove(member);
		this.houseRepo.save(household);
	}
	
	public void addIngredient(Item ingredient, Household household) {
		List<Item> currentIngredients = household.getIngredients();
		currentIngredients.add(ingredient);
		this.houseRepo.save(household);
	}
	public void removeIngredient(Item ingredient, Household household) {
		List<Item> currentIngredients = household.getIngredients();
		currentIngredients.remove(ingredient);
		this.houseRepo.save(household);
	}
	
	public void addRecipe(Recipe recipe, Household household) {
		List<Recipe> currentRecipes = household.getRecipes();
		currentRecipes.add(recipe);
		this.houseRepo.save(household);
	}
	public void removeRecipe(Recipe recipe, Household household) {
		List<Recipe> currentRecipes = household.getRecipes();
		currentRecipes.remove(recipe);
		this.houseRepo.save(household);
	}
}
