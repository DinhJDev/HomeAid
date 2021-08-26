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

import com.homeaid.models.Member;
import com.homeaid.services.MemberService;

@Controller
public class HomeController {
	@Autowired
	MemberService memberService;
	
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
        session.invalidate();
        return "welcomePage.jsp";
    }
	@GetMapping("/dashboard")
	public String home(Principal principal, Model model) {
		String username = principal.getName();
		model.addAttribute("currentUser", memberService.findByUsername(username));
		return "dashboardPage.jsp";
	}
	

	
	
	
}
