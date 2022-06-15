package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.security.Principal;

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
                             @RequestParam(value = "id") Long[] id) {
        for (Long s : id) {
            user.addRole(roleService.getRoleById(s));
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
                             @RequestParam(value = "userRolesSelector") Long[] selectResult) throws Exception {
        for (Long s : selectResult) {
            user.addRole(roleService.getRoleById(s));
        }
        userService.update(id, user);
        return "redirect:/admin";
    }
}

//@Controller
//public class AdminController {
//    private final UserService userService;
//    @Autowired
//    RoleService roleService;
//    @Autowired
//    public AdminController(UserService userService) {
//        this.userService = userService;
//    }
//
//    @GetMapping(value = "/admin")
//    public String printWelcome(ModelMap model) {
//
//        model.addAttribute("messages", userService.findAllUsers());
//        return "index";
//    }
//
//    @GetMapping(value = "/admin/new")
//    public String newUser(@ModelAttribute("user") User user) {
//        return "new";
//    }
//
//    @PostMapping
//    public String create(@ModelAttribute("user") User user){
//        userService.saveUser(user);
//        return "redirect:/admin";
//    }
//    @GetMapping(value = "/admin/edit/{id}")
//    public String editUser(Model model, @PathVariable("id") long id) {
//        model.addAttribute("user", userService.getUserById(id));
//        return "edit";
//    }
//
//    @PatchMapping(value = "/admin/id")
//    public String update(@ModelAttribute("user") User user){
//        userService.updateUser(user);
//
//        return "redirect:/admin";
//    }
//    @DeleteMapping(value = "/admin/{id}")
//    public String delete(@PathVariable("id") long id){
//        userService.deleteUserById(id);
//        return "redirect:/admin";
//    }
//}
