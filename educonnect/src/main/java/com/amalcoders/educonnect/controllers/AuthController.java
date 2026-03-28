package com.amalcoders.educonnect.controllers;

import com.amalcoders.educonnect.models.Role;
import com.amalcoders.educonnect.models.User;
import com.amalcoders.educonnect.services.UserService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    // ── GET /login ────────────────────────────
    @GetMapping("/login")
    public String showLogin() {
        return "auth/login";
    }

    // ── GET /register ─────────────────────────
    @GetMapping("/register")
    public String showRegister(Model model) {
        model.addAttribute("user", new User());
        return "auth/register";
    }

    // ── POST /register ────────────────────────
    @PostMapping("/register")
    public String register(
            @Valid @ModelAttribute("user") User user,
            BindingResult result,
            RedirectAttributes redirectAttributes
    ) {
        // Validate role — only STUDENT or INSTRUCTOR allowed
        if (user.getRole() == null ||
                user.getRole() == Role.ADMIN) {
            result.rejectValue("role", "error.user",
                    "Please select Student or Instructor");
        }

        if (userService.usernameExists(user.getUsername())) {
            result.rejectValue("username", "error.user",
                    "Username already taken");
        }

        if (userService.emailExists(user.getEmail())) {
            result.rejectValue("email", "error.user",
                    "Email already registered");
        }

        if (result.hasErrors()) {
            return "auth/register";
        }

        userService.register(user);
        redirectAttributes.addFlashAttribute("successMessage",
                "Account created! Please log in.");
        return "redirect:/login";
    }
}
