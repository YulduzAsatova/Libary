package com.example.new_online_libary_project.user;
import com.example.new_online_libary_project.user.dto.RegisterDto;
import com.example.new_online_libary_project.user.dto.UpdateDto;
import com.example.new_online_libary_project.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final UserService userService;

    @GetMapping("/users")
    public String getAll(Model model) {
        List<User> users = userService.getAll();
        //List<Role> roles = roleService.getAll();
        model.addAttribute("users", userService.getAll());
       // model.addAttribute("roles", roleService.getAll());
        return "admin/users";
    }

    @GetMapping("/register")
    public String register(Model model) {
        return "auth/register";
    }

    @PostMapping("/register")
    public String register(@ModelAttribute RegisterDto registerDto) {
        userService.create(registerDto);
        return "redirect:/auth/login";
    }

  /*  @GetMapping("/logout")
    public String logoutPage() {
       return "logout";
    }*/

    @GetMapping("/login")
    public String login() {
        return "auth/login";
    }
    @GetMapping("/create-user")
    public String showCreateForm(Model model) {
        return "admin/new-user";
    }

    @PostMapping("/user")
    public String createUser(@ModelAttribute RegisterDto registerDto, BindingResult result, Model model) {
        userService.create(registerDto);
        return "redirect:/auth/users";
    }

    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable("id") UUID id) {
        userService.delete(id);
        return "redirect:/auth/users";
    }

    @GetMapping("/update/{id}")
    public String showUpdateForm(@PathVariable("id") UUID id, Model model) {
        User user = userService
                .getById(id)
                .orElseThrow(() -> new IllegalArgumentException("User id" + id + "Not fount"));
        model.addAttribute("user", user);
        return "admin/update-user";
    }

    @PostMapping("/user/{id}")
    public String updateUser(@PathVariable("id") UUID id, @ModelAttribute UpdateDto updateDto, BindingResult result) {
        userService.update(updateDto, id);
        return "redirect:/auth/users";
    }


}
