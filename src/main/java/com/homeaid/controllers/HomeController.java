package com.homeaid.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.homeaid.models.Member;
import com.homeaid.services.MemberService;

@RestController
public class HomeController {
	@Autowired
	MemberService memberService;
	
	@GetMapping("/")
	public String welcomePage(@Valid @ModelAttribute("member") Member member) {
		return "welcomePage.jsp";
	}
	@PostMapping("/registration")
	public String registration(@Valid @ModelAttribute("login") Member member, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "welcomePage.jsp";
        }
        memberService.saveWithUserRole(member);
        return "redirect:/login";
    }
	@GetMapping("/login")
    public String login() {
        return "loginPage.jsp";
    }
	@PostMapping("/login")
    public String login(@RequestParam(value="error", required=false) String error, @RequestParam(value="logout", required=false) String logout, Model model) {
        if(error != null) {
            model.addAttribute("errorMessage", "Invalid Credentials, Please try again.");
        }
        if(logout != null) {
            model.addAttribute("logoutMessage", "Logout Successful!");
        }
        return "loginPage.jsp";
    }
	@GetMapping("/dashboard")
	public String home() {
		return "dashboardPage.jsp";
	}
}
