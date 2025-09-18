package com.portfolio.lmsbackend.service.application.helper;

import com.portfolio.lmsbackend.exception.course.SectionContentNotFoundException;
import com.portfolio.lmsbackend.model.content.SectionContent;
import com.portfolio.lmsbackend.model.course.Section;
import com.portfolio.lmsbackend.repository.course.SectionContentRepository;
import com.portfolio.lmsbackend.repository.course.SectionRepository;
import org.springframework.stereotype.Component;

@Component
public class SectionContentServiceHelper
        extends BaseServiceHelper<SectionContent, SectionContentRepository, SectionContentNotFoundException> {
    private final SectionRepository sectionRepository;

    protected SectionContentServiceHelper(SectionContentRepository repository, SectionRepository sectionRepository) {
        super(repository, SectionContentNotFoundException::new);
        this.sectionRepository = sectionRepository;
    }

    public void delete(SectionContent sectionContent) {
        Section section = sectionContent.getSection();
        section.getContents().remove(sectionContent);
        sectionRepository.save(section);
    }

    public static IllegalStateException unexpectedSectionContentType(SectionContent sectionContent) {
        return new IllegalStateException("Unexpected section content type: " + sectionContent.getClass().getName());
    }
}
