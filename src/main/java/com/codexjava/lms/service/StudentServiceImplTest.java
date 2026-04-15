package com.codexjava.lms.service;

import com.codexjava.lms.entity.Student;
import com.codexjava.lms.repository.StudentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StudentServiceImplTest {

    @Mock
    private StudentRepository studentRepository;

    @InjectMocks
    private StudentServiceImpl studentService;

    private Student student;

    @BeforeEach
    void setUp() {
        student = Student.builder()
                .id(1L)
                .name("Harsh")
                .email("harsh@gmail.com")
                .build();
    }

    @Test
    void testCreateStudent_success() {
        when(studentRepository.findByEmail(student.getEmail()))
                .thenReturn(Optional.empty());

        when(studentRepository.save(student))
                .thenReturn(student);

        Student result = studentService.create(student);

        assertNotNull(result);
        assertEquals("Harsh", result.getName());
        verify(studentRepository, times(1)).save(student);
    }

    @Test
    void testCreateStudent_emailAlreadyExists() {
        when(studentRepository.findByEmail(student.getEmail()))
                .thenReturn(Optional.of(student));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            studentService.create(student);
        });

        assertEquals("Email already exists", exception.getMessage());
    }

    @Test
    void testFindById_success() {
        when(studentRepository.findById(1L))
                .thenReturn(Optional.of(student));

        Student result = studentService.findById(1L);

        assertEquals(1L, result.getId());
    }

    @Test
    void testFindById_notFound() {
        when(studentRepository.findById(1L))
                .thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> {
            studentService.findById(1L);
        });
    }

    @Test
    void testUpdateStudent_success() {
        when(studentRepository.findById(1L)).thenReturn(Optional.of(student));
        when(studentRepository.save(any(Student.class))).thenReturn(student);

        Student updated = studentService.update(1L, student);

        assertNotNull(updated);
        verify(studentRepository, times(1)).save(any(Student.class));
    }

    @Test
    void testDeleteStudent_success() {
        when(studentRepository.findById(1L)).thenReturn(Optional.of(student));

        studentService.delete(1L);

        verify(studentRepository, times(1)).save(student);
    }
}