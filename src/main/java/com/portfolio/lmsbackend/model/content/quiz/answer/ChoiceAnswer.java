package com.portfolio.lmsbackend.model.content.quiz.answer;

import com.portfolio.lmsbackend.model.content.quiz.Attempt;
import com.portfolio.lmsbackend.model.content.quiz.QuizQuestion;
import com.portfolio.lmsbackend.model.content.quiz.question.ChoiceQuestion;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Random;

@Entity
@Table(name = "choice_answer")
@Inheritance(strategy = InheritanceType.JOINED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public abstract class ChoiceAnswer extends Answer {
    @Column(name = "shuffle_seed", updatable = false)
    private Long shuffleSeed;

    protected ChoiceAnswer(Attempt attempt, QuizQuestion quizQuestion) {
        super(attempt, quizQuestion);

        if (((ChoiceQuestion) quizQuestion.getQuestion()).getShuffleOptions()) {
            this.shuffleSeed = new Random().nextLong();
        }
    }
}
