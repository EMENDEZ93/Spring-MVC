package com.springmvc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.springcourse.pojo.Admin;

@Controller
public class AdminController {
	
	@RequestMapping("/admin")
	public String showAdmin(Model model) {
		Admin admin = new Admin();
		model.addAttribute("admin", admin);
		
		return "admin";
	}
	
	@RequestMapping(value="/admin/save", method=RequestMethod.POST)
	public String handleAdmin(@ModelAttribute("admin") Admin adminForm, Model model) {
		model.addAttribute("adminForm", adminForm);
		return "index";
	}
	
}
