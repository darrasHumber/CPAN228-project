package com.amalcoders.educonnect.controllers;


import com.amalcoders.educonnect.models.Course;
import com.amalcoders.educonnect.services.CourseService;
import com.amalcoders.educonnect.services.UserService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final CourseService courseService;
    private final UserService userService;

    public AdminController(CourseService courseService, UserService userService) {
        this.courseService = courseService;
        this.userService  = userService;
    }

    // ── GET /admin — dashboard ────────────────
    @GetMapping
    public String dashboard(Model model) {
        model.addAttribute("courses",      courseService.getCourses(null, null, null, 0, 100, "courseId", "ASC").getContent());
        model.addAttribute("totalCourses", courseService.count());
        model.addAttribute("totalUsers",   userService.count());
        model.addAttribute("activePage",   "admin");
        return "admin/dashboard";
    }

    // ── GET /admin/courses/edit/{id} — edit form ──
    @GetMapping("/courses/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        model.addAttribute("course",     courseService.findById(id));
        model.addAttribute("categories", Course.Category.values());
        model.addAttribute("statuses",   Course.CourseStatus.values());
        model.addAttribute("activePage", "admin");
        return "admin/edit-course";
    }

    // ── POST /admin/courses/edit/{id} — save edit ─
    @PostMapping("/courses/edit/{id}")
    public String saveEdit(
            @PathVariable Long id,
            @Valid @ModelAttribute("course") Course course,
            BindingResult result,
            Model model,
            RedirectAttributes redirectAttributes
    ) {
        if (result.hasErrors()) {
            model.addAttribute("categories", Course.Category.values());
            model.addAttribute("statuses",   Course.CourseStatus.values());
            model.addAttribute("activePage", "admin");
            return "admin/edit-course";
        }
        course.setCourseId(id);
        courseService.save(course);
        redirectAttributes.addFlashAttribute("successMessage",
                "Course updated successfully!");
        return "redirect:/admin";
    }

    // ── POST /admin/courses/delete/{id} — delete ──
    @PostMapping("/courses/delete/{id}")
    public String deleteCourse(
            @PathVariable Long id,
            RedirectAttributes redirectAttributes
    ) {
        courseService.delete(id);
        redirectAttributes.addFlashAttribute("successMessage",
                "Course deleted successfully!");
        return "redirect:/admin";
    }
}

