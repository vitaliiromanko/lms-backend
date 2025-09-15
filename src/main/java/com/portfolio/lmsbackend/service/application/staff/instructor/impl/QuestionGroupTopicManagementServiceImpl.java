package com.portfolio.lmsbackend.service.application.staff.instructor.impl;

import com.portfolio.lmsbackend.dto.staff.instructor.management.questiongroup.response.QuestionGroupSummary;
import com.portfolio.lmsbackend.dto.staff.instructor.management.questiongrouptopic.request.CreateQuestionGroupTopicRequest;
import com.portfolio.lmsbackend.dto.staff.instructor.management.questiongrouptopic.request.UpdateQuestionGroupTopicParentRequest;
import com.portfolio.lmsbackend.dto.staff.instructor.management.questiongrouptopic.request.UpdateQuestionGroupTopicRequest;
import com.portfolio.lmsbackend.dto.staff.instructor.management.questiongrouptopic.response.GetQuestionGroupRootTopicResponse;
import com.portfolio.lmsbackend.dto.staff.instructor.management.questiongrouptopic.response.GetQuestionGroupTopicResponse;
import com.portfolio.lmsbackend.dto.staff.instructor.management.questiongrouptopic.response.QuestionGroupTopicSummary;
import com.portfolio.lmsbackend.exception.course.QuestionGroupTopicAlreadyExistsException;
import com.portfolio.lmsbackend.exception.course.QuestionGroupTopicHierarchyConflictException;
import com.portfolio.lmsbackend.model.content.quiz.question.questiongrouptopic.QuestionGroupTopic;
import com.portfolio.lmsbackend.model.content.quiz.question.questiongrouptopic.UserQuestionGroupTopic;
import com.portfolio.lmsbackend.repository.course.QuestionGroupRepository;
import com.portfolio.lmsbackend.repository.course.UserQuestionGroupTopicRepository;
import com.portfolio.lmsbackend.service.application.helper.ArchiveHelper;
import com.portfolio.lmsbackend.service.application.helper.UserQuestionGroupTopicServiceHelper;
import com.portfolio.lmsbackend.service.application.staff.instructor.QuestionGroupTopicManagementService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@RequiredArgsConstructor
public class QuestionGroupTopicManagementServiceImpl implements QuestionGroupTopicManagementService {
    private final UserQuestionGroupTopicRepository userQuestionGroupTopicRepository;
    private final QuestionGroupRepository questionGroupRepository;
    private final UserQuestionGroupTopicServiceHelper userQuestionGroupTopicServiceHelper;
    private final ArchiveHelper archiveHelper;

    @Override
    public void create(CreateQuestionGroupTopicRequest createQuestionGroupTopicRequest) {
        if (userQuestionGroupTopicRepository.existsByTitle(createQuestionGroupTopicRequest.title())) {
            throw new QuestionGroupTopicAlreadyExistsException();
        }

        userQuestionGroupTopicRepository.save(new UserQuestionGroupTopic(
                createQuestionGroupTopicRequest.title(),
                createQuestionGroupTopicRequest.parentId() != null
                        ? userQuestionGroupTopicServiceHelper.findByIdOrThrow(createQuestionGroupTopicRequest.parentId())
                        : null
        ));
    }

    @Override
    public GetQuestionGroupRootTopicResponse getRoot() {
        return new GetQuestionGroupRootTopicResponse(
                userQuestionGroupTopicRepository.findAllByParentIsNull().stream()
                        .map(QuestionGroupTopicSummary::new)
                        .sorted(Comparator.comparing(QuestionGroupTopicSummary::title))
                        .toList(),
                questionGroupRepository.findAllByTopicIsNull().stream()
                        .map(QuestionGroupSummary::new)
                        .sorted(Comparator.comparing(QuestionGroupSummary::title))
                        .toList());
    }

    @Override
    @Transactional
    public GetQuestionGroupTopicResponse getOne(UUID topicId) {
        return new GetQuestionGroupTopicResponse(userQuestionGroupTopicServiceHelper.findByIdOrThrow(topicId));
    }

    @Override
    public void update(UpdateQuestionGroupTopicRequest updateQuestionGroupTopicRequest) {
        UserQuestionGroupTopic topic = userQuestionGroupTopicServiceHelper.findByIdOrThrow(updateQuestionGroupTopicRequest.topicId());

        if (!topic.getTitle().equals(updateQuestionGroupTopicRequest.title())) {
            if (userQuestionGroupTopicRepository.existsByTitle(updateQuestionGroupTopicRequest.title())) {
                throw new QuestionGroupTopicAlreadyExistsException();
            }

            topic.setTitle(updateQuestionGroupTopicRequest.title());
        }

        userQuestionGroupTopicRepository.save(topic);
    }

    @Override
    @Transactional
    public void updateParent(UpdateQuestionGroupTopicParentRequest updateQuestionGroupTopicParentRequest) {
        UserQuestionGroupTopic topic = userQuestionGroupTopicServiceHelper.findByIdOrThrow(updateQuestionGroupTopicParentRequest.topicId());
        UserQuestionGroupTopic newParent = updateQuestionGroupTopicParentRequest.newParentId() != null
                ? userQuestionGroupTopicServiceHelper.findByIdOrThrow(updateQuestionGroupTopicParentRequest.newParentId())
                : null;

        if (!Objects.equals(topic.getParent(), newParent)) {
            if (newParent != null && isDescendant(topic, newParent)) {
                throw new QuestionGroupTopicHierarchyConflictException();
            }

            topic.setParent(newParent);
        }

        userQuestionGroupTopicRepository.save(topic);
    }

    @Override
    @Transactional
    public void delete(UUID topicId) {
        UserQuestionGroupTopic topic = userQuestionGroupTopicServiceHelper.findByIdOrThrow(topicId);
        archiveHelper.archiveQuestionGroups(topic);
        userQuestionGroupTopicRepository.delete(topic);
    }

    private boolean isDescendant(UserQuestionGroupTopic root, QuestionGroupTopic candidate) {
        if (root.getChildren() == null || root.getChildren().isEmpty()) {
            return false;
        }

        Deque<UserQuestionGroupTopic> stack = new ArrayDeque<>(root.getChildren());
        while (!stack.isEmpty()) {
            UserQuestionGroupTopic current = stack.pop();
            if (current.equals(candidate)) {
                return true;
            }

            if (current.getChildren() != null && !current.getChildren().isEmpty()) {
                stack.addAll(current.getChildren());
            }
        }

        return false;
    }
}
