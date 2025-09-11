package com.portfolio.lmsbackend.service.application.answer;

import com.portfolio.lmsbackend.enums.content.quiz.QuestionType;
import org.springframework.stereotype.Service;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;

@Service
public class AnswerHandlerRegistry {
    private final Map<QuestionType, AnswerHandler> handlerMap = new EnumMap<>(QuestionType.class);

    private AnswerHandlerRegistry(List<AnswerHandler> handlers) {
        for (AnswerHandler handler : handlers) {
            for (QuestionType type : handler.getSupportedQuestionTypes()) {
                if (handlerMap.containsKey(type)) {
                    throw new IllegalStateException("Duplicate handler for question type: " + type);
                }
                handlerMap.put(type, handler);
            }
        }
    }

    public AnswerHandler getHandler(QuestionType type) {
        AnswerHandler handler = handlerMap.get(type);
        if (handler == null) {
            throw new IllegalArgumentException("No handler registered for question type: " + type);
        }
        return handler;
    }
}
