package com.portfolio.lmsbackend.service.application.helper;

import com.portfolio.lmsbackend.model.content.quiz.question.QuestionGroup;
import com.portfolio.lmsbackend.model.content.quiz.question.questiongrouptopic.ArchiveQuestionGroupTopic;
import com.portfolio.lmsbackend.model.content.quiz.question.questiongrouptopic.UserQuestionGroupTopic;
import com.portfolio.lmsbackend.repository.course.ArchiveQuestionGroupTopicRepository;
import com.portfolio.lmsbackend.repository.course.QuestionGroupRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayDeque;
import java.util.Deque;

import static com.portfolio.lmsbackend.enums.content.quiz.QuestionGroupStatus.ARCHIVED;

@Component
@RequiredArgsConstructor
public class ArchiveHelper {
    private final ArchiveQuestionGroupTopicRepository archiveQuestionGroupTopicRepository;
    private final QuestionGroupRepository questionGroupRepository;

    public ArchiveQuestionGroupTopic getArchiveTopic() {
        return archiveQuestionGroupTopicRepository.findFirstBy()
                .orElseGet(() -> archiveQuestionGroupTopicRepository.save(new ArchiveQuestionGroupTopic()));
    }

    public void archiveQuestionGroups(UserQuestionGroupTopic topic) {
        ArchiveQuestionGroupTopic archiveTopic = getArchiveTopic();

        Deque<UserQuestionGroupTopic> stack = new ArrayDeque<>();
        stack.push(topic);

        while (!stack.isEmpty()) {
            UserQuestionGroupTopic current = stack.pop();
            current.getQuestionGroups().forEach(g -> archiveQuestionGroup(g, archiveTopic));
            stack.addAll(current.getChildren());
        }
    }

    public void archiveQuestionGroup(QuestionGroup questionGroup, ArchiveQuestionGroupTopic archiveTopic) {
        questionGroup.setStatus(ARCHIVED);
        questionGroup.setTopic(archiveTopic);
        questionGroupRepository.save(questionGroup);
    }
}
