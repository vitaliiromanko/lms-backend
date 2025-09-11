package com.portfolio.lmsbackend.service.application.helper;

import com.portfolio.lmsbackend.exception.course.QuestionNotFoundException;
import com.portfolio.lmsbackend.model.content.quiz.question.*;
import com.portfolio.lmsbackend.repository.course.QuestionRepository;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class QuestionServiceHelper extends BaseServiceHelper<Question, QuestionRepository, QuestionNotFoundException> {
    protected QuestionServiceHelper(QuestionRepository repository) {
        super(repository, QuestionNotFoundException::new);
    }

    public static <R> R mapQuestionTo(Question question,
                                      Function<SingleChoiceQuestion, R> scMapper,
                                      Function<MultipleChoiceQuestion, R> mcMapper,
                                      Function<FillTheGapsQuestion, R> ftgMapper,
                                      Function<TextLongQuestion, R> tlMapper) {
        return switch (question) {
            case SingleChoiceQuestion singleChoiceQuestion -> scMapper.apply(singleChoiceQuestion);
            case MultipleChoiceQuestion multipleChoiceQuestion -> mcMapper.apply(multipleChoiceQuestion);
            case FillTheGapsQuestion textLongQuestion -> ftgMapper.apply(textLongQuestion);
            case TextLongQuestion textLongQuestion -> tlMapper.apply(textLongQuestion);
            default -> throw unexpectedQuestionType(question);
        };
    }

    public static IllegalStateException unexpectedQuestionType(Question question) {
        return new IllegalStateException("Unexpected question type: " + question.getClass().getName());
    }
}
