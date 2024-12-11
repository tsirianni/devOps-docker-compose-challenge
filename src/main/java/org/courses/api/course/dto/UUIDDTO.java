package org.courses.api.course.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import org.courses.api.Utils;

public record UUIDDTO(
        @NotNull
        @Pattern(regexp = Utils.UUID_PATTERN, message = "Invalid UUID format")
        String id
) {
}
