package com.codejava.lms.service;

import com.codejava.lms.dto.CourseDTO;
import com.codejava.lms.entity.Course;
import com.codejava.lms.repository.CourseRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CourseServiceImplTest {

    @Mock
    private CourseRepository courseRepository;

    @InjectMocks
    private CourseServiceImpl courseService;

    // ===============================
    // FIND BY ID
    // ===============================
    @Test
    void testFindById() {

        Course course = Course.builder()
                .id(1L)
                .title("Java")
                .code("J101")
                .credits(4)
                .deleted(false)
                .build();

        when(courseRepository.findById(1L))
                .thenReturn(Optional.of(course));

        CourseDTO result = courseService.findById(1L);

        assertNotNull(result);
        assertEquals("Java", result.getTitle());
        assertEquals("J101", result.getCode());
    }

    // ===============================
    // FIND ALL
    // ===============================
    @Test
    void testFindAll() {

        when(courseRepository.findByDeletedFalse())
                .thenReturn(List.of(
                        Course.builder()
                                .id(1L)
                                .title("Java")
                                .code("J101")
                                .credits(4)
                                .deleted(false)
                                .build()
                ));

        List<CourseDTO> result = courseService.findAll();

        assertEquals(1, result.size());
        assertEquals("Java", result.get(0).getTitle());
    }

    // ===============================
    // DELETE
    // ===============================
    @Test
    void testDelete() {

        Course course = Course.builder()
                .id(1L)
                .title("Java")
                .code("J101")
                .credits(4)
                .deleted(false)
                .build();

        when(courseRepository.findById(1L))
                .thenReturn(Optional.of(course));

        courseService.delete(1L);

        assertTrue(course.isDeleted());

        verify(courseRepository, times(1)).save(course);
    }

    // ===============================
    // SEARCH
    // ===============================
    @Test
    void testSearch() {

        Page<Course> page = new PageImpl<>(
                List.of(
                        Course.builder()
                                .id(1L)
                                .title("Java")
                                .code("J101")
                                .credits(4)
                                .deleted(false)
                                .build()
                )
        );

        when(courseRepository.findAll(
                any(org.springframework.data.jpa.domain.Specification.class),
                any(Pageable.class)
        )).thenReturn(page);

        Page<CourseDTO> result =
                courseService.search("Java", "J101", 0, 5, "title");

        assertEquals(1, result.getTotalElements());
        assertEquals("Java", result.getContent().get(0).getTitle());
    }

    // ===============================
    // CREATE
    // ===============================
    @Test
    void testCreate() {

        CourseDTO dto = CourseDTO.builder()
                .title("Spring Boot")
                .code("SB101")
                .credits(5)
                .build();

        when(courseRepository.findByCode("SB101"))
                .thenReturn(Optional.empty());

        when(courseRepository.save(any(Course.class)))
                .thenReturn(
                        Course.builder()
                                .id(2L)
                                .title("Spring Boot")
                                .code("SB101")
                                .credits(5)
                                .build()
                );

        CourseDTO result = courseService.create(dto);

        assertNotNull(result);
        assertEquals("Spring Boot", result.getTitle());
    }

    // ===============================
    // UPDATE
    // ===============================
    @Test
    void testUpdate() {

        Course existing = Course.builder()
                .id(1L)
                .title("Old Java")
                .code("J100")
                .credits(3)
                .build();

        CourseDTO dto = CourseDTO.builder()
                .title("Advanced Java")
                .code("J101")
                .credits(4)
                .build();

        when(courseRepository.findById(1L))
                .thenReturn(Optional.of(existing));

        when(courseRepository.findByCode("J101"))
                .thenReturn(Optional.empty());

        when(courseRepository.save(any(Course.class)))
                .thenReturn(existing);

        CourseDTO result = courseService.update(1L, dto);

        assertEquals("Advanced Java", result.getTitle());
        assertEquals("J101", result.getCode());
    }
}