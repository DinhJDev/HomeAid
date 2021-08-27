package com.homeaid.controllers;

import java.security.Principal;
import java.util.Date;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.homeaid.models.Item;
import com.homeaid.models.Member;
import com.homeaid.services.EventService;
import com.homeaid.services.HouseholdService;
import com.homeaid.services.ItemService;
import com.homeaid.services.MemberService;
import com.homeaid.services.TaskService;

@Controller
@RequestMapping("/meals")
public class MealController {
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
	
	@GetMapping("ingredients/add")
	private String createItem(Model viewModel, @ModelAttribute("item") Item item, HttpSession session, Principal principal) {
		String username = principal.getName();
		viewModel.addAttribute("currentUser", memberService.findByUsername(username));
		viewModel.addAttribute("houseMembers", this.householdService.findbyMember(username).getMembers());
		
		return "createItem.jsp";
	}
	
	@PostMapping("ingredients/add")
	public String addTask(@Valid @ModelAttribute("item") Item item, BindingResult result, Model viewModel, HttpSession session, 
			Principal principal, RedirectAttributes redirectAttr, @RequestParam("expirationDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date date) {
		
		String username = principal.getName();
		Member currentMember = memberService.findByUsername(username);
		if (result.hasErrors()) { 
			System.out.println("errors creating item" + result.getAllErrors());
			viewModel.addAttribute("currentUser", memberService.findByUsername(username));
			viewModel.addAttribute("houseMembers", this.householdService.findbyMember(username).getMembers());
			
			return "createItem.jsp";
		}
		item.setHousehold(currentMember.getHousehold());
		System.out.println("Created item");
		
		redirectAttr.addAttribute("addSuccess", item.getName() + " with quantity " + item.getQuantity() + " " + item.getMeasurement() + " has been added!");
		
		this.itemService.createItem(item);
		return "redirect:/meals/ingredients/add";
	}

}
