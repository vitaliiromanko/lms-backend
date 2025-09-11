package com.portfolio.lmsbackend.model.content.quiz.question;

import com.portfolio.lmsbackend.model.user.Staff;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.AssertTrue;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.List;

import static com.portfolio.lmsbackend.enums.content.quiz.QuestionType.MULTIPLE_CHOICE;

@Entity
@Table(name = "multiple_choice_question")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MultipleChoiceQuestion extends ChoiceQuestion {
    public MultipleChoiceQuestion(QuestionGroup group, Staff createdBy,
                                  String text, List<ChoiceOption> options, Boolean shuffleOptions) {
        super(MULTIPLE_CHOICE, group, createdBy, text, options, shuffleOptions);
    }

    @AssertTrue
    protected boolean hasAtLeastOneCorrectOption() {
        return getOptions().stream()
                .anyMatch(ChoiceOption::getCorrect);
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
