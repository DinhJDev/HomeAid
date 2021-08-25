package com.homeaid.services;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.homeaid.models.Household;
import com.homeaid.models.Item;
import com.homeaid.models.Member;
import com.homeaid.models.Recipe;
import com.homeaid.repositories.HouseholdRepository;
import com.homeaid.repositories.ItemRepository;
import com.homeaid.repositories.MemberRepository;
import com.homeaid.repositories.RecipeRepository;

@Service
public class HouseholdService {
	@Autowired
	private MemberRepository memberRepository;
	@Autowired
	private ItemRepository itemRepository;
	@Autowired
	private RecipeRepository recipeRepository;
	
	private HouseholdRepository houseRepo;
	public HouseholdService(HouseholdRepository houseRepo) {
		this.houseRepo = houseRepo;
	}
	
	public List<Household> allHouseHold(){
		return houseRepo.findAll();
	}
	//find by id?
	public Household findbyMember(String username){
		return houseRepo.findByMembersUsername(username);
	}
	public Member addMember(Member members){
		return this.memberRepository.save(members);
	}
	
	public Member updateMember(@Valid Long id, Member members) {
		return this.memberRepository.save(members);
	}
	
	public Item addItem(@Valid Long id, Item ingredients) {
		return this.itemRepository.save(id, ingredients);
	}
	public List<Household> removeMemberItem(Long id, Item item){
		return this.removeMemberItem(id, item);
		
	}
	public String removeItem(String item){
		return houseRepo.removeByItem(item);
	}
	
	public Recipe addRecipe(@Valid Long id, Recipe recipe) {
		return this.recipeRepository.save(id, recipe);
	}
	public List<Household> removeMemberRecipe(Long id, Recipe recipe){
		return recipeRepository.removeMemberRecipe(id, recipe);
	}
	
	public String deleteMember(Long id) {
		this.houseRepo.deleteById(id);
		return "Memeber" + id + "has been deleted.";
	}
	
	
}
