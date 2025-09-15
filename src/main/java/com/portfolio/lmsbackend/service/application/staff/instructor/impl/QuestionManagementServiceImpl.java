package com.portfolio.lmsbackend.service.application.staff.instructor.impl;

import com.portfolio.lmsbackend.dto.staff.instructor.management.question.request.*;
import com.portfolio.lmsbackend.exception.media.InvalidMediaPlaceholderException;
import com.portfolio.lmsbackend.model.content.quiz.question.*;
import com.portfolio.lmsbackend.model.user.Staff;
import com.portfolio.lmsbackend.repository.course.QuestionGroupRepository;
import com.portfolio.lmsbackend.service.application.helper.QuestionGroupServiceHelper;
import com.portfolio.lmsbackend.service.application.helper.UserQuestionGroupTopicServiceHelper;
import com.portfolio.lmsbackend.service.application.helper.UserServiceHelper;
import com.portfolio.lmsbackend.service.application.staff.instructor.QuestionImageService;
import com.portfolio.lmsbackend.service.application.staff.instructor.QuestionManagementService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;

import static com.portfolio.lmsbackend.utils.StringsHelper.MEDIA_PLACEHOLDER;

@Service
@RequiredArgsConstructor
public class QuestionManagementServiceImpl implements QuestionManagementService {
    private final QuestionGroupRepository questionGroupRepository;
    private final QuestionImageService questionImageService;
    private final QuestionGroupServiceHelper questionGroupServiceHelper;
    private final UserServiceHelper userServiceHelper;
    private final UserQuestionGroupTopicServiceHelper userQuestionGroupTopicServiceHelper;

    @Override
    @Transactional
    public void create(UUID userId, CreateQuestionRequest createQuestionRequest, List<MultipartFile> images) {
        QuestionGroup group = getQuestionGroup(createQuestionRequest);
        saveQuestion(
                group,
                createQuestion(group, userId, createQuestionRequest.newQuestion()),
                images
        );
    }

    private QuestionGroup getQuestionGroup(CreateQuestionRequest createQuestionRequest) {
        return switch (createQuestionRequest) {
            case CreateQuestionForNewGroupRequest createQuestionForNewGroupRequest -> new QuestionGroup(
                    createQuestionForNewGroupRequest.title(),
                    createQuestionForNewGroupRequest.topicId() != null
                            ? userQuestionGroupTopicServiceHelper.findByIdOrThrow(createQuestionForNewGroupRequest.topicId())
                            : null
            );
            case CreateQuestionForExistingGroupRequest createQuestionForExistingGroupRequest ->
                    questionGroupServiceHelper.findByIdAndNotArchivedOrThrow(
                            createQuestionForExistingGroupRequest.groupId());
            default -> throw new IllegalArgumentException("Invalid CreateQuestionRequest");
        };
    }

    private void saveQuestion(QuestionGroup group, Question question, List<MultipartFile> images) {
        if (!validateImagePlaceholders(question.getTextWithImagePlaceholders(), images != null ? images.size() : 0)) {
            throw new InvalidMediaPlaceholderException();
        }

        group.getQuestions().add(question);
        questionGroupRepository.save(group);

        if (images != null && !images.isEmpty()) {
            saveImages(question, images);
        }
    }

    private void saveImages(Question question, List<MultipartFile> images) {
        images.forEach(image -> question.getImages().add(
                questionImageService.saveQuestionImage(image, question)
        ));
    }

    private Question createQuestion(QuestionGroup group, UUID userId, NewQuestion newQuestion) {
        Staff createdBy = userServiceHelper.findByIdAndTypeOrThrow(userId, Staff.class);

        return switch (newQuestion) {
            case NewChoiceQuestion newChoiceQuestion -> createChoiceQuestion(group, createdBy, newChoiceQuestion);
            case NewFillTheGapsQuestion newFillTheGapsQuestion ->
                    createFillTheGapsQuestion(group, createdBy, newFillTheGapsQuestion);
            case NewTextLongQuestion newTextLongQuestion ->
                    createTextLongQuestion(group, createdBy, newTextLongQuestion);
            default -> throw new IllegalArgumentException("Invalid NewQuestion");
        };
    }

    private ChoiceQuestion createChoiceQuestion(QuestionGroup group, Staff createdBy, NewChoiceQuestion newChoiceQuestion) {
        List<ChoiceOption> options = newChoiceQuestion.options().stream()
                .map(o -> new ChoiceOption(o.text(), o.correct()))
                .toList();

        return switch (newChoiceQuestion) {
            case NewSingleChoiceQuestion newSingleChoiceQuestion -> new SingleChoiceQuestion(
                    group, createdBy, newSingleChoiceQuestion.text(), options, newSingleChoiceQuestion.shuffleOptions()
            );
            case NewMultipleChoiceQuestion newMultipleChoiceQuestion -> new MultipleChoiceQuestion(
                    group, createdBy, newMultipleChoiceQuestion.text(), options, newMultipleChoiceQuestion.shuffleOptions()
            );
            default -> throw new IllegalArgumentException("Invalid newChoiceQuestion");
        };
    }

    private FillTheGapsQuestion createFillTheGapsQuestion(QuestionGroup group, Staff createdBy,
                                                          NewFillTheGapsQuestion newFillTheGapsQuestion) {
        return new FillTheGapsQuestion(
                group,
                createdBy,
                newFillTheGapsQuestion.visibleTextSegments().stream()
                        .map(VisibleTextSegment::new)
                        .toList(),
                newFillTheGapsQuestion.missingTextSegments().stream()
                        .map(MissingTextSegment::new)
                        .toList()
        );
    }

    private TextLongQuestion createTextLongQuestion(QuestionGroup group, Staff createdBy,
                                                    NewTextLongQuestion newTextLongQuestion) {
        return new TextLongQuestion(group, createdBy, newTextLongQuestion.text());
    }

    private static boolean validateImagePlaceholders(String text, int imageCount) {
        Matcher matcher = MEDIA_PLACEHOLDER.matcher(text);
        int index = 0;

        while (matcher.find()) {
            if ("image".equalsIgnoreCase(matcher.group("type"))) {
                int id = Integer.parseInt(matcher.group("id"));
                if (id != index) {
                    return false;
                }
                index++;
            }
        }

        return index == imageCount;
    }
}
