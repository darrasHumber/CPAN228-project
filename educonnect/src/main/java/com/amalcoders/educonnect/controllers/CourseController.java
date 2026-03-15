package com.amalcoders.educonnect.controllers;

import com.amalcoders.educonnect.models.Course;
import com.amalcoders.educonnect.services.CourseService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/courses")
public class CourseController {

    private final CourseService courseService;

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    // ── GET /courses — list view ──────────────
    @GetMapping
    public String list(
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String search,
            @RequestParam(defaultValue = "0")         int    page,
            @RequestParam(defaultValue = "10")        int    size,
            @RequestParam(defaultValue = "courseId")  String sort,
            @RequestParam(defaultValue = "ASC")       String direction,
            Model model
    ) {
        Page<Course> coursePage = courseService.getCourses(
                category, status, search, page, size, sort, direction
        );

        model.addAttribute("courses",       coursePage.getContent());
        model.addAttribute("totalPages",    coursePage.getTotalPages());
        model.addAttribute("totalElements", coursePage.getTotalElements());
        model.addAttribute("currentPage",   page);
        model.addAttribute("pageSize",      size);
        model.addAttribute("hasPrevious",   coursePage.hasPrevious());
        model.addAttribute("hasNext",       coursePage.hasNext());
        model.addAttribute("category",      category);
        model.addAttribute("status",        status);
        model.addAttribute("search",        search);
        model.addAttribute("sort",          sort);
        model.addAttribute("direction",     direction);
        model.addAttribute("categories",    Course.Category.values());
        model.addAttribute("statuses",      Course.CourseStatus.values());
        model.addAttribute("activePage",    "courses");

        return "courses/list";
    }

    // ── GET /courses/new — show form ──────────
    @GetMapping("/new")
    public String showForm(Model model) {
        model.addAttribute("course",     new Course());
        model.addAttribute("categories", Course.Category.values());
        model.addAttribute("statuses",   Course.CourseStatus.values());
        model.addAttribute("activePage", "courses");
        return "courses/form";
    }

    // ── POST /courses — save form ─────────────
    @PostMapping
    public String save(
            @Valid @ModelAttribute("course") Course course,
            BindingResult result,
            Model model,
            RedirectAttributes redirectAttributes
    ) {
        if (result.hasErrors()) {
            model.addAttribute("categories", Course.Category.values());
            model.addAttribute("statuses",   Course.CourseStatus.values());
            model.addAttribute("activePage", "courses");
            return "courses/form";
        }

        Course saved = courseService.save(course);
        redirectAttributes.addFlashAttribute("successMessage",
                "Course \"" + saved.getTitle() + "\" saved successfully!");
        return "redirect:/courses/" + saved.getCourseId();
    }

    // ── GET /courses/{id} — detail view ───────
    @GetMapping("/{id}")
    public String detail(@PathVariable Long id, Model model) {
        model.addAttribute("course",     courseService.findById(id));
        model.addAttribute("activePage", "courses");
        return "courses/detail";
    }
}
