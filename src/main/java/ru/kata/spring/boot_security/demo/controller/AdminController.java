package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserService;

@Controller
public class AdminController {
    private final UserService userService;
    @Autowired
    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = "/admin")
    public String printWelcome(ModelMap model) {

        model.addAttribute("messages", userService.findAllUsers());
        return "index";
    }

    @GetMapping(value = "/admin/new")
    public String newUser(@ModelAttribute("user") User user) {
        return "new";
    }

    @PostMapping
    public String create(@ModelAttribute("user") User user){
        userService.saveUser(user);
        return "redirect:/admin";
    }
    @GetMapping(value = "/admin/edit/{id}")
    public String editUser(Model model, @PathVariable("id") long id) {
        model.addAttribute("user", userService.getUserById(id));
        return "edit";
    }

    @PatchMapping(value = "/admin/id")
    public String update(@ModelAttribute("user") User user){
        userService.updateUser(user);

        return "redirect:/admin";
    }
    @DeleteMapping(value = "/admin/{id}")
    public String delete(@PathVariable("id") long id){
        userService.deleteUserById(id);
        return "redirect:/admin";
    }
}
