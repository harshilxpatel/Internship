package com.codejava.lms.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class EnrollmentRequestDTO {

    @NotNull
    private Long studentId;

    @NotNull
    private Long courseId;
}