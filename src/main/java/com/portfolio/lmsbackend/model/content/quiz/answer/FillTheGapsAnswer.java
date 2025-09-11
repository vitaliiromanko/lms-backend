package com.portfolio.lmsbackend.model.content.quiz.answer;

import com.portfolio.lmsbackend.enums.content.quiz.QuestionType;
import com.portfolio.lmsbackend.model.content.quiz.Attempt;
import com.portfolio.lmsbackend.model.content.quiz.QuizQuestion;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

import static com.portfolio.lmsbackend.enums.content.quiz.QuestionType.FILL_THE_GAPS;
import static jakarta.persistence.CascadeType.ALL;

@Entity
@Table(name = "fill_the_gaps_answer")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class FillTheGapsAnswer extends Answer {
    @OneToMany(targetEntity = GapAnswerSegment.class,
            mappedBy = "fillTheGapsAnswer", cascade = ALL, orphanRemoval = true)
    private Set<GapAnswerSegment> gapAnswerSegments = new HashSet<>();

    public FillTheGapsAnswer(Attempt attempt, QuizQuestion quizQuestion, Set<GapAnswerSegment> gapAnswerSegments) {
        super(attempt, quizQuestion);

        gapAnswerSegments.forEach(t -> t.setFillTheGapsAnswer(this));
        this.gapAnswerSegments = gapAnswerSegments;
    }

    @Override
    protected QuestionType getSupportedQuestionType() {
        return FILL_THE_GAPS;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "id = " + getId() + ", " +
                "attempt = " + getAttempt() + ", " +
                "status = " + getStatus() + ", " +
                "quizQuestion = " + getQuizQuestion() + ", " +
                "score = " + getScore() + ")";
    }
}
