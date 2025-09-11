package com.portfolio.lmsbackend.service.application.helper;

import com.portfolio.lmsbackend.model.content.quiz.quizgroupaccessrestriction.QuizGroupAccessRestriction;
import com.portfolio.lmsbackend.model.content.quiz.quizgroupaccessrestriction.QuizGroupBlockedAccessRestriction;
import com.portfolio.lmsbackend.model.content.quiz.quizgroupaccessrestriction.QuizGroupTimeWindowAccessRestriction;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class QuizGroupAccessRestrictionServiceHelper {
    public static <R> R mapQuizGroupAccessRestrictionTo(QuizGroupAccessRestriction accessRestriction,
                                                        Function<QuizGroupBlockedAccessRestriction, R> barMapper,
                                                        Function<QuizGroupTimeWindowAccessRestriction, R> twarMapper) {
        return switch (accessRestriction) {
            case QuizGroupBlockedAccessRestriction blockedAccessRestriction ->
                    barMapper.apply(blockedAccessRestriction);
            case QuizGroupTimeWindowAccessRestriction timeWindowAccessRestriction ->
                    twarMapper.apply(timeWindowAccessRestriction);
            default -> throw unexpectedQuizGroupAccessRestrictionType(accessRestriction);
        };
    }

    public static IllegalStateException unexpectedQuizGroupAccessRestrictionType(QuizGroupAccessRestriction accessRestriction) {
        return new IllegalStateException("Unexpected quizGroupAccessRestriction type: " + accessRestriction.getClass().getName());
    }
}
