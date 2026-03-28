package com.amalcoders.educonnect.repositories;

import com.amalcoders.educonnect.models.Course;
import com.amalcoders.educonnect.models.Enrollment;
import com.amalcoders.educonnect.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {

    // All enrollments for a student
    List<Enrollment> findByStudent(User student);

    // All enrollments for a course
    List<Enrollment> findByCourse(Course course);

    // Check if student is already enrolled
    boolean existsByStudentAndCourse(User student, Course course);

    // Find specific enrollment
    Optional<Enrollment> findByStudentAndCourse(User student, Course course);

    // Count students enrolled in a course
    long countByCourse(Course course);
}
