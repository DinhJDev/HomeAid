package com.homeaid.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.homeaid.models.Event;
import com.homeaid.models.Member;
import com.homeaid.models.Task;
import com.homeaid.repositories.EventRepository;

@Service
public class EventService {
	@Autowired
	EventRepository eventRepository;
	
	
	public List<Event> allEvent(){
		return this.eventRepository.findAll();
	}
	
	public List<Event> allEventStartAsc(){
		return this.eventRepository.findAllByOrderByStartAsc();
	}
	
	public List<Event> allEventStartAscPublic(Long hostId, Boolean privacy){
		return this.eventRepository.findByHost_IdOrPrivacyOrderByStartAsc(hostId, privacy);
	}
	
	
	public Event createEvent(Event event) {
		return this.eventRepository.save(event);
	}
	
	// Read - added by mabel
	public Event getOneEvent(Long id) {
		return this.eventRepository.findById(id).orElse(null);
	}
	
	public Event updateEvent(Long id, Event event) {
		return this.eventRepository.save(event);
	}
	
	public String removeEvent(Long id) {
		this.eventRepository.deleteById(id);
		return "Selected Event" + id + "is deleted";
	}
	
	public void addAttendee(Member attendee, Event event) {
		List<Member> currentAttendees = event.getAttendees();
		System.out.println(currentAttendees + "current attendees"); 
		// Prevent Duplicates
		if (!currentAttendees.contains(attendee)) {
			currentAttendees.add(attendee); 
		}
		this.eventRepository.save(event);
	}
	
	public void removeAttendee(Member attendee, Event event) {
		List<Member> currentAttendees = event.getAttendees();
		currentAttendees.remove(attendee);
		this.eventRepository.save(event);
	}
	
	

}
