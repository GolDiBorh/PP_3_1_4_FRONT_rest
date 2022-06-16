package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.security.Principal;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;

    private final
    RoleService roleService;

    @Autowired
    public AdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping(value = "")
    public String getAdminPage(Model model, Principal principal) {
        model.addAttribute("user", userService.findByUsername(principal.getName()));
        model.addAttribute("users", userService.findAllUsers());
        model.addAttribute("roles", roleService.getAllRoles());
        model.addAttribute("emptyUser", new User());
        return "/admin";
    }

    @PostMapping("/addUser")
    public String createUser(@ModelAttribute("emptyUser") User user,
                             @RequestParam(value = "id") Long id) {
        if(id ==4){
            Set<Role> list = roleService.getAllRoles().stream().collect(Collectors.toSet());
            user.setRoles(list);
        } else {
            Set<Role> list1 = roleService.getAllRoles().stream().limit(1).collect(Collectors.toSet());
            user.setRoles(list1);
        }
        userService.saveUser(user);
        return "redirect:/admin";
    }

    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable("id") Long id) {
        userService.deleteUserById(id);
        return "redirect:/admin";
    }

    @PostMapping("/updateUser/{id}")
    public String updateUser(@ModelAttribute("emptyUser") User user, @PathVariable("id") Long id,
                             @RequestParam(value = "userRolesSelector") Long selectResult) throws Exception {
        if(selectResult ==4){
            Set<Role> list = roleService.getAllRoles().stream().collect(Collectors.toSet());
            user.setRoles(list);
        } else {
            Set<Role> list1 = roleService.getAllRoles().stream().limit(1).collect(Collectors.toSet());
            user.setRoles(list1);
        }
        userService.update(user);
        return "redirect:/admin";
    }
}
