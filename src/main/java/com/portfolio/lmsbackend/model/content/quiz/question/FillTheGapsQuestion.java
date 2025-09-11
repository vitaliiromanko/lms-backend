package com.portfolio.lmsbackend.model.content.quiz.question;

import com.portfolio.lmsbackend.model.user.Staff;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OrderColumn;
import jakarta.persistence.Table;
import jakarta.validation.Valid;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.portfolio.lmsbackend.enums.content.quiz.QuestionType.FILL_THE_GAPS;
import static jakarta.persistence.CascadeType.ALL;

@Entity
@Table(name = "fill_the_gaps_question")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class FillTheGapsQuestion extends Question {
    @OneToMany(mappedBy = "fillTheGapsQuestion", cascade = ALL, orphanRemoval = true)
    @OrderColumn(name = "position")
    @Size(min = 2)
    @NotEmpty
    @Valid
    private List<VisibleTextSegment> visibleTextSegments = new ArrayList<>();

    @OneToMany(mappedBy = "fillTheGapsQuestion", cascade = ALL, orphanRemoval = true)
    @OrderColumn(name = "position")
    @NotEmpty
    @Valid
    private List<MissingTextSegment> missingTextSegments = new ArrayList<>();

    public FillTheGapsQuestion(QuestionGroup group, Staff createdBy,
                               List<VisibleTextSegment> visibleTextSegments,
                               List<MissingTextSegment> missingTextSegments) {
        super(FILL_THE_GAPS, group, createdBy);

        visibleTextSegments.forEach(t -> t.setFillTheGapsQuestion(this));
        this.visibleTextSegments = visibleTextSegments;

        missingTextSegments.forEach(t -> t.setFillTheGapsQuestion(this));
        this.missingTextSegments = missingTextSegments;
    }

    @Override
    public String getTextWithImagePlaceholders() {
        return visibleTextSegments.stream()
                .map(VisibleTextSegment::getText)
                .collect(Collectors.joining(" "));
    }

    @AssertTrue
    protected boolean isMissingSegmentsCountValid() {
        return missingTextSegments.size() == (visibleTextSegments.size() - 1);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "id = " + getId() + ", " +
                "type = " + getType() + ", " +
                "group = " + getGroup() + ", " +
                "createdBy = " + getCreatedBy() + ", " +
                "createdAt = " + getCreatedAt() + ")";
    }
}
