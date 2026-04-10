package com.codejava.lms.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Entity
@Table(name = "courses")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank(message = "Title is required")
    @Column(nullable = false)
    private String title;

    @Column(unique = true, nullable = false)
    private String code;

    @Min(value = 1, message = "Credits must be >= 1")
    @Column(nullable = false)
    private Integer credits;

    private boolean deleted = false;
}