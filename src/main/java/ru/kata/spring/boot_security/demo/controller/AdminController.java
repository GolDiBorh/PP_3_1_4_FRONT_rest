package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;


@RestController
@RequestMapping("/api")
public class AdminController {

    private final UserService userService;

    @Autowired
    public AdminController (RoleService roleService, UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> getUsers() {
        return new ResponseEntity<>(userService.findAllUsers(), HttpStatus.OK);
    }

    @PostMapping("/users")
    public ResponseEntity<User> createUser(@Valid @RequestBody User user) {

            userService.saveUser(user);
            return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<User> pageDelete(@PathVariable("id") long id) {
        userService.deleteUserById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("users/{id}")
    public ResponseEntity<User> getUser (@PathVariable("id") long id) {
        User user = userService.getUserById(id);
        return new ResponseEntity<>(user,HttpStatus.OK);
    }

    @GetMapping("/user")
    public ResponseEntity<User> getUserByUsername (Principal principal) {
        User user = userService.findByUsername(principal.getName());
        return new ResponseEntity<>(user,HttpStatus.OK);
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<User> pageEdit(@PathVariable("id") long id,
                                         @Valid @RequestBody User user) {

                userService.update(user);
            return new ResponseEntity<>(HttpStatus.OK);
    }
}
















//@Controller
//@RequestMapping("/admin")
//public class AdminController {
//
//    private final UserService userService;
//
//    private final
//    RoleService roleService;
//
//    @Autowired
//    public AdminController(UserService userService, RoleService roleService) {
//        this.userService = userService;
//        this.roleService = roleService;
//    }
//
//    @GetMapping(value = "")
//    public String getAdminPage(Model model, Principal principal) {
//        model.addAttribute("user", userService.findByUsername(principal.getName()));
//        model.addAttribute("users", userService.findAllUsers());
//        model.addAttribute("roles", roleService.getAllRoles());
//        model.addAttribute("emptyUser", new User());
//        return "/admin";
//    }
//
//    @PostMapping("/addUser")
//    public String createUser(@ModelAttribute("emptyUser") User user,
//                             @RequestParam(value = "id") Long id) {
//        if(id ==4){
//            Set<Role> list = roleService.getAllRoles().stream().collect(Collectors.toSet());
//            user.setRoles(list);
//        } else {
//            Set<Role> list1 = roleService.getAllRoles().stream().limit(1).collect(Collectors.toSet());
//            user.setRoles(list1);
//        }
//        userService.saveUser(user);
//        return "redirect:/admin";
//    }
//
//    @GetMapping("/delete/{id}")
//    public String deleteUser(@PathVariable("id") Long id) {
//        userService.deleteUserById(id);
//        return "redirect:/admin";
//    }
//
//    @PostMapping("/updateUser/{id}")
//    public String updateUser(@ModelAttribute("emptyUser") User user, @PathVariable("id") Long id,
//                             @RequestParam(value = "userRolesSelector") Long selectResult) throws Exception {
//        if(selectResult ==4){
//            Set<Role> list = roleService.getAllRoles().stream().collect(Collectors.toSet());
//            user.setRoles(list);
//        } else {
//            Set<Role> list1 = roleService.getAllRoles().stream().limit(1).collect(Collectors.toSet());
//            user.setRoles(list1);
//        }
//        userService.update(user);
//        return "redirect:/admin";
//    }
//}
