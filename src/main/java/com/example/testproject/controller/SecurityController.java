package com.example.testproject.controller;

import com.example.testproject.dto.UserDto;
import com.example.testproject.entity.User;
import com.example.testproject.repository.LogRepository;
import com.example.testproject.repository.UserRepository;
import com.example.testproject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


import javax.validation.Valid;
import java.sql.SQLOutput;
import java.util.List;
import java.util.Optional;

@Controller
public class SecurityController {

    private UserService userService;
    @Autowired
    private UserRepository userRepository;


    public SecurityController(UserService userService) {this.userService = userService;}


    @GetMapping("/index")
    public String home() {return "index";}

    @GetMapping("/login")
    public String login() {return  "login";}

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        UserDto user = new UserDto();
        model.addAttribute("user", user);
        return "register";
    }

    @PostMapping("/register/save")
    public String registration(@Valid @ModelAttribute("user") UserDto userDto,
                               BindingResult result,
                               Model model) {
        User existingUser = userService.findUserByEmail(userDto.getEmail());

        if(existingUser != null && existingUser.getEmail() != null && !existingUser.getEmail().isEmpty()) {
            result.rejectValue("email", null,
                    "На этот адрес электронной почты уже зарегистрирована учетная запись.");
        }

        if(result.hasErrors()) {

            return "/register";
        }

        userService.saveUser(userDto);
        return "redirect:/login?registration_success";
    }

    @GetMapping("/users")
    public String users(Model model){
        List<UserDto> users = userService.findAllUsers();
        model.addAttribute("users", users);
        return "users";
    }

    @GetMapping("/logs")
    public String usersLog(@RequestParam Long userId, Model model){
         Optional<User> user = userRepository.findById(userId);
         if (user.isPresent()) {
             model.addAttribute("user", user.get());
             return "user-log";
         }
        return "redirect:/users";
    }

}
