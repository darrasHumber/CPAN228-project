package com.amalcoders.educonnect.controllers;

import com.amalcoders.educonnect.models.Course;
import com.amalcoders.educonnect.models.User;
import com.amalcoders.educonnect.services.CourseService;
import com.amalcoders.educonnect.services.EnrollmentService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/courses")
public class CourseController {

    private final CourseService    courseService;
    private final EnrollmentService enrollmentService;

    public CourseController(CourseService courseService,
                            EnrollmentService enrollmentService) {
        this.courseService    = courseService;
        this.enrollmentService = enrollmentService;
    }

    // ── GET /courses — list ───────────────────
    @GetMapping
    public String list(
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String search,
            @RequestParam(defaultValue = "0")        int    page,
            @RequestParam(defaultValue = "10")       int    size,
            @RequestParam(defaultValue = "courseId") String sort,
            @RequestParam(defaultValue = "ASC")      String direction,
            Model model
    ) {
        Page<Course> coursePage = courseService.getCourses(
                category, status, search, page, size, sort, direction);

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

    // ── POST /courses — save ──────────────────
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

    // ── GET /courses/{id} — detail ────────────
    @GetMapping("/{id}")
    public String detail(
            @PathVariable Long id,
            @AuthenticationPrincipal User currentUser,
            Model model
    ) {
        Course course = courseService.findById(id);
        model.addAttribute("course",     course);
        model.addAttribute("activePage", "courses");

        // Check if current student is enrolled
        if (currentUser != null) {
            boolean enrolled = enrollmentService.isEnrolled(currentUser, course);
            model.addAttribute("isEnrolled", enrolled);
            model.addAttribute("enrolledCount",
                    enrollmentService.countByCourse(course));
        }
        return "courses/detail";
    }

    // ── POST /courses/{id}/enroll ─────────────
    @PostMapping("/{id}/enroll")
    public String enroll(
            @PathVariable Long id,
            @AuthenticationPrincipal User currentUser,
            RedirectAttributes redirectAttributes
    ) {
        Course course = courseService.findById(id);
        try {
            enrollmentService.enroll(currentUser, course);
            redirectAttributes.addFlashAttribute("successMessage",
                    "Successfully enrolled in \"" + course.getTitle() + "\"!");
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("errorMessage",
                    e.getMessage());
        }
        return "redirect:/courses/" + id;
    }

    // ── POST /courses/{id}/drop ───────────────
    @PostMapping("/{id}/drop")
    public String drop(
            @PathVariable Long id,
            @AuthenticationPrincipal User currentUser,
            RedirectAttributes redirectAttributes
    ) {
        Course course = courseService.findById(id);
        enrollmentService.drop(currentUser, course);
        redirectAttributes.addFlashAttribute("successMessage",
                "You have dropped \"" + course.getTitle() + "\".");
        return "redirect:/courses/" + id;
    }
}
