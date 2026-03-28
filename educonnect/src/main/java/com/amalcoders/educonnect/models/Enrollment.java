package com.amalcoders.educonnect.models;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "enrollments",
        uniqueConstraints = @UniqueConstraint(
                columnNames = {"student_id", "course_id"}
        ))
public class Enrollment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long enrollmentId;

    // ── Relationships ─────────────────────────
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id", nullable = false)
    private User student;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;

    // ── Status ────────────────────────────────
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EnrollmentStatus status = EnrollmentStatus.ACTIVE;

    // ── Timestamp ─────────────────────────────
    @Column(nullable = false, updatable = false)
    private LocalDateTime enrolledAt;

    @PrePersist
    protected void onCreate() {
        this.enrolledAt = LocalDateTime.now();
    }

    public enum EnrollmentStatus {
        ACTIVE, COMPLETED, DROPPED
    }

    // ── Getters & Setters ─────────────────────
    public Long getEnrollmentId()                        { return enrollmentId; }
    public void setEnrollmentId(Long enrollmentId)       { this.enrollmentId = enrollmentId; }

    public User getStudent()                             { return student; }
    public void setStudent(User student)                 { this.student = student; }

    public Course getCourse()                            { return course; }
    public void setCourse(Course course)                 { this.course = course; }

    public EnrollmentStatus getStatus()                  { return status; }
    public void setStatus(EnrollmentStatus status)       { this.status = status; }

    public LocalDateTime getEnrolledAt()                 { return enrolledAt; }
    public void setEnrolledAt(LocalDateTime enrolledAt)  { this.enrolledAt = enrolledAt; }
}
