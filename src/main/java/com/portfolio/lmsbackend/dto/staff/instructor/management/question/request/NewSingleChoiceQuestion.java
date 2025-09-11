package com.portfolio.lmsbackend.dto.staff.instructor.management.question.request;

import com.portfolio.lmsbackend.validation.annotation.SingleChoiceQuestionRequiresOneCorrectOption;

import java.util.List;

@SingleChoiceQuestionRequiresOneCorrectOption
public class NewSingleChoiceQuestion extends NewChoiceQuestion {
    public NewSingleChoiceQuestion(
            String text,
            List<NewChoiceOption> options,
            Boolean shuffleOptions
    ) {
        super(text, options, shuffleOptions);
    }
}
