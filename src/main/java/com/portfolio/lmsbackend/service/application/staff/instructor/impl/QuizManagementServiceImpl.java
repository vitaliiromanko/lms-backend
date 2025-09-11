package com.portfolio.lmsbackend.service.application.staff.instructor.impl;

import com.portfolio.lmsbackend.dto.staff.instructor.management.quiz.request.*;
import com.portfolio.lmsbackend.model.content.quiz.Quiz;
import com.portfolio.lmsbackend.model.content.quiz.QuizGroup;
import com.portfolio.lmsbackend.model.content.quiz.QuizQuestion;
import com.portfolio.lmsbackend.model.course.Section;
import com.portfolio.lmsbackend.model.user.Staff;
import com.portfolio.lmsbackend.repository.course.QuizGroupRepository;
import com.portfolio.lmsbackend.repository.course.SectionRepository;
import com.portfolio.lmsbackend.service.application.helper.QuestionServiceHelper;
import com.portfolio.lmsbackend.service.application.helper.QuizGroupServiceHelper;
import com.portfolio.lmsbackend.service.application.helper.SectionServiceHelper;
import com.portfolio.lmsbackend.service.application.helper.UserServiceHelper;
import com.portfolio.lmsbackend.service.application.staff.instructor.QuizManagementService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class QuizManagementServiceImpl implements QuizManagementService {
    private final QuizGroupRepository quizGroupRepository;
    private final SectionRepository sectionRepository;
    private final QuestionServiceHelper questionServiceHelper;
    private final QuizGroupServiceHelper quizGroupServiceHelper;
    private final SectionServiceHelper sectionServiceHelper;
    private final UserServiceHelper userServiceHelper;

    @Override
    @Transactional
    public void create(String userId, CreateQuizRequest createQuizRequest) {
        Staff createdBy = userServiceHelper.findByIdAndTypeOrThrow(userId, Staff.class);
        switch (createQuizRequest) {
            case CreateQuizForNewGroupRequest createQuizForNewGroupRequest -> createForNewGroup(
                    new QuizGroup(
                            createQuizForNewGroupRequest.title(),
                            sectionServiceHelper.findByIdOrThrow(createQuizForNewGroupRequest.sectionId()),
                            createQuizForNewGroupRequest.duration(),
                            createQuizForNewGroupRequest.maxAttempts()
                    ),
                    createQuizForNewGroupRequest.newQuiz(),
                    createdBy
            );
            case CreateQuizForExistingGroupRequest createQuizForExistingGroupRequest -> createForExistingGroup(
                    quizGroupServiceHelper.findByIdOrThrow(createQuizForExistingGroupRequest.groupId()),
                    createQuizForExistingGroupRequest.newQuiz(),
                    createdBy
            );
            default -> throw new IllegalArgumentException("Invalid CreateQuizRequest");
        }
    }

    private void createForNewGroup(QuizGroup group, NewQuiz newQuiz, Staff createdBy) {
        group.getQuizzes().add(createQuiz(group, newQuiz, createdBy));
        Section section = group.getSection();
        section.getContents().add(group);
        sectionRepository.save(section);
    }

    private void createForExistingGroup(QuizGroup group, NewQuiz newQuiz, Staff createdBy) {
        group.getQuizzes().add(createQuiz(group, newQuiz, createdBy));
        quizGroupRepository.save(group);
    }

    private Quiz createQuiz(QuizGroup group, NewQuiz newQuiz, Staff createdBy) {
        return new Quiz(
                group,
                newQuiz.newQuizQuestions().stream()
                        .map(this::createQuizQuestion)
                        .toList(),
                newQuiz.shuffleQuestions(),
                createdBy
        );
    }

    private QuizQuestion createQuizQuestion(NewQuizQuestion newQuizQuestion) {
        return new QuizQuestion(
                questionServiceHelper.findByIdOrThrow(newQuizQuestion.questionId()),
                newQuizQuestion.maxScore()
        );
    }
}
