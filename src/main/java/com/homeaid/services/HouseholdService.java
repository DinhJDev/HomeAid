package com.homeaid.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.homeaid.models.Household;
import com.homeaid.repositories.HouseholdRepository;

@Service
public class HouseholdService {
	@Autowired
	private HouseholdRepository hRepo;
	
	public List<Household> allHouseholds() {
		return this.hRepo.findAll();
	}
	
	// Read
	public Household getOneHousehold(Long id) {
		return this.hRepo.findById(id).orElse(null);
	}
	
	// Create
	public Household createHousehold(Household household) {
		return this.hRepo.save(household);
	}
	
	// Update
	public Household updateHousehold(Household household) {
		return this.hRepo.save(household);
	}
	
	// Delete
	public void deleteHousehold(Long id) {
		this.hRepo.deleteById(id);
	}

	
}
