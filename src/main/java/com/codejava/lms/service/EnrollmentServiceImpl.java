package com.codejava.lms.service;

import com.codejava.lms.dto.CourseDTO;
import com.codejava.lms.entity.Course;
import com.codejava.lms.entity.Enrollment;
import com.codejava.lms.entity.Student;
import com.codejava.lms.enums.EnrollmentStatus;
import com.codejava.lms.exception.DuplicateResourceException;
import com.codejava.lms.exception.ResourceNotFoundException;
import com.codejava.lms.mapper.CourseMapper;
import com.codejava.lms.repository.CourseRepository;
import com.codejava.lms.repository.EnrollmentRepository;
import com.codejava.lms.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EnrollmentServiceImpl implements EnrollmentService {

    private final EnrollmentRepository enrollmentRepository;
    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;


    @Override
    public String enroll(Long studentId, Long courseId) {

        Student student = studentRepository.findById(studentId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Student not found"));

        Course course = courseRepository.findById(courseId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Course not found"));

        enrollmentRepository.findByStudentIdAndCourseId(studentId, courseId)
                .ifPresent(enrollment -> {
                    if (enrollment.getStatus() == EnrollmentStatus.ENROLLED) {
                        throw new DuplicateResourceException("Already enrolled");
                    }
                });

        Enrollment enrollment = Enrollment.builder()
                .student(student)
                .course(course)
                .enrolledAt(LocalDateTime.now())
                .status(EnrollmentStatus.ENROLLED)
                .build();

        enrollmentRepository.save(enrollment);

        return "Enrollment successful";
    }


    @Override
    public String unenroll(Long studentId, Long courseId) {

        Enrollment enrollment = enrollmentRepository
                .findByStudentIdAndCourseId(studentId, courseId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Enrollment not found"));

        enrollment.setStatus(EnrollmentStatus.UNENROLLED);

        enrollmentRepository.save(enrollment);

        return "Unenrolled successfully";
    }


    @Override
    public List<CourseDTO> getCoursesByStudent(Long studentId) {

        studentRepository.findById(studentId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Student not found"));

        return enrollmentRepository
                .findByStudentIdAndStatus(studentId, EnrollmentStatus.ENROLLED)
                .stream()
                .map(enrollment ->
                        CourseMapper.toDTO(enrollment.getCourse()))
                .toList();
    }


    @Override
    public List<Student> getStudentsByCourse(Long courseId) {

        courseRepository.findById(courseId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Course not found"));

        return enrollmentRepository
                .findAllWithStudentAndCourse(EnrollmentStatus.ENROLLED)
                .stream()
                .filter(enrollment ->
                        enrollment.getCourse().getId().equals(courseId))
                .map(Enrollment::getStudent)
                .toList();
    }
}