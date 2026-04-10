package com.codejava.lms.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StudentDTO {

    private Long id;
    private String name;
    private String email;
}