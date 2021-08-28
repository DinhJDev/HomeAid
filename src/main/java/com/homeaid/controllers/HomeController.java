package com.homeaid.controllers;

import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.homeaid.models.Event;
import com.homeaid.models.Item;
import com.homeaid.models.Member;
import com.homeaid.models.Task;
import com.homeaid.services.EventService;
import com.homeaid.services.HouseholdService;
import com.homeaid.services.ItemService;
import com.homeaid.services.MemberService;
import com.homeaid.services.TaskService;

@Controller
public class HomeController {
	@Autowired
	MemberService memberService;
	@Autowired
	HouseholdService householdService;
	@Autowired
	TaskService taskService;
	@Autowired
	EventService eventService;
	@Autowired
	ItemService itemService;
	
	@GetMapping(value = {"/", "/login"})
	public String welcomePage(@Valid @ModelAttribute("member") Member member, Model model) {
		model.addAttribute("member", new Member());
		return "welcomePage.jsp";
	}
	@PostMapping("/registration")
	public String registration(@Valid @ModelAttribute("login") Member member, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "welcomePage.jsp";
        }
        memberService.saveWithUserRole(member);
        return "redirect:/";
    }
	@RequestMapping("/login")
    public String login(@RequestParam(value="error", required=false) String error, @RequestParam(value="logout", required=false) String logout, Model model, HttpSession session) {
		if(error != null) {
            model.addAttribute("errorMessage", "Invalid Credentials, Please try again.");
        }
        if(logout != null) {
            model.addAttribute("logoutMessage", "Logout Successful!");
            return "redirect:/";
        }
//        session.invalidate();
        return "welcomePage.jsp";
    }
	@GetMapping("/dashboard")
	public String home(@Valid @ModelAttribute("task") Task task, Principal principal, Model model) {
		String username = principal.getName();
		model.addAttribute("currentUser", memberService.findByUsername(username));
		Member currUser = memberService.findByUsername(username);
		
		boolean foundHighestPriorityTask = false;
		boolean foundEasiestTask = false;
		if (this.taskService.descendingPriorityTasks().size() > 0) { // Needs to find the highest priority task ASSIGNED to you (not just anyone)
			for (Task t : this.taskService.descendingPriorityTasks()) {
				if (t.getAssignees().contains(currUser)) {
					model.addAttribute("highestPriorityTask", t);
					foundHighestPriorityTask = true;
					break;
				}
			}
		} 
		if (!foundHighestPriorityTask) {
			model.addAttribute("highestPriorityTask", new Task());
		}
		
		if (this.taskService.ascendingDifficultyTasks().size() > 0) {
			for (Task t : this.taskService.ascendingDifficultyTasks()) {
				if (t.getAssignees().contains(currUser)) {
					model.addAttribute("easiestTask", t);
					foundEasiestTask = true;
					break;
				}
			}
		} 
		if (!foundEasiestTask) {
			model.addAttribute("highestPriorityTask", new Task());
		}
		
		
		// Filter off old events before displaying
		Date now = java.util.Calendar.getInstance().getTime();
		for (Event e : this.eventService.allEventStartAsc()) {
			if (e.getEnd().compareTo(now) < 0) {
		         System.out.println("Found an old event that ends at " + e.getEnd());
		         this.eventService.removeEvent(e.getId());
		     } 			
		}
		Event upcoming = null; 
		if (this.eventService.allEventStartAscPublic(currUser.getId(), false) != null && this.eventService.allEventStartAscPublic(currUser.getId(), false).size() > 0) {
			for (Event e : this.eventService.allEventStartAscPublic(currUser.getId(), false)) {
				if (currUser.getHousehold() != null && e.getHost().getHousehold().getId().equals(currUser.getHousehold().getId())
						&& e.getAttendees().contains(currUser)) { // Found first event for this household
					upcoming = e; //
					break;
				}
			}
		}
		if (upcoming != null) {
			model.addAttribute("upcomingEvent", upcoming);
		}
		else {
			model.addAttribute("upcomingEvent", new Event());
		}
		
		if (memberService.findByUsername(username).getHousehold() != null) {
			List<Item> toDisplay = this.itemService.householdItems(memberService.findByUsername(username).getHousehold().getId());
			List<Item> allHouseholdItems = this.itemService.householdItems(memberService.findByUsername(username).getHousehold().getId());
			if (allHouseholdItems != null && allHouseholdItems.size() > 5) {
				toDisplay = allHouseholdItems.subList(0, 4);
			}
			model.addAttribute("expiringSoon", toDisplay);
		} else {
			model.addAttribute("expiringSoon", new ArrayList<>());
		}
		
		model.addAttribute("today", new Date());
		
		return "dashboardPage.jsp";
	}
	
	// TODO: Make usernames unique
	// TODO: Bind events to a house specifically so not everyone can see it (right now not secure on jsp)
	// TODO: add pfp
	// TODO: add recipe API
	// TODO: take out session
	// TODO: hide api key
	// Recurring tasks
	// Make tasks household centric or at least have a creator
	// Deploy
	// Modify privacy & only modify privacy if you're the host
	// Make private boolean checked to begin with if it was already priv
	
	// able to delete stuff from pantry
 
	
}
