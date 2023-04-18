package ru.kata.spring.boot_security.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;
    public final RoleService roleService;

    public AdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping()
    public String getAllUser(Principal principal, Model model) {
        User user = userService.findByUsername(principal.getName());
        model.addAttribute("user", user);
        List<User> list = userService.allUsers();
        model.addAttribute("list", list);
        model.addAttribute("roles", roleService.getAllRoles());

        model.addAttribute("newUser", new User());
        model.addAttribute("newRoles", roleService.getAllRoles());

        return "admin";
    }

    @PostMapping()
    public String addUser(@ModelAttribute("user") User user) {
        userService.saveUser(user);
        return "redirect:/admin";
    }


    @PatchMapping(value = "/{id}")
    public String update(@ModelAttribute("user") User updateUser, @PathVariable("id") Long id) {
        User existingUser = userService.getByUserId(id);
        existingUser.setFirstName(updateUser.getFirstName());
        existingUser.setLastName(updateUser.getLastName());
        existingUser.setAge(updateUser.getAge());
        existingUser.setUserName(updateUser.getUserName());
        existingUser.setListRole(updateUser.getListRole());
        userService.update(existingUser);
        return "redirect:/admin";
    }


    @GetMapping("/{id}/delete")
    public String deleteUser(@PathVariable("id") Long id) {
        userService.deleteUser(id);
        return "redirect:/admin";
    }
}
