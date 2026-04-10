package com.codejava.lms.mapper;

import com.codejava.lms.dto.CourseDTO;
import com.codejava.lms.entity.Course;

public class CourseMapper {

    public static Course toEntity(CourseDTO dto) {
        return Course.builder()
                .id(dto.getId())
                .title(dto.getTitle())
                .code(dto.getCode())
                .credits(dto.getCredits())
                .build();
    }

    public static CourseDTO toDTO(Course entity) {
        return CourseDTO.builder()
                .id(entity.getId())
                .title(entity.getTitle())
                .code(entity.getCode())
                .credits(entity.getCredits())
                .build();
    }
}