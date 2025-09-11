package com.portfolio.lmsbackend.service.application.staff.instructor.impl;

import com.portfolio.lmsbackend.dto.staff.instructor.management.questiongroup.request.UpdateQuestionGroupRequest;
import com.portfolio.lmsbackend.dto.staff.instructor.management.questiongroup.request.UpdateQuestionGroupStatusRequest;
import com.portfolio.lmsbackend.dto.staff.instructor.management.questiongroup.response.GetQuestionGroupResponse;
import com.portfolio.lmsbackend.model.content.quiz.question.QuestionGroup;
import com.portfolio.lmsbackend.repository.course.QuestionGroupRepository;
import com.portfolio.lmsbackend.service.application.helper.ArchiveHelper;
import com.portfolio.lmsbackend.service.application.helper.QuestionGroupServiceHelper;
import com.portfolio.lmsbackend.service.application.staff.instructor.QuestionGroupManagementService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.portfolio.lmsbackend.enums.content.quiz.QuestionGroupStatus.ARCHIVED;

@Service
@RequiredArgsConstructor
public class QuestionGroupManagementServiceImpl implements QuestionGroupManagementService {
    private final QuestionGroupRepository questionGroupRepository;
    private final QuestionGroupServiceHelper questionGroupServiceHelper;
    private final ArchiveHelper archiveHelper;

    @Override
    @Transactional
    public GetQuestionGroupResponse getOne(String groupId) {
        return new GetQuestionGroupResponse(questionGroupServiceHelper.findByIdAndNotArchivedOrThrow(groupId));
    }

    @Override
    public void update(UpdateQuestionGroupRequest updateQuestionGroupRequest) {
        QuestionGroup group = questionGroupServiceHelper.findByIdAndNotArchivedOrThrow(updateQuestionGroupRequest.groupId());
        group.setTitle(updateQuestionGroupRequest.title());
        questionGroupRepository.save(group);
    }

    @Override
    public void updateStatus(UpdateQuestionGroupStatusRequest updateQuestionGroupStatusRequest) {
        QuestionGroup group = questionGroupServiceHelper.findByIdAndNotArchivedOrThrow(updateQuestionGroupStatusRequest.groupId());

        if (updateQuestionGroupStatusRequest.newStatus() == ARCHIVED) {
            archiveHelper.archiveQuestionGroup(group, archiveHelper.getArchiveTopic());
        } else {
            group.setStatus(updateQuestionGroupStatusRequest.newStatus());
            questionGroupRepository.save(group);
        }
    }
}
