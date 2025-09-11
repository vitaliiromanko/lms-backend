package com.portfolio.lmsbackend.dto.staff.instructor.management.question.request;

import com.portfolio.lmsbackend.validation.annotation.MultipleChoiceQuestionRequiresMinOneCorrectOption;

import java.util.List;

@MultipleChoiceQuestionRequiresMinOneCorrectOption
public class NewMultipleChoiceQuestion extends NewChoiceQuestion {
    public NewMultipleChoiceQuestion(
            String text,
            List<NewChoiceOption> options,
            Boolean shuffleOptions
    ) {
        super(text, options, shuffleOptions);
    }
}
