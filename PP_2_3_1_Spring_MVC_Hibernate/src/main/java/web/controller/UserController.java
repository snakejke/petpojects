package web.controller;

import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import web.model.User;
import web.service.UserService;

@Controller
public class UserController {

    private UserService userService;

    public UserController() {
    }

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = {"/", "/allUsers"})
    public String displayAllUsers(Model model) {
        model.addAttribute("userList", userService.getAllUsers());
        return "allUsers";
    }

    @GetMapping("/addUser")
    public String displayNewUserForm(Model model) {
        model.addAttribute("headerMessage", "Add User Details");
        model.addAttribute("user", new User());
        return "addUser";
    }

    @PostMapping("/addUser")
    public String saveNewUser(@ModelAttribute User user, BindingResult result) {
        if (result.hasErrors()) {
            return "error";
        }
        userService.saveUser(user);
        return "redirect:/allUsers";
    }

    @GetMapping("/editUser")
    public String displayEditUserForm(@RequestParam("id") Long id, Model model) {
        User user = userService.getUserById(id);
        model.addAttribute("headerMessage", "Edit User Details");
        model.addAttribute("user", user);
        return "editUser";
    }

    @PostMapping("/editUser")
    public String saveEditedUser(@ModelAttribute("user") @Valid User user, BindingResult result) {
        if (result.hasErrors()) {
            return "error";
        }
        userService.saveUser(user);
        return "redirect:/allUsers";
    }

    @GetMapping("/deleteUser")
    public String deleteUserById(@RequestParam("id") Long id) {
        userService.deleteUser(id);
        return "redirect:/allUsers";
    }

}

