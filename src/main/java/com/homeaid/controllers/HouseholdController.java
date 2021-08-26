package com.homeaid.controllers;

import java.security.Principal;

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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.homeaid.models.Household;
import com.homeaid.models.Member;
import com.homeaid.services.HouseholdService;
import com.homeaid.services.MemberService;

@Controller
@RequestMapping("/households")
public class HouseholdController {
	/**
	 * TODO: 
	 * Check for duplicate households
	 * Protect this endpoint
	 * Prevent one person from joining multiple households (right now it just switches you and hides the button)
	 * 
	 */
	@Autowired
	MemberService memberService;
	@Autowired
	HouseholdService householdService;
	
	
	/** Main Households Creation - Accessible from the Dashboard */
	@GetMapping("/create")
	private String createHouseholdPage(Model viewModel, @ModelAttribute("household") Household household, HttpSession session, Principal principal) {
		String username = principal.getName();
		viewModel.addAttribute("currentUser", memberService.findByUsername(username));
		return "createHousehold.jsp";
	}
	
	@PostMapping("/create/new") // Now a complete object
	private String createHousehold(@Valid @ModelAttribute("household") Household household, BindingResult result, Model viewModel, HttpSession session, Principal principal,  RedirectAttributes redirectAttributes) {
		String username = principal.getName();
		if (result.hasErrors()) { // need this stuff
			viewModel.addAttribute("currentUser", memberService.findByUsername(username));
			return "createHousehold.jsp";
		}
		
		// Try assigning relationship on the member instead
		Member currMember = memberService.findByUsername(username);
		if (currMember.getHousehold() != null) {
			redirectAttributes.addFlashAttribute("failureMessage", "🚨 ERROR: You are already in a household");
			return "redirect:/dashboard";
		}
		currMember.setHousehold(household);
		this.memberService.updateMember(currMember);
//		this.householdService.createHousehold(household);
		System.out.println(household.getMembers());
		redirectAttributes.addFlashAttribute("successMessage", "You have successfully created your household " + household.getName());
		return "redirect:/dashboard"; // TODO: redirect to the dashboard
		
	}
	
	// Join household by name 
	@GetMapping("/join")
	private String joinHousehold(Model viewModel, @ModelAttribute("household") Household household, HttpSession session, Principal principal) {
		String username = principal.getName();
		viewModel.addAttribute("currentUser", memberService.findByUsername(username));
		return "joinHousehold.jsp";
	}

	@PostMapping("/join/post") // Now a complete object
	private String joinHouseholdPost(@RequestParam("housename") String housename, Model viewModel, HttpSession session, Principal principal, RedirectAttributes redirectAttributes) {
		String username = principal.getName();
		viewModel.addAttribute("currentUser", memberService.findByUsername(username));
	
		Member currMember = memberService.findByUsername(username);
		for (Household h : householdService.allHouseHold()) {
			if (h.getName().equals(housename)) {
				System.out.println("Found matching house");
				currMember.setHousehold(h);
				this.memberService.updateMember(currMember);
			}
		}
		redirectAttributes.addFlashAttribute("successMessage", "You have successfully joined the household" + housename);
		return "redirect:/dashboard"; 
		
	}
}
