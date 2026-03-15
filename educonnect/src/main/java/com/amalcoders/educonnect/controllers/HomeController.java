package com.amalcoders.educonnect.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("activePage", "home");
        return "home";
    }

    @GetMapping("/about")
    public String about(Model model) {
        model.addAttribute("activePage", "about");
        return "about";
    }

    @GetMapping("/how-it-works")
    public String howItWorks(Model model) {
        model.addAttribute("activePage", "how-it-works");
        return "how-it-works";
    }
}
