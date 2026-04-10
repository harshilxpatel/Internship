package com.codejava.lms.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CourseDTO {

    private Long id;

    @NotBlank(message = "Title is required")  // FIX
    private String title;

    @NotBlank(message = "Code is required")   // IMPORTANT
    private String code;

    private Integer credits;
}