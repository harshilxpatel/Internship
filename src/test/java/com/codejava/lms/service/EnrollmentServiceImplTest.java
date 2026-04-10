package com.codejava.lms.service;

import com.codejava.lms.entity.Course;
import com.codejava.lms.entity.Student;
import com.codejava.lms.repository.CourseRepository;
import com.codejava.lms.repository.EnrollmentRepository;
import com.codejava.lms.repository.StudentRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EnrollmentServiceImplTest {

    @Mock
    private EnrollmentRepository enrollmentRepository;

    @Mock
    private StudentRepository studentRepository;

    @Mock
    private CourseRepository courseRepository;

    @InjectMocks
    private EnrollmentServiceImpl enrollmentService;

    @Test
    void testEnroll() {

        Student student = Student.builder().id(1L).build();
        Course course = Course.builder().id(1L).build();

        when(studentRepository.findById(1L))
                .thenReturn(Optional.of(student));

        when(courseRepository.findById(1L))
                .thenReturn(Optional.of(course));

        when(enrollmentRepository.findByStudentIdAndCourseId(1L,1L))
                .thenReturn(Optional.empty());

        enrollmentService.enroll(1L,1L);

        verify(enrollmentRepository, times(1)).save(any());
    }
}