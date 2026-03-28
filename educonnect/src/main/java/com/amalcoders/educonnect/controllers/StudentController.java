package com.amalcoders.educonnect.controllers;

import com.amalcoders.educonnect.models.Enrollment;
import com.amalcoders.educonnect.models.User;
import com.amalcoders.educonnect.services.EnrollmentService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/dashboard")
public class StudentController {

    private final EnrollmentService enrollmentService;

    public StudentController(EnrollmentService enrollmentService) {
        this.enrollmentService = enrollmentService;
    }

    // ── GET /dashboard ────────────────────────
    @GetMapping
    public String dashboard(
            @AuthenticationPrincipal User currentUser,
            Model model
    ) {
        List<Enrollment> enrollments =
                enrollmentService.getEnrollmentsForStudent(currentUser);

        // Stats
        long totalEnrolled = enrollments.size();
        long completed = enrollments.stream()
                .filter(e -> e.getStatus() == Enrollment.EnrollmentStatus.COMPLETED)
                .count();
        long active = enrollments.stream()
                .filter(e -> e.getStatus() == Enrollment.EnrollmentStatus.ACTIVE)
                .count();

        model.addAttribute("enrollments",    enrollments);
        model.addAttribute("totalEnrolled",  totalEnrolled);
        model.addAttribute("completed",      completed);
        model.addAttribute("active",         active);
        model.addAttribute("currentUser",    currentUser);
        model.addAttribute("activePage",     "dashboard");

        return "student/dashboard";
    }
}
