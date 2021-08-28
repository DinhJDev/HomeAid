package com.homeaid.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.homeaid.models.Event;
import com.homeaid.models.Item;

@Repository
public interface EventRepository extends CrudRepository <Event, Long>{
	List<Event> findAll();
	List<Event> findAllByOrderByStartAsc();
	List<Event> findByHost_IdOrPrivacyOrderByStartAsc(Long hostId, boolean privacy);
}
