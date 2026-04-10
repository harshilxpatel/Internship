package com.codejava.lms.repository;

import com.codejava.lms.entity.Enrollment;
import com.codejava.lms.enums.EnrollmentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {

    Optional<Enrollment> findByStudentIdAndCourseId(Long studentId, Long courseId);

    List<Enrollment> findByStudentIdAndStatus(Long studentId, EnrollmentStatus status);

    List<Enrollment> findByCourseIdAndStatus(Long courseId, EnrollmentStatus status);

    @Query("""
        SELECT e FROM Enrollment e
        JOIN FETCH e.student
        JOIN FETCH e.course
        WHERE e.status = :status
    """)
    List<Enrollment> findAllWithStudentAndCourse(EnrollmentStatus status);
}