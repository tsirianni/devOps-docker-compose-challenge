package org.courses.api.course;

import jakarta.validation.Valid;
import jakarta.websocket.server.PathParam;
import org.courses.api.course.dto.*;
import org.courses.api.exceptions.ErrorTypes;
import org.courses.api.exceptions.NotFound;
import org.courses.api.exceptions.dto.UnprocessableEntityDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/courses")
public class Controller {

    @Autowired
    CourseService service;

    @PostMapping
    public ResponseEntity<Object> create(@Valid @RequestBody CreateCourseDTO body) {
        try {
            var course = this.service.createCourse(body);
            return ResponseEntity.ok().body(course);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.unprocessableEntity().body(
                    new UnprocessableEntityDTO(e.getMessage(), ErrorTypes.INVALID_CATEGORY.getErrorMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@Valid @PathParam("id") UUIDDTO data) {
        try {
            this.service.deleteCourse(UUID.fromString(data.id()));
            return ResponseEntity.noContent().build();
        } catch (NotFound ex) {
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping("/{id}/active")
    public ResponseEntity<Object> toggleCourseState(@Valid @PathParam("id") UUIDDTO data) {
        try {
            var course = this.service.toggleCourseState(UUID.fromString(data.id()));
            return ResponseEntity.ok().body(course);
        } catch (NotFound ex) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<Object>> findAllCourses(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Short courseCategoryId
    ) {
        var dto = new FindAllCoursesDTO(Optional.ofNullable(name), Optional.ofNullable(courseCategoryId));

        var courses = this.service.findAllCourses(dto);
        return ResponseEntity.ok().body(Arrays.asList(courses.toArray()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateCourse(@Valid @PathParam("id") UUIDDTO uuidDTO, @Valid @RequestBody UpdateCourseDTO data) {
        try {
            var course = this.service.updateCourse(UUID.fromString(uuidDTO.id()), data);
            return ResponseEntity.ok().body(course);
        } catch (NotFound ex) {
            return ResponseEntity.notFound().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.unprocessableEntity().body(
                    new UnprocessableEntityDTO(e.getMessage(), ErrorTypes.INVALID_CATEGORY.getErrorMessage()));
        }
    }
}
