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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.homeaid.models.Event;
import com.homeaid.models.Item;
import com.homeaid.models.Member;
import com.homeaid.services.EventService;
import com.homeaid.services.HouseholdService;
import com.homeaid.services.ItemService;
import com.homeaid.services.MemberService;
import com.homeaid.services.TaskService;

import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;
import kong.unirest.json.JSONArray;
import kong.unirest.json.JSONObject;

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
	
	@GetMapping("/recipes")
	public String recipes(Principal principal, Model viewModel) {
		String username = principal.getName();
		Member currentMember = memberService.findByUsername(username);
		String items = "";
		for (Item i : this.itemService.householdItems(currentMember.getHousehold().getId())) {
			items += i.getName() + ",";
		}
		ArrayList<JSONObject> results = searchHelper(items);
		ArrayList<String> recipeUrls = new ArrayList<>();
		for (JSONObject o : results) {
			recipeUrls.add("https://tasty.co/recipe/"+(String) o.get("slug"));
		}
		viewModel.addAttribute("items", items);
		viewModel.addAttribute("results", recipeUrls);
		return "recipes.jsp";
	}
	
	public ArrayList<JSONObject> searchHelper(String ingredientsList) {
		String url = "https://tasty.p.rapidapi.com/recipes/list?from=0&size=20&q=";
		ingredientsList.replace(",", "%2C");
		System.out.println(ingredientsList);
		HttpResponse<JsonNode> request  = Unirest.get(url+ingredientsList).header("x-rapidapi-host", "tasty.p.rapidapi.com").header("x-rapidapi-key", "7bb0d70e3dmshc45e7c1084a9a97p1fd0ecjsn9799e5425411").asJson();
		
		JSONObject resultsObject = request.getBody().getObject();
		
		JSONArray search = resultsObject.getJSONArray("results");
		
		ArrayList<JSONObject> results = new ArrayList<JSONObject>();
		for (int i = 0; i < search.length(); i++) {
			results.add(search.getJSONObject(i));
		}
		
		return results;
	}
	
	
	/** Plan a Meal */
	@GetMapping("/create")
	private String createMealPlan(Model viewModel, @ModelAttribute("event") Event event, HttpSession session, Principal principal) {
		String username = principal.getName();
		viewModel.addAttribute("currentUser", memberService.findByUsername(username));
		viewModel.addAttribute("houseMembers", this.householdService.findbyMember(username).getMembers());
	
		return "planMeal.jsp";
	}
	
	@PostMapping("/create/new")
	public String addMealPlan(@Valid @ModelAttribute("event") Event event, BindingResult result, Model viewModel, HttpSession session, Principal principal, RedirectAttributes redirectAttr, @RequestParam("start") @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm") Date startDate, @RequestParam("end") @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm") Date endDate) {
		String username = principal.getName();
		Member currUser = memberService.findByUsername(username);

		if (result.hasErrors()) { 
			System.out.println("errors creating event" + result.getAllErrors());
			
			viewModel.addAttribute("currentUser", memberService.findByUsername(username));
			viewModel.addAttribute("houseMembers", this.householdService.findbyMember(username).getMembers());
			
			return "planMeal.jsp";
		}
		
		System.out.println("Created meal");
		
		// Setting defaults for meals
		event.setNote("Meal");
		event.setEnd(event.getStart());
		event.setPrivacy(false);
		
		event.setHost(currUser);
		List<Member> attendees = new ArrayList<>();
		attendees.addAll(currUser.getHousehold().getMembers()); // Everyone attends meals
		event.setAttendees(attendees);
		Event newEvent = this.eventService.createEvent(event);
		this.eventService.getOneEvent(newEvent.getId());
		
		// You are automatically attending your own event
		
		
		redirectAttr.addFlashAttribute("successMessage", "Created meal successfully!");
		return "redirect:/dashboard";
	}
		

}
