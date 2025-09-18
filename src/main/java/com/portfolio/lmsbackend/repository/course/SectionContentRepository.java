package com.portfolio.lmsbackend.repository.course;

import com.portfolio.lmsbackend.enums.content.SectionContentStatus;
import com.portfolio.lmsbackend.model.content.SectionContent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface SectionContentRepository extends JpaRepository<SectionContent, UUID> {
    @Query("select sc.section.id from SectionContent sc where sc.id = :sectionContentId")
    Optional<UUID> findSectionIdBySectionContentId(@Param("sectionContentId") UUID sectionContentId);

    boolean existsByIdAndStatus(UUID id, SectionContentStatus status);
}
