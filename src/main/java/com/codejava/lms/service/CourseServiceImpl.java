package com.codejava.lms.service;

import com.codejava.lms.dto.CourseDTO;
import com.codejava.lms.entity.Course;
import com.codejava.lms.exception.DuplicateResourceException;
import com.codejava.lms.exception.ResourceNotFoundException;
import com.codejava.lms.mapper.CourseMapper;
import com.codejava.lms.repository.CourseRepository;
import com.codejava.lms.specification.CourseSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRepository;

    @Override
    public CourseDTO create(CourseDTO dto) {

        courseRepository.findByCode(dto.getCode())
                .ifPresent(course -> {
                    throw new DuplicateResourceException("Course code already exists");
                });

        Course saved = courseRepository.save(CourseMapper.toEntity(dto));

        return CourseMapper.toDTO(saved);
    }

    @Override
    public CourseDTO findById(Long id) {

        Course course = courseRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Course not found"));

        return CourseMapper.toDTO(course);
    }

    @Override
    public List<CourseDTO> findAll() {

        return courseRepository.findByDeletedFalse()
                .stream()
                .map(CourseMapper::toDTO)
                .toList();
    }

    @Override
    public CourseDTO update(Long id, CourseDTO dto) {

        Course existing = courseRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Course not found"));

        courseRepository.findByCode(dto.getCode())
                .filter(course -> !course.getId().equals(id))
                .ifPresent(course -> {
                    throw new DuplicateResourceException("Course code already exists");
                });

        existing.setTitle(dto.getTitle());
        existing.setCode(dto.getCode());
        existing.setCredits(dto.getCredits());

        Course updated = courseRepository.save(existing);

        return CourseMapper.toDTO(updated);
    }

    @Override
    public void delete(Long id) {

        Course course = courseRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Course not found"));

        course.setDeleted(true);

        courseRepository.save(course);
    }

    @Override
    public Page<CourseDTO> search(String title,
                                  String code,
                                  int page,
                                  int size,
                                  String sortBy) {

        Pageable pageable =
                PageRequest.of(page, size, Sort.by(sortBy));

        Specification<Course> spec =
                CourseSpecification.notDeleted()
                        .and(CourseSpecification.hasTitle(title))
                        .and(CourseSpecification.hasCode(code));

        return courseRepository.findAll(spec, pageable)
                .map(CourseMapper::toDTO);
    }
}