package ru.kata.spring.boot_security.demo.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.security.Principal;


@Controller
public class HelloController {

	private final UserService userService;
	@Autowired
	public HelloController(UserService userService) {
		this.userService = userService;
	}

	@GetMapping(value = "/admin/")
	public String printWelcome(ModelMap model) {

		model.addAttribute("messages", userService.listUsers());
		return "index";
	}

	@GetMapping(value = "/admin/new")
	public String newUser(@ModelAttribute("user") User user) {
		return "new";
	}

	@PostMapping
	public String create(@ModelAttribute("user") User user){
		userService.add(user);
		return "redirect:/admin/";
	}
	@GetMapping(value = "/admin/edit/{id}")
	public String editUser( Model model, @PathVariable("id") long id) {
		model.addAttribute("user", userService.show(id));
		return "edit";
	}

	@PatchMapping(value = "/admin/id")
	public String update(@ModelAttribute("user") User user){
		userService.update(user);

		return "redirect:/admin/";
	}
	@DeleteMapping(value = "/admin/{id}")
	public String delete(@PathVariable("id") long id){
		userService.delete(id);
		return "redirect:/admin/";
	}

	@GetMapping(value = "/user")
	public String getUser( Model model, Principal principal) {
		model.addAttribute("user", userService.findByUsername(principal.getName()));
		return "user";
	}








}