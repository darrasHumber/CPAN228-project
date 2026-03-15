package com.amalcoders.educonnect.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "courses")
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long courseId;

    @NotBlank(message = "Course code is required")
    @Size(min = 3, max = 20, message = "Course code must be 3–20 characters")
    @Column(unique = true, nullable = false)
    private String courseCode;

    @NotBlank(message = "Title is required")
    @Size(min = 5, max = 100, message = "Title must be 5–100 characters")
    @Column(nullable = false)
    private String title;

    @NotBlank(message = "Description is required")
    @Size(max = 1000, message = "Description cannot exceed 1000 characters")
    @Column(length = 1000)
    private String description;

    @NotBlank(message = "Instructor name is required")
    private String instructorName;

    @NotNull(message = "Category is required")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Category category;

    @NotNull(message = "Status is required")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CourseStatus status = CourseStatus.DRAFT;

    @NotNull(message = "Max students is required")
    @Min(value = 1,   message = "Max students must be at least 1")
    @Max(value = 500, message = "Max students cannot exceed 500")
    @Column(nullable = false)
    private Integer maxStudents;

    @NotNull(message = "Duration is required")
    @Min(value = 1,  message = "Duration must be at least 1 week")
    @Max(value = 52, message = "Duration cannot exceed 52 weeks")
    @Column(nullable = false)
    private Integer durationWeeks;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    public enum Category {
        PROGRAMMING, MATHEMATICS, BUSINESS, SCIENCE, LANGUAGE, ARTS, OTHER
    }

    public enum CourseStatus {
        DRAFT, ACTIVE, COMPLETED, ARCHIVED
    }

    // ── Getters & Setters ─────────────────────
    public Long getCourseId() { return courseId; }
    public void setCourseId(Long courseId) { this.courseId = courseId; }

    public String getCourseCode() { return courseCode; }
    public void setCourseCode(String courseCode) { this.courseCode = courseCode; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getInstructorName() { return instructorName; }
    public void setInstructorName(String instructorName) { this.instructorName = instructorName; }

    public Category getCategory() { return category; }
    public void setCategory(Category category) { this.category = category; }

    public CourseStatus getStatus() { return status; }
    public void setStatus(CourseStatus status) { this.status = status; }

    public Integer getMaxStudents() { return maxStudents; }
    public void setMaxStudents(Integer maxStudents) { this.maxStudents = maxStudents; }

    public Integer getDurationWeeks() { return durationWeeks; }
    public void setDurationWeeks(Integer durationWeeks) { this.durationWeeks = durationWeeks; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}