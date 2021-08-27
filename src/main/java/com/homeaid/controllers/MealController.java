package com.homeaid.controllers;

import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.homeaid.models.Item;
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
	
	 @InitBinder
	    public void initBinder(WebDataBinder binder) {
	        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");
	        sdf.setLenient(true);
	        binder.registerCustomEditor(Date.class, new CustomDateEditor(sdf, true));
	    }
	
	@GetMapping("ingredients/add")
	private String createItem(Model viewModel, @ModelAttribute("item") Item item, HttpSession session, Principal principal) {
		String username = principal.getName();
		viewModel.addAttribute("currentUser", memberService.findByUsername(username));
		viewModel.addAttribute("houseMembers", this.householdService.findbyMember(username).getMembers());
		
		return "createItem.jsp";
	}
	
	@PostMapping("ingredients/add")
	public String addTask(@Valid @ModelAttribute("item") Item item, BindingResult result, Model viewModel, HttpSession session, Principal principal, RedirectAttributes redirectAttr) {
		if (result.hasErrors()) { 
			System.out.println("errors creating item" + result.getAllErrors());
			String username = principal.getName();
			viewModel.addAttribute("currentUser", memberService.findByUsername(username));
			viewModel.addAttribute("houseMembers", this.householdService.findbyMember(username).getMembers());
			
			return "createItem.jsp";
		}
		System.out.println("Created item");
		
		this.itemService.createItem(item);
		return "redirect:/meals/ingredients/add";
	}

}
