package com.portfolio.lmsbackend.repository.course;

import com.portfolio.lmsbackend.enums.course.SectionStatus;
import com.portfolio.lmsbackend.model.course.Section;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface SectionRepository extends JpaRepository<Section, UUID> {
    @Query("select s.course.id from Section s where s.id = :sectionId")
    Optional<UUID> findCourseIdBySectionId(@Param("sectionId") UUID sectionId);

    boolean existsByIdAndStatus(UUID id, SectionStatus status);
}
