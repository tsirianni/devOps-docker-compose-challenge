package org.courses.api.course.dto;

import java.util.Optional;

public record FindAllCoursesDTO(
        Optional<String> name,
        Optional<Short> courseCategoryId
) {
}
