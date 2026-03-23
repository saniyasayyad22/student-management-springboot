package com.saniya.studentmanagement.dto;

import jakarta.validation.constraints.*;
import lombok.*;

// DTO (Data Transfer Object) - used to send/receive data via API
// We never expose the Entity directly to the outside world
// This separates the API layer from the database layer
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class StudentDTO {

    private Long id;

    @NotBlank(message = "First name is required")
    private String firstName;

    @NotBlank(message = "Last name is required")
    private String lastName;

    @NotBlank(message = "Email is required")
    @Email(message = "Please provide a valid email")
    private String email;

    @Pattern(regexp = "^[0-9]{10}$", message = "Phone number must be 10 digits")
    private String phoneNumber;

    @NotBlank(message = "Department is required")
    private String department;

    @DecimalMin(value = "0.0", message = "CGPA cannot be less than 0")
    @DecimalMax(value = "10.0", message = "CGPA cannot be more than 10")
    private Double cgpa;
}
