package com.homeaid.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.homeaid.models.Household;

@Repository
public interface HouseholdRepository extends CrudRepository<Household, Long> {
	List<Household> findAll();
	
	Household findByMembersUsername(String username);
}
