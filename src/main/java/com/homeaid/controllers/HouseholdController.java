package com.homeaid.controllers;

import java.security.Principal;
import java.util.ArrayList;
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
	private String createHousehold(@Valid @ModelAttribute("household") Household household, BindingResult result, Model viewModel, HttpSession session, Principal principal) {
		String username = principal.getName();
		if (result.hasErrors()) { // need this stuff
			viewModel.addAttribute("currentUser", memberService.findByUsername(username));
			return "createHousehold.jsp";
		}
		// Try assigning relationship on the member instead
		Member currMember = memberService.findByUsername(username);
		currMember.setHousehold(household);
		this.memberService.updateMember(currMember);
		this.householdService.createHousehold(household);
		System.out.println(household.getMembers());
		session.setAttribute("successMessage", "You have successfully created your household " + household.getName());
		return "redirect:/dashboard"; // TODO: redirect to the dashboard
		
	}
	
	// Join household by name 
	

}
