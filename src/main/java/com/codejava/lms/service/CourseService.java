// ===============================
// CourseService.java
// ===============================
package com.codejava.lms.service;

import com.codejava.lms.dto.CourseDTO;
import org.springframework.data.domain.Page;

import java.util.List;

public interface CourseService {

    CourseDTO create(CourseDTO dto);

    CourseDTO findById(Long id);

    List<CourseDTO> findAll();

    CourseDTO update(Long id, CourseDTO dto);

    void delete(Long id);

    Page<CourseDTO> search(String title,
                           String code,
                           int page,
                           int size,
                           String sortBy);
}