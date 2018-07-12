package com.springmvc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.springmvc.pojo.Admin;

@Controller
public class AdminController {

	@RequestMapping("/admin")
	public String showAdmin(Model model, @ModelAttribute("resultado")String resultado ) {
		
		Admin admin = new Admin();
		model.addAttribute("admin", admin);
		model.addAttribute("resultado", resultado);
		return "admin";
	}	

	
	@RequestMapping(value="/admin/save", method=RequestMethod.POST)
	public String handleAdmin(@ModelAttribute("admin") Admin adminForm, Model model,
			RedirectAttributes redirectAttributes) {
		
		System.out.println(adminForm);
		redirectAttributes.addFlashAttribute("resultado","Cambio realizados con exito");
		return "redirect:/admin";
	}	
	
	
}
