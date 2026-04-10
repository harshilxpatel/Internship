package com.codejava.lms.controller;

import com.codejava.lms.entity.Student;
import com.codejava.lms.repository.StudentRepository;
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
class StudentControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        studentRepository.deleteAll();
    }

    // 409
    @Test
    void shouldReturn409WhenDuplicateEmail() throws Exception {

        Student student = Student.builder()
                .name("Harsh")
                .email("harsh@gmail.com")
                .build();

        studentRepository.save(student);

        mockMvc.perform(post("/api/v1/students")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(student)))
                .andExpect(status().isConflict());
    }

    // 400
    @Test
    void shouldReturn400WhenInvalidEmail() throws Exception {

        Student student = Student.builder()
                .name("Harsh")
                .email("invalid-email") // invalid
                .build();

        mockMvc.perform(post("/api/v1/students")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(student)))
                .andExpect(status().isBadRequest());
    }
}