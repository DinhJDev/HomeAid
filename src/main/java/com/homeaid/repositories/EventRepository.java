package com.homeaid.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.homeaid.models.Event;

@Repository
public interface EventRepository extends CrudRepository <Event, Long>{
	List<Event> findAll();
	List<Event> findAllByOrderByStartAsc();
	List<Event> findByPrivacyFalseOrderByStartAsc();
}
