package com.codejava.lms.service;

import com.codejava.lms.entity.Course;
import com.codejava.lms.repository.CourseRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class CourseServiceImplTest {

    @Mock
    private CourseRepository courseRepository;

    @InjectMocks
    private CourseServiceImpl courseService;

    // CREATE TEST
    @Test
    void testCreateCourse_Success() {
        Course course = Course.builder()
                .title("Java")
                .code("J101")
                .credits(4)
                .deleted(false)
                .build();

        when(courseRepository.findByCode("J101")).thenReturn(Optional.empty());
        when(courseRepository.save(course)).thenReturn(course);

        Course result = courseService.create(course);

        assertNotNull(result);
        assertEquals("Java", result.getTitle());
        verify(courseRepository).save(course);
    }

    // DUPLICATE CODE TEST
    @Test
    void testCreateCourse_DuplicateCode() {
        Course course = Course.builder()
                .title("Java")
                .code("J101")
                .build();

        when(courseRepository.findByCode("J101"))
                .thenReturn(Optional.of(course));

        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> courseService.create(course));

        assertEquals("Course code already exists", ex.getMessage());
        verify(courseRepository, never()).save(any());
    }

    // GET BY ID SUCCESS
    @Test
    void testFindById_Success() {
        Course course = Course.builder()
                .id(1L)
                .title("Spring")
                .build();

        when(courseRepository.findById(1L))
                .thenReturn(Optional.of(course));

        Course result = courseService.findById(1L);

        assertEquals("Spring", result.getTitle());
    }

    // GET BY ID NOT FOUND
    @Test
    void testFindById_NotFound() {
        when(courseRepository.findById(1L))
                .thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> courseService.findById(1L));

        assertEquals("Course not found", ex.getMessage());
    }

    // GET ALL (ONLY NOT DELETED)
    @Test
    void testFindAll() {
        List<Course> courses = List.of(
                Course.builder().id(1L).title("Java").deleted(false).build(),
                Course.builder().id(2L).title("Spring").deleted(false).build()
        );

        when(courseRepository.findByDeletedFalse()).thenReturn(courses);

        List<Course> result = courseService.findAll();

        assertEquals(2, result.size());
    }

    // UPDATE SUCCESS
    @Test
    void testUpdateCourse() {
        Course existing = Course.builder()
                .id(1L)
                .title("Old")
                .code("O101")
                .credits(3)
                .build();

        Course updated = Course.builder()
                .title("New")
                .code("N101")
                .credits(4)
                .build();

        when(courseRepository.findById(1L))
                .thenReturn(Optional.of(existing));
        when(courseRepository.save(existing))
                .thenReturn(existing);

        Course result = courseService.update(1L, updated);

        assertEquals("New", result.getTitle());
        assertEquals("N101", result.getCode());
        assertEquals(4, result.getCredits());
    }

    // UPDATE NOT FOUND
    @Test
    void testUpdate_NotFound() {
        when(courseRepository.findById(1L))
                .thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> courseService.update(1L, new Course()));

        assertEquals("Course not found", ex.getMessage());
    }

    // DELETE (SOFT DELETE)
    @Test
    void testDeleteCourse() {
        Course course = Course.builder()
                .id(1L)
                .deleted(false)
                .build();

        when(courseRepository.findById(1L))
                .thenReturn(Optional.of(course));

        courseService.delete(1L);

        assertTrue(course.isDeleted());
        verify(courseRepository).save(course);
    }

    // DELETE NOT FOUND
    @Test
    void testDelete_NotFound() {
        when(courseRepository.findById(1L))
                .thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> courseService.delete(1L));

        assertEquals("Course not found", ex.getMessage());
    }
}