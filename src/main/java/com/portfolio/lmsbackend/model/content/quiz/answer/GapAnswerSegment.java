package com.portfolio.lmsbackend.model.content.quiz.answer;

import com.portfolio.lmsbackend.model.content.quiz.TextSegment;
import com.portfolio.lmsbackend.model.content.quiz.question.FillTheGapsQuestion;
import com.portfolio.lmsbackend.model.content.quiz.question.MissingTextSegment;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.AssertTrue;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "gap_answer_segment")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class GapAnswerSegment extends TextSegment {
    @ManyToOne
    @JoinColumn(name = "fill_the_gaps_answer_id", nullable = false, updatable = false)
    private FillTheGapsAnswer fillTheGapsAnswer;

    @ManyToOne
    @JoinColumn(name = "missing_text_segment_id", nullable = false, updatable = false)
    private MissingTextSegment missingTextSegment;

    public GapAnswerSegment(String text, FillTheGapsAnswer fillTheGapsAnswer, MissingTextSegment missingTextSegment) {
        super(text);
        this.fillTheGapsAnswer = fillTheGapsAnswer;
        this.missingTextSegment = missingTextSegment;
    }

    @AssertTrue
    protected boolean isMissingTextSegmentValid() {
        FillTheGapsQuestion question;
        try {
            question = (FillTheGapsQuestion) fillTheGapsAnswer.getQuizQuestion().getQuestion();
        } catch (ClassCastException | NullPointerException e) {
            return false;
        }

        return question.getMissingTextSegments().contains(missingTextSegment);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "id = " + getId() + ", " +
                "text = " + getText() + ", " +
                "fillTheGapsAnswer = " + getFillTheGapsAnswer() + ", " +
                "missingTextSegment = " + getMissingTextSegment() + ")";
    }
}
