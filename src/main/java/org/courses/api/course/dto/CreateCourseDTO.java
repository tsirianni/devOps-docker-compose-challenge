package org.courses.api.course.dto;

import jakarta.validation.constraints.*;

public record CreateCourseDTO(
        @NotBlank
        @NotEmpty(message = "Name cannot be empty")
        @NotNull(message = "Name cannot be null")
        @Size(max = 255, message = "Name should not exceed 255 characters")
        @Pattern(regexp = "^[A-Za-z -]+$", message = "Name can only contain letters and spaces")
        String name,

        @NotNull(message = "Category cannot be null")
        @Min(1)
        short category
) {
}
