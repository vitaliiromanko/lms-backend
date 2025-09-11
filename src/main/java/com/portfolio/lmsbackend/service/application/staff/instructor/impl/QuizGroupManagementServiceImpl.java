package com.portfolio.lmsbackend.service.application.staff.instructor.impl;

import com.portfolio.lmsbackend.dto.staff.instructor.management.quizgroup.request.UpdateQuizGroupRequest;
import com.portfolio.lmsbackend.dto.staff.instructor.management.quizgroup.response.GetQuizGroupResponse;
import com.portfolio.lmsbackend.model.content.quiz.QuizGroup;
import com.portfolio.lmsbackend.repository.course.QuizGroupRepository;
import com.portfolio.lmsbackend.service.application.helper.QuizGroupServiceHelper;
import com.portfolio.lmsbackend.service.application.staff.instructor.QuizGroupManagementService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class QuizGroupManagementServiceImpl implements QuizGroupManagementService {
    private final QuizGroupRepository quizGroupRepository;
    private final QuizGroupServiceHelper quizGroupServiceHelper;

    @Override
    @Transactional
    public GetQuizGroupResponse getOne(String groupId) {
        return new GetQuizGroupResponse(quizGroupServiceHelper.findByIdOrThrow(groupId));
    }

    @Override
    @Transactional
    public void update(UpdateQuizGroupRequest updateQuizGroupRequest) {
        QuizGroup group = quizGroupServiceHelper.findByIdOrThrow(updateQuizGroupRequest.groupId());
        group.setDuration(updateQuizGroupRequest.duration());
        group.setMaxAttempts(updateQuizGroupRequest.maxAttempts());
        quizGroupRepository.save(group);
    }
}
