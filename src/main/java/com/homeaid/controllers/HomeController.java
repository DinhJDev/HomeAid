package com.homeaid.controllers;

import java.security.Principal;
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
	
	@GetMapping("/")
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
		
		if (this.taskService.descendingPriorityTasks().size() > 0) {
			model.addAttribute("highestPriorityTask", this.taskService.descendingPriorityTasks().get(0));
		} else {
			model.addAttribute("highestPriorityTask", new Task());
		}
		
		if (this.taskService.ascendingDifficultyTasks().size() > 0) {
			model.addAttribute("easiestTask", this.taskService.ascendingDifficultyTasks().get(0));
		} else {
			model.addAttribute("easiestTask", new Task());
		}
		
		// Filter off old events before displaying
		Date now = java.util.Calendar.getInstance().getTime();
		for (Event e : this.eventService.allEventStartAsc()) {
			if (e.getEnd().compareTo(now) < 0) {
		         System.out.println("Found an old event");
		         this.eventService.removeEvent(e.getId());
		     } 			
		}
		
		if (this.eventService.allEventStartAscPublic().size() > 0) {
			model.addAttribute("upcomingEvent", this.eventService.allEventStartAscPublic().get(0)); 
		} else {
			model.addAttribute("upcomingEvent", new Event());
			System.out.println(new Event().getTitle());
		}
		
		List<Item> toDisplay = new ArrayList<>();
		List<Item> allHouseholdItems = this.itemService.householdItems(memberService.findByUsername(username).getHousehold().getId());
		if (allHouseholdItems.size() > 5) {
			toDisplay = allHouseholdItems.subList(0, 4);
		}
		model.addAttribute("expiringSoon", toDisplay);
		
		return "dashboardPage.jsp";
	}
	
	// TODO: Make usernames unique
	// TODO: Bind events to a house specifically so not everyone can see it (right now not secure on jsp)
	// TODO: bug where any endpoint is still available
 
	
	
	
}
