package ru.chuistov.springboot.crud.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.chuistov.springboot.crud.entities.User;
import ru.chuistov.springboot.crud.services.UserService;

@Controller
@RequestMapping("/user")
public class UserController {

    private final UserService userService;
    private final ApplicationContext context;

    @Autowired
    public UserController(UserService userService, ApplicationContext context) {
        this.userService = userService;
        this.context = context;
    }

    @GetMapping()
    public String showAllUsers(Model model) {
        model.addAttribute("users", userService.findAll());
        return "user/users";
    }

    @GetMapping("/{id}")
    public String showUser(@PathVariable("id") long id, Model model) {
        model.addAttribute("user", userService.findById(id));
        return "user/user";
    }

    @GetMapping("/new")
    public String startCreateUser(Model model) {
        model.addAttribute("user", new User("name", "last name", 0));
        return "user/new";
    }

    @PostMapping()
    public String finishCreateUser(@ModelAttribute("user") User user) {
        userService.save(user);
        return "redirect:/user";
    }

    @GetMapping("/{id}/edit")
    public String startUpdateUser(@PathVariable("id") long id, Model model) {
        model.addAttribute("user", userService.findById(id));
        return "user/edit";
    }

    @PatchMapping("/{id}")
    public String finishUpdateUser(@ModelAttribute("user") User user,
                                   @PathVariable("id") long id) {
        userService.update(user);
        return "redirect:/user";
    }

    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable("id") long id) {
        userService.deleteById(id);
        return "redirect:/user";
    }

    @RequestMapping("/exit")
    public void shutdown() {
        System.exit(SpringApplication.exit(context, () -> 0));
    }
}