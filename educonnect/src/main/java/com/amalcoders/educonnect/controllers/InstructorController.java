package com.amalcoders.educonnect.controllers;

import com.amalcoders.educonnect.models.Course;
import com.amalcoders.educonnect.models.User;
import com.amalcoders.educonnect.services.CourseService;
import com.amalcoders.educonnect.services.EnrollmentService;
import org.springframework.data.domain.Page;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/instructor")
public class InstructorController {

    private final CourseService    courseService;
    private final EnrollmentService enrollmentService;

    public InstructorController(CourseService courseService,
                                EnrollmentService enrollmentService) {
        this.courseService    = courseService;
        this.enrollmentService = enrollmentService;
    }

    // ── GET /instructor — dashboard ───────────
    @GetMapping
    public String dashboard(
            @AuthenticationPrincipal User currentUser,
            Model model
    ) {
        // Get all courses taught by this instructor
        Page<Course> coursePage = courseService.getCourses(
                null, null, null, 0, 100, "courseId", "ASC");

        List<Course> myCourses = coursePage.getContent().stream()
                .filter(c -> c.getInstructorName().equalsIgnoreCase(
                        currentUser.getFirstName() + " " + currentUser.getLastName()) ||
                        c.getInstructorName().toLowerCase()
                                .contains(currentUser.getLastName().toLowerCase()))
                .toList();

        // Build stats map: course → enrolled count
        Map<Course, Long> courseStats = new LinkedHashMap<>();
        for (Course course : myCourses) {
            long count = enrollmentService.countByCourse(course);
            courseStats.put(course, count);
        }

        // Summary stats
        long totalStudents = courseStats.values().stream()
                .mapToLong(Long::longValue).sum();
        long activeCourses = myCourses.stream()
                .filter(c -> c.getStatus() == Course.CourseStatus.ACTIVE).count();
        long draftCourses = myCourses.stream()
                .filter(c -> c.getStatus() == Course.CourseStatus.DRAFT).count();

        model.addAttribute("currentUser",   currentUser);
        model.addAttribute("courseStats",   courseStats);
        model.addAttribute("totalCourses",  myCourses.size());
        model.addAttribute("totalStudents", totalStudents);
        model.addAttribute("activeCourses", activeCourses);
        model.addAttribute("draftCourses",  draftCourses);
        model.addAttribute("activePage",    "instructor");

        return "instructor/dashboard";
    }

    // ── GET /instructor/courses/{id}/students ─
    @GetMapping("/courses/{id}/students")
    public String courseStudents(
            @PathVariable Long id,
            Model model
    ) {
        Course course = courseService.findById(id);
        model.addAttribute("course",      course);
        model.addAttribute("enrollments", enrollmentService.getEnrollmentsForCourse(course));
        model.addAttribute("activePage",  "instructor");
        return "instructor/course-students";
    }
}
