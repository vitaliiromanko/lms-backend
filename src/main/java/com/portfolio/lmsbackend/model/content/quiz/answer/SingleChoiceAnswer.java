package com.portfolio.lmsbackend.model.content.quiz.answer;

import com.portfolio.lmsbackend.enums.content.quiz.QuestionType;
import com.portfolio.lmsbackend.model.content.quiz.Attempt;
import com.portfolio.lmsbackend.model.content.quiz.QuizQuestion;
import com.portfolio.lmsbackend.model.content.quiz.question.ChoiceOption;
import com.portfolio.lmsbackend.model.content.quiz.question.SingleChoiceQuestion;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.AssertTrue;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static com.portfolio.lmsbackend.enums.content.quiz.QuestionType.SINGLE_CHOICE;

@Entity
@Table(name = "single_choice_answer")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class SingleChoiceAnswer extends Answer {
    @ManyToOne
    @JoinColumn(name = "selected_option_id", updatable = false)
    private ChoiceOption selectedOption;

    public SingleChoiceAnswer(Attempt attempt, QuizQuestion quizQuestion, ChoiceOption selectedOption) {
        super(attempt, quizQuestion);
        this.selectedOption = selectedOption;
    }

    @Override
    protected QuestionType getSupportedQuestionType() {
        return SINGLE_CHOICE;
    }

    @AssertTrue
    protected boolean isSelectedOptionValid() {
        if (selectedOption == null) {
            return true;
        }

        if (!(getQuizQuestion().getQuestion() instanceof SingleChoiceQuestion scq)) {
            return false;
        }

        return scq.getOptions().contains(selectedOption);
    }


    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "id = " + getId() + ", " +
                "attempt = " + getAttempt() + ", " +
                "status = " + getStatus() + ", " +
                "quizQuestion = " + getQuizQuestion() + ", " +
                "selectedOption = " + getSelectedOption() + ", " +
                "score = " + getScore() + ")";
    }
}
