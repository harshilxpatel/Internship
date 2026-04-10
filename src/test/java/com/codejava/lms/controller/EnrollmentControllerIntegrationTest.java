package com.codejava.lms.controller;

import com.codejava.lms.dto.EnrollmentRequestDTO;
import com.codejava.lms.entity.Course;
import com.codejava.lms.entity.Student;
import com.codejava.lms.repository.CourseRepository;
import com.codejava.lms.repository.StudentRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class EnrollmentControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private Student student;
    private Course course;

    @BeforeEach
    void setUp() {

        studentRepository.deleteAll();
        courseRepository.deleteAll();

        student = studentRepository.save(
                Student.builder()
                        .name("Harshil")
                        .email("harshil@gmail.com")
                        .build()
        );

        course = courseRepository.save(
                Course.builder()
                        .title("Java")
                        .code("J101")
                        .credits(4)
                        .build()
        );
    }

    // Success
    @Test
    void shouldEnrollSuccessfully() throws Exception {

        EnrollmentRequestDTO dto = new EnrollmentRequestDTO();
        dto.setStudentId(student.getId());
        dto.setCourseId(course.getId());

        mockMvc.perform(post("/api/v1/enrollments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(content().string("Enrollment successful"));
    }

    // Duplicate = 409
    @Test
    void shouldReturn409WhenDuplicateEnrollment() throws Exception {

        EnrollmentRequestDTO dto = new EnrollmentRequestDTO();
        dto.setStudentId(student.getId());
        dto.setCourseId(course.getId());

        mockMvc.perform(post("/api/v1/enrollments")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)));

        mockMvc.perform(post("/api/v1/enrollments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isConflict());
    }

    // Student Not Found = 404
    @Test
    void shouldReturn404WhenStudentNotFound() throws Exception {

        EnrollmentRequestDTO dto = new EnrollmentRequestDTO();
        dto.setStudentId(999L);
        dto.setCourseId(course.getId());

        mockMvc.perform(post("/api/v1/enrollments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isNotFound());
    }

    // Course Not Found = 404
    @Test
    void shouldReturn404WhenCourseNotFound() throws Exception {

        EnrollmentRequestDTO dto = new EnrollmentRequestDTO();
        dto.setStudentId(student.getId());
        dto.setCourseId(999L);

        mockMvc.perform(post("/api/v1/enrollments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isNotFound());
    }
}