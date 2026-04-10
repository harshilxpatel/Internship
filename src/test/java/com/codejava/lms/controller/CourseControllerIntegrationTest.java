package com.codejava.lms.controller;

import com.codejava.lms.entity.Course;
import com.codejava.lms.repository.CourseRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class CourseControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private Course course;

    @BeforeEach
    void setUp() {
        courseRepository.deleteAll();

        course = Course.builder()
                .title("Java")
                .code("J101")
                .credits(4)
                .build();

        courseRepository.save(course);
    }

    // 404
    @Test
    void shouldReturn404WhenCourseNotFound() throws Exception {
        mockMvc.perform(get("/api/v1/courses/999"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404));
    }

    // 409
    @Test
    void shouldReturn409WhenDuplicateCourseCode() throws Exception {

        Course duplicate = Course.builder()
                .title("Spring")
                .code("J101")
                .credits(3)
                .build();

        mockMvc.perform(post("/api/v1/courses")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(duplicate)))
                .andExpect(status().isConflict());
    }

    // 400
    @Test
    void shouldReturn400WhenInvalidInput() throws Exception {

        Course invalid = Course.builder()
                .title("") // invalid
                .code("C102")
                .credits(3)
                .build();

        mockMvc.perform(post("/api/v1/courses")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalid)))
                .andExpect(status().isBadRequest());
    }
}