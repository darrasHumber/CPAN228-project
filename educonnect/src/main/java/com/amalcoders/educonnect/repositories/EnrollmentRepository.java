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

    List<Enrollment> findByStudent(User student);

    List<Enrollment> findByCourse(Course course);

    boolean existsByStudentAndCourse(User student, Course course);

    Optional<Enrollment> findByStudentAndCourse(User student, Course course);

    long countByCourse(Course course);

    // ── Delete all enrollments for a course ───
    // Called before deleting a course
    void deleteByCourse(Course course);
}
