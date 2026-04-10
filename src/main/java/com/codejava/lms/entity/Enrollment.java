package com.codejava.lms.entity;

import com.codejava.lms.enums.EnrollmentStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "enrollments",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"student_id", "course_id"})
        })
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Enrollment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    // Many enrollments -> One student
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;

    // Many enrollments -> One course
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;

    private LocalDateTime enrolledAt;

    @Enumerated(EnumType.STRING)
    private EnrollmentStatus status;
}