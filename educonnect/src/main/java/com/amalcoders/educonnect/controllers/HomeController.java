package com.amalcoders.educonnect.controllers;

import com.amalcoders.educonnect.services.CourseService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    private final CourseService courseService;

    public HomeController(CourseService courseService) {
        this.courseService = courseService;
    }

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("activePage",   "home");
        model.addAttribute("totalCourses", courseService.count());
        return "home";
    }

    @GetMapping("/about")
    public String about(Model model) {
        model.addAttribute("activePage",   "about");
        model.addAttribute("totalCourses", courseService.count());
        return "about";
    }

    @GetMapping("/how-it-works")
    public String howItWorks(Model model) {
        model.addAttribute("activePage", "how-it-works");
        return "how-it-works";
    }

    // ── Temporary placeholder ─────────────────
    // Will be replaced by InstructorController in Branch 10
    @GetMapping("/instructor")
    public String instructor() {
        return "redirect:/courses";
    }
}
