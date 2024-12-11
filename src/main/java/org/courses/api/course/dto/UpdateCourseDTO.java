package org.courses.api.course.dto;

import jakarta.validation.constraints.*;

import java.util.Optional;

public record UpdateCourseDTO(

        Optional<
                @Size(max = 255, message = "Name should not exceed 255 characters")
                @Pattern(regexp = "^[A-Za-z -]+$", message = "Name can only contain letters and spaces")
                        String> name,


        Optional<@Min(1) Short> category
) {
}
