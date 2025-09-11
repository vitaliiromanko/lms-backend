package com.portfolio.lmsbackend.model.content.quiz.answer;

import com.portfolio.lmsbackend.enums.content.quiz.QuestionType;
import com.portfolio.lmsbackend.model.content.quiz.Attempt;
import com.portfolio.lmsbackend.model.content.quiz.QuizQuestion;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static com.portfolio.lmsbackend.enums.content.quiz.QuestionType.TEXT_LONG;

@Entity
@Table(name = "text_long_answer")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class TextLongAnswer extends Answer {
    @Lob
    @Column(name = "text", updatable = false)
    private String text;

    public TextLongAnswer(Attempt attempt, QuizQuestion quizQuestion, String text) {
        super(attempt, quizQuestion);
        this.text = text;
    }

    @Override
    protected QuestionType getSupportedQuestionType() {
        return TEXT_LONG;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "id = " + getId() + ", " +
                "attempt = " + getAttempt() + ", " +
                "status = " + getStatus() + ", " +
                "quizQuestion = " + getQuizQuestion() + ", " +
                "text = " + getText() + ", " +
                "score = " + getScore() + ")";
    }
}
