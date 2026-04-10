package com.codejava.lms.repository;

import com.codejava.lms.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Optional;

public interface CourseRepository extends
        JpaRepository<Course, Long>,
        JpaSpecificationExecutor<Course> {

    Optional<Course> findByCode(String code);

    List<Course> findByDeletedFalse();
}