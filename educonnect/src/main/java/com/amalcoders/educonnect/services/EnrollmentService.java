package com.amalcoders.educonnect.services;

import com.amalcoders.educonnect.models.Course;
import com.amalcoders.educonnect.models.Enrollment;
import com.amalcoders.educonnect.models.User;
import com.amalcoders.educonnect.repositories.EnrollmentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EnrollmentService {

    private final EnrollmentRepository enrollmentRepository;

    public EnrollmentService(EnrollmentRepository enrollmentRepository) {
        this.enrollmentRepository = enrollmentRepository;
    }

    // ── Enroll student in a course ────────────
    public Enrollment enroll(User student, Course course) {
        if (enrollmentRepository.existsByStudentAndCourse(student, course)) {
            throw new RuntimeException("Already enrolled in this course");
        }
        Enrollment enrollment = new Enrollment();
        enrollment.setStudent(student);
        enrollment.setCourse(course);
        return enrollmentRepository.save(enrollment);
    }

    // ── Drop a course ─────────────────────────
    public void drop(User student, Course course) {
        enrollmentRepository.findByStudentAndCourse(student, course)
                .ifPresent(enrollmentRepository::delete);
    }

    // ── Check if student is enrolled ──────────
    public boolean isEnrolled(User student, Course course) {
        return enrollmentRepository.existsByStudentAndCourse(student, course);
    }

    // ── Get all courses for a student ─────────
    public List<Enrollment> getEnrollmentsForStudent(User student) {
        return enrollmentRepository.findByStudent(student);
    }

    // ── Get all enrollments for a course ──────
    public List<Enrollment> getEnrollmentsForCourse(Course course) {
        return enrollmentRepository.findByCourse(course);
    }

    // ── Count enrolled students in a course ───
    public long countByCourse(Course course) {
        return enrollmentRepository.countByCourse(course);
    }
}
