package com.homeaid.controllers;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.hibernate.internal.build.AllowSysOut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.homeaid.models.Member;
import com.homeaid.models.Task;
import com.homeaid.services.HouseholdService;
import com.homeaid.services.MemberService;
import com.homeaid.services.TaskService;

@Controller
@RequestMapping("/tasks")
public class TaskController {
	/**
	 * 	private String title;
	private Integer priority;
	private Integer difficulty;
	private String note;
	private Boolean completed;
	
	Should add date
	 */
	@Autowired
	private MemberService memberService;
	@Autowired
	private HouseholdService householdService;
	@Autowired
	private TaskService taskService;
	
	/** Shows all tasks for the house, including unassigned ones 
	 * TODO: only this household's tasks **/
	@GetMapping("/all")
	private String showAllTasks(Model viewModel, HttpSession session, Principal principal) {
		String username = principal.getName();
		viewModel.addAttribute("currentUser", memberService.findByUsername(username));
//		List<Task> tasksList = new ArrayList<>();
//		for (Member m: this.householdService.findbyMember(username).getMembers()) {
//			System.out.println("member" + m.getFullName());
//			tasksList.addAll(m.getTasks());
//		}
		viewModel.addAttribute("allTasks", taskService.allTask());
	
		return "allTasks.jsp";
	}
	
	/** Main Tasks Creation - Accessible from the Dashboard */
	@GetMapping("/create")
	private String createTask(Model viewModel, @ModelAttribute("task") Task task, HttpSession session, Principal principal) {
		String username = principal.getName();
		viewModel.addAttribute("currentUser", memberService.findByUsername(username));
		viewModel.addAttribute("houseMembers", this.householdService.findbyMember(username).getMembers());
	
		return "createTask.jsp";
	}
	
	@PostMapping("/create/new")
	public String addTask(@Valid @ModelAttribute("task") Task task, BindingResult result, Model viewModel, HttpSession session, Principal principal, RedirectAttributes redirectAttr) {
		if (result.hasErrors()) { 
			System.out.println("errors creating task" + result.getAllErrors());
			
			String username = principal.getName();
			viewModel.addAttribute("currentUser", memberService.findByUsername(username));
			
			viewModel.addAttribute("houseMembers", this.householdService.findbyMember(username).getMembers());
			
			
			return "createTask.jsp";
		}
		System.out.println("Created task");
		
		this.taskService.createTask(task);
		return "redirect:/dashboard";
	}
	
	/** EDITING A TASK AND ASSIGNING IT */
	@GetMapping("/edit/{task_id}")
	private String editTask(@PathVariable("task_id") Long taskId, Model viewModel, @ModelAttribute("task") Task task, HttpSession session, Principal principal) {
		// Make sure it won't keep creating new tasks
		String username = principal.getName();
		viewModel.addAttribute("currentUser", memberService.findByUsername(username));
		viewModel.addAttribute("task", taskService.getOneTask(taskId));
		viewModel.addAttribute("houseMembers", this.householdService.findbyMember(username).getMembers());
		viewModel.addAttribute("edit", "True");
		return "editTask.jsp";
		
	}
	
	@PostMapping("/edit/{task_id}")
	private String editTaskPost(@PathVariable("task_id") Long taskId, @Valid @ModelAttribute("task") Task task, BindingResult result, Model viewModel, HttpSession session, Principal principal, RedirectAttributes redirectAttr) {
		// Make sure it won't keep creating new tasks
		if (result.hasErrors()) { 
			System.out.println("Errors");
			String username = principal.getName();
			viewModel.addAttribute("currentUser", memberService.findByUsername(username));
			viewModel.addAttribute("task", taskService.getOneTask(taskId));
			viewModel.addAttribute("houseMembers", this.householdService.findbyMember(username).getMembers());
			return "editTask.jsp";
		}
		
		
		this.taskService.updateTask(taskId, task);
		if (task.getCompleted()) { // Delete upon completion. Later can move to a compeleted section. 
			System.out.println("Removing task");
			this.taskService.removeTask(taskId);
		}
		return "redirect:/dashboard";
		
	}
	
	/** My Tasks */
	@GetMapping("/mytasks")
	private String myTasks(Model viewModel, @ModelAttribute("task") Task task, HttpSession session, Principal principal) {
		// Make sure it won't keep creating new tasks
		String username = principal.getName();
		viewModel.addAttribute("currentUser", memberService.findByUsername(username));
		return "myTasks.jsp";
		
	}

}
