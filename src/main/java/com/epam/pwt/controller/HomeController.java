package com.epam.pwt.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HomeController {
	
	@GetMapping("/")
	public ModelAndView WelcomePage() {
		
		return new ModelAndView("index");
	}
	@GetMapping("home")
	public ModelAndView goToHome() {
		
		return new ModelAndView("index");
	}

}
