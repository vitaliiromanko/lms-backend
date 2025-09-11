package com.portfolio.lmsbackend.model.content.quiz.question;

import com.portfolio.lmsbackend.model.content.quiz.TextSegment;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "visible_text_segment")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class VisibleTextSegment extends TextSegment {
    @Setter(AccessLevel.PROTECTED)
    @ManyToOne
    @JoinColumn(name = "fill_the_gaps_question_id", nullable = false, updatable = false)
    private FillTheGapsQuestion fillTheGapsQuestion;

    public VisibleTextSegment(String text) {
        super(text);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "id = " + getId() + ", " +
                "text = " + getText() + ", " +
                "fillTheGapsQuestion = " + getFillTheGapsQuestion() + ")";
    }
}
