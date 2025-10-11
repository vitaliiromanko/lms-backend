package com.portfolio.lmsbackend.model.content.quiz.answer;

import com.portfolio.lmsbackend.enums.content.quiz.QuestionType;
import com.portfolio.lmsbackend.model.content.quiz.Attempt;
import com.portfolio.lmsbackend.model.content.quiz.QuizQuestion;
import com.portfolio.lmsbackend.model.content.quiz.question.ChoiceOption;
import com.portfolio.lmsbackend.model.content.quiz.question.MultipleChoiceQuestion;
import jakarta.persistence.*;
import jakarta.validation.constraints.AssertTrue;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

import static com.portfolio.lmsbackend.enums.content.quiz.QuestionType.MULTIPLE_CHOICE;

@Entity
@Table(name = "multiple_choice_answer")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
public class MultipleChoiceAnswer extends ChoiceAnswer {
    @ManyToMany
    @JoinTable(
            name = "multiple_choice_selected_option",
            joinColumns = @JoinColumn(name = "answer_id", nullable = false),
            inverseJoinColumns = @JoinColumn(name = "choice_option_id", nullable = false)
    )
    private Set<ChoiceOption> selectedOptions = new HashSet<>();


    public MultipleChoiceAnswer(Attempt attempt, QuizQuestion quizQuestion) {
        super(attempt, quizQuestion);
    }

    @Override
    protected QuestionType getSupportedQuestionType() {
        return MULTIPLE_CHOICE;
    }

    @AssertTrue
    protected boolean areSelectedOptionsValid() {
        if (selectedOptions == null || selectedOptions.isEmpty()) {
            return true;
        }

        if (!(getQuizQuestion().getQuestion() instanceof MultipleChoiceQuestion mcq)) {
            return false;
        }

        return new HashSet<>(mcq.getOptions()).containsAll(selectedOptions);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "id = " + getId() + ", " +
                "attempt = " + getAttempt() + ", " +
                "status = " + getStatus() + ", " +
                "quizQuestion = " + getQuizQuestion() + ", " +
                "shuffleSeed = " + getShuffleSeed() + ", " +
                "score = " + getScore() + ")";
    }
}
