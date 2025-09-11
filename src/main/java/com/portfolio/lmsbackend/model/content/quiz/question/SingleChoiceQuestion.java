package com.portfolio.lmsbackend.model.content.quiz.question;

import com.portfolio.lmsbackend.model.user.Staff;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.AssertTrue;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.List;

import static com.portfolio.lmsbackend.enums.content.quiz.QuestionType.SINGLE_CHOICE;

@Entity
@Table(name = "single_choice_question")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SingleChoiceQuestion extends ChoiceQuestion {
    public SingleChoiceQuestion(QuestionGroup group, Staff createdBy, String text,
                                List<ChoiceOption> options, Boolean shuffleOptions) {
        super(SINGLE_CHOICE, group, createdBy, text, options, shuffleOptions);
    }

    @AssertTrue
    protected boolean hasExactlyOneCorrectOption() {
        return getOptions().stream()
                .filter(ChoiceOption::getCorrect)
                .count() == 1;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "id = " + getId() + ", " +
                "type = " + getType() + ", " +
                "group = " + getGroup() + ", " +
                "text = " + getText() + ", " +
                "shuffleOptions = " + getShuffleOptions() + ", " +
                "createdBy = " + getCreatedBy() + ", " +
                "createdAt = " + getCreatedAt() + ")";
    }
}
