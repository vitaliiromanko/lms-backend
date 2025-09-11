package com.portfolio.lmsbackend.repository.course;

import com.portfolio.lmsbackend.model.course.Section;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface SectionRepository extends JpaRepository<Section, UUID> {
}
