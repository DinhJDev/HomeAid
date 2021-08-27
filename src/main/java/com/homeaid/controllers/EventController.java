package com.homeaid.controllers;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.homeaid.models.Event;
import com.homeaid.models.Member;
import com.homeaid.services.EventService;
import com.homeaid.services.HouseholdService;
import com.homeaid.services.MemberService;
import com.homeaid.services.TaskService;

@Controller
@RequestMapping("/events")
public class EventController {
	@Autowired
	private MemberService memberService;
	@Autowired
	private HouseholdService householdService;
	@Autowired
	private TaskService taskService;
	@Autowired
	private EventService eventService;
	

	/** Create an Event */
	/** Main Tasks Creation - Accessible from the Dashboard */
	@GetMapping("/create")
	private String createEvent(Model viewModel, @ModelAttribute("event") Event event, HttpSession session, Principal principal) {
		String username = principal.getName();
		viewModel.addAttribute("currentUser", memberService.findByUsername(username));
		viewModel.addAttribute("houseMembers", this.householdService.findbyMember(username).getMembers());
	
		return "createEvent.jsp";
	}
	
	@PostMapping("/create/new")
	public String addEvent(@Valid @ModelAttribute("event") Event event, BindingResult result, Model viewModel, HttpSession session, Principal principal, RedirectAttributes redirectAttr, @RequestParam("start") @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm") Date startDate, @RequestParam("end") @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm") Date endDate) {
		String username = principal.getName();
		Member currUser = memberService.findByUsername(username);

		if (result.hasErrors()) { 
			System.out.println("errors creating event" + result.getAllErrors());
			
			viewModel.addAttribute("currentUser", memberService.findByUsername(username));
			viewModel.addAttribute("houseMembers", this.householdService.findbyMember(username).getMembers());
			
			return "createEvent.jsp";
		}
		System.out.println("Created event");
		
		event.setHost(currUser);
		List<Member> attendees = new ArrayList<>();
		attendees.add(currUser);
		event.setAttendees(attendees);
		Event newEvent = this.eventService.createEvent(event);
		this.eventService.getOneEvent(newEvent.getId());
		
		// You are automatically attending your own event
		
		
		redirectAttr.addAttribute("successMessage", "Created event successfully!");
		return "redirect:/dashboard";
	}
	
	/** EDITING AN EVENT AND ATTENDING IT */
	@GetMapping("/edit/{event_id}")
	private String editTask(@PathVariable("event_id") Long eventId, Model viewModel, @ModelAttribute("event") Event event, HttpSession session, Principal principal) {
		// Make sure it won't keep creating new events
		String username = principal.getName();
		viewModel.addAttribute("currentUser", memberService.findByUsername(username));
		viewModel.addAttribute("event", this.eventService.getOneEvent(eventId));
		viewModel.addAttribute("houseMembers", this.householdService.findbyMember(username).getMembers());
		viewModel.addAttribute("edit", "True");
		return "editEvent.jsp";
		
	}
	
	@PostMapping("/edit/{event_id}")
	private String editTaskPost(@RequestParam("going") boolean going, @RequestParam("modifyprivacy") boolean modifyprivacy, @PathVariable("event_id") Long eventId, @ModelAttribute("event") Event event, BindingResult result, Model viewModel, HttpSession session, Principal principal, RedirectAttributes redirectAttr) {
		// Make sure it won't keep creating new tasks
		String username = principal.getName();
		Member currMember = memberService.findByUsername(username);
		if (result.hasErrors()) { 
			System.out.println("Errors");
			viewModel.addAttribute("currentUser", memberService.findByUsername(username));
			viewModel.addAttribute("event", this.eventService.getOneEvent(eventId));
			viewModel.addAttribute("houseMembers", this.householdService.findbyMember(username).getMembers());
			return "editEvent.jsp";
		}
		
		java.text.SimpleDateFormat sdf = 
			     new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm");
//		java.util.Date startdt = new java.util.Date();
//		java.util.Date enddt = new java.util.Date();
		event.setStart(event.getStart());
		event.setEnd(event.getEnd());
		if (currMember.getUsername().equals(event.getHost().getUsername())) {
			event.setPrivacy(modifyprivacy);
		}
			
		if (going) {
			this.eventService.addAttendee(currMember, this.eventService.getOneEvent(eventId));
		} else {
			this.eventService.removeAttendee(currMember, this.eventService.getOneEvent(eventId));
		}
		this.eventService.updateEvent(eventId, event);
		return "redirect:/dashboard";
		
	}
	
	@GetMapping("/all")
	private String showAllEvents(Model viewModel, HttpSession session, Principal principal) {
		String username = principal.getName();
		viewModel.addAttribute("currentUser", memberService.findByUsername(username));
		viewModel.addAttribute("allEvents", eventService.allEventStartAscPublic());
	
		return "allEvents.jsp";
	}
	
	@GetMapping("/delete/{event_id}")
	private String deleteEvent(@PathVariable("event_id") Long eventId, Model viewModel, HttpSession session, Principal principal, RedirectAttributes redirectAttr) {
		String eventName = this.eventService.getOneEvent(eventId).getTitle();
		this.eventService.removeEvent(eventId);
		System.out.println("Deleted event");
		redirectAttr.addFlashAttribute("deleteEventSuccess", "Successfully deleted event " + eventName);
		return "redirect:/dashboard";
	}
	

	// How to fix the time problem: https://stackoverflow.com/questions/6252678/converting-a-date-string-to-a-datetime-object-using-joda-time-library
}



