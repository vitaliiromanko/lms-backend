package com.portfolio.lmsbackend.repository.course;

import com.portfolio.lmsbackend.model.content.SectionContent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface SectionContentRepository extends JpaRepository<SectionContent, UUID> {
}
