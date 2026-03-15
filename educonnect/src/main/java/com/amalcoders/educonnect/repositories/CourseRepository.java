package com.amalcoders.educonnect.repositories;

import com.amalcoders.educonnect.models.Course;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {

    // Filter by category
    Page<Course> findByCategory(Course.Category category, Pageable pageable);

    // Filter by status
    Page<Course> findByStatus(Course.CourseStatus status, Pageable pageable);

    // Filter by category AND status
    Page<Course> findByCategoryAndStatus(Course.Category category, Course.CourseStatus status, Pageable pageable);

    // Search by title (partial, case-insensitive)
    Page<Course> findByTitleContainingIgnoreCase(String title, Pageable pageable);
}