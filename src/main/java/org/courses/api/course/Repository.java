package org.courses.api.course;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface Repository extends JpaRepository<CourseEntity, UUID> {

    @Query("SELECT c FROM course c WHERE (:name IS NULL OR c.name ILIKE %:name%) " +
            "AND (:courseCategoryId IS NULL OR c.courseCategory.id = :courseCategoryId)")
    List<CourseEntity> findAllWithFilters(
            @Param("name") String name,
            @Param("courseCategoryId") Short courseCategoryId
    );
}
