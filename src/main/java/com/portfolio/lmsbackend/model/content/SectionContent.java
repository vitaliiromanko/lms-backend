package com.portfolio.lmsbackend.model.content;

import com.portfolio.lmsbackend.enums.content.SectionContentStatus;
import com.portfolio.lmsbackend.enums.content.SectionContentType;
import com.portfolio.lmsbackend.model.BaseEntity;
import com.portfolio.lmsbackend.model.course.Section;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import static com.portfolio.lmsbackend.enums.content.SectionContentStatus.VISIBLE;
import static jakarta.persistence.EnumType.STRING;

@Entity
@Table(name = "section_content")
@Inheritance(strategy = InheritanceType.JOINED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
public abstract class SectionContent extends BaseEntity {
    @Setter(AccessLevel.NONE)
    @Enumerated(value = STRING)
    @Column(name = "type", nullable = false, updatable = false)
    private SectionContentType type;

    @Column(name = "title", nullable = false, length = 100)
    private String title;

    @Enumerated(value = STRING)
    @Column(name = "status", nullable = false)
    private SectionContentStatus status = VISIBLE;

    @ManyToOne
    @JoinColumn(name = "section_id", nullable = false)
    private Section section;

    protected SectionContent(SectionContentType type, String title, Section section) {
        this.type = type;
        this.title = title;
        this.section = section;
    }
}
