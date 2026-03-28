package com.amalcoders.educonnect.services;

import com.amalcoders.educonnect.models.Course;
import com.amalcoders.educonnect.repositories.CourseRepository;
import com.amalcoders.educonnect.repositories.EnrollmentRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CourseService {

    private final CourseRepository     courseRepository;
    private final EnrollmentRepository enrollmentRepository;

    public CourseService(CourseRepository courseRepository,
                         EnrollmentRepository enrollmentRepository) {
        this.courseRepository     = courseRepository;
        this.enrollmentRepository = enrollmentRepository;
    }

    // ── Save ──────────────────────────────────
    public Course save(Course course) {
        return courseRepository.save(course);
    }

    // ── Find by ID ────────────────────────────
    public Course findById(Long id) {
        return courseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(
                        "Course not found with id: " + id));
    }

    // ── Paginated list with filters ───────────
    public Page<Course> getCourses(
            String category, String status, String search,
            int page, int size, String sort, String direction
    ) {
        String sortField = switch (sort) {
            case "title", "courseCode", "category",
                 "status", "maxStudents",
                 "durationWeeks", "createdAt" -> sort;
            default -> "courseId";
        };

        Sort.Direction dir = "DESC".equalsIgnoreCase(direction)
                ? Sort.Direction.DESC : Sort.Direction.ASC;

        Pageable pageable = PageRequest.of(page, size, Sort.by(dir, sortField));

        boolean hasCategory = category != null && !category.isBlank();
        boolean hasStatus   = status   != null && !status.isBlank();
        boolean hasSearch   = search   != null && !search.isBlank();

        if (hasSearch) {
            return courseRepository.findByTitleContainingIgnoreCase(search, pageable);
        }
        if (hasCategory && hasStatus) {
            return courseRepository.findByCategoryAndStatus(
                    Course.Category.valueOf(category),
                    Course.CourseStatus.valueOf(status), pageable);
        }
        if (hasCategory) {
            return courseRepository.findByCategory(
                    Course.Category.valueOf(category), pageable);
        }
        if (hasStatus) {
            return courseRepository.findByStatus(
                    Course.CourseStatus.valueOf(status), pageable);
        }
        return courseRepository.findAll(pageable);
    }

    // ── Delete — removes enrollments first ────
    @Transactional
    public void delete(Long id) {
        Course course = findById(id);
        // Remove all enrollments before deleting the course
        enrollmentRepository.deleteByCourse(course);
        courseRepository.delete(course);
    }

    // ── Count ─────────────────────────────────
    public long count() {
        return courseRepository.count();
    }
}
