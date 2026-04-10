package com.codejava.lms.service;

import com.codejava.lms.dto.CourseDTO;
import com.codejava.lms.entity.Student;

import java.util.List;

public interface EnrollmentService {

    String enroll(Long studentId, Long courseId);

    String unenroll(Long studentId, Long courseId);

    List<CourseDTO> getCoursesByStudent(Long studentId);

    List<Student> getStudentsByCourse(Long courseId);
}