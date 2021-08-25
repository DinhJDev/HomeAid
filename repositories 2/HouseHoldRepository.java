package com.homeaid.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.homeaid.models.Household;

@Repository
public interface HouseHoldRepository extends CrudRepository<Household, Long> {
	List<Household> findAll();
	
	List<Household> findByName(String name);
	
	Long deleteById();
	
	String removeByItem(String item);
}
