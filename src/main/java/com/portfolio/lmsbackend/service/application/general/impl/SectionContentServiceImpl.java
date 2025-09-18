package com.portfolio.lmsbackend.service.application.general.impl;

import com.portfolio.lmsbackend.dto.general.sectioncontent.quizgroup.response.GetQuizGroupResponse;
import com.portfolio.lmsbackend.dto.general.sectioncontent.quizgroup.response.GetStaffQuizGroupResponse;
import com.portfolio.lmsbackend.dto.general.sectioncontent.quizgroup.response.GetStudentQuizGroupResponse;
import com.portfolio.lmsbackend.dto.general.sectioncontent.response.GetSectionContentResponse;
import com.portfolio.lmsbackend.model.content.SectionContent;
import com.portfolio.lmsbackend.model.content.quiz.Attempt;
import com.portfolio.lmsbackend.model.content.quiz.QuizGroup;
import com.portfolio.lmsbackend.model.content.quiz.quizgroupaccessrestriction.QuizGroupAccessRestriction;
import com.portfolio.lmsbackend.model.user.Staff;
import com.portfolio.lmsbackend.model.user.Student;
import com.portfolio.lmsbackend.model.user.User;
import com.portfolio.lmsbackend.repository.course.AttemptRepository;
import com.portfolio.lmsbackend.repository.course.QuizGroupAccessRestrictionRepository;
import com.portfolio.lmsbackend.service.application.general.SectionContentService;
import com.portfolio.lmsbackend.service.application.helper.SectionContentServiceHelper;
import com.portfolio.lmsbackend.service.application.helper.UserServiceHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

import static com.portfolio.lmsbackend.service.application.helper.SectionContentServiceHelper.unexpectedSectionContentType;
import static com.portfolio.lmsbackend.service.application.helper.UserServiceHelper.unexpectedUserType;

@Service
@RequiredArgsConstructor
public class SectionContentServiceImpl implements SectionContentService {
    private final AttemptRepository attemptRepository;
    private final QuizGroupAccessRestrictionRepository quizGroupAccessRestrictionRepository;
    private final UserServiceHelper userServiceHelper;
    private final SectionContentServiceHelper sectionContentServiceHelper;

    @Override
    @Transactional
    public GetSectionContentResponse getSectionContent(UUID userId, UUID sectionContentId) {
        User user = userServiceHelper.findByIdOrThrow(userId);
        SectionContent sectionContent = sectionContentServiceHelper.findByIdOrThrow(sectionContentId);
        return switch (sectionContent) {
            case QuizGroup quizGroup -> getQuizGroup(user, quizGroup);
            default -> throw unexpectedSectionContentType(sectionContent);
        };
    }

    private GetQuizGroupResponse getQuizGroup(User user, QuizGroup quizGroup) {
        return switch (user) {
            case Staff staff -> getStaffQuizGroup(staff, quizGroup);
            case Student student -> getStudentQuizGroup(student, quizGroup);
            default -> throw unexpectedUserType(user);
        };
    }

    private GetQuizGroupResponse getStaffQuizGroup(Staff staff, QuizGroup quizGroup) {
        return new GetStaffQuizGroupResponse(quizGroup, getAttempts(staff, quizGroup));
    }

    private GetQuizGroupResponse getStudentQuizGroup(Student student, QuizGroup quizGroup) {
        return new GetStudentQuizGroupResponse(quizGroup, getAttempts(student, quizGroup),
                getAccessRestriction(student, quizGroup));
    }

    private List<Attempt> getAttempts(User user, QuizGroup quizGroup) {
        return attemptRepository.findByQuizInAndUser(quizGroup.getQuizzes(), user);
    }

    private QuizGroupAccessRestriction getAccessRestriction(Student student, QuizGroup quizGroup) {
        return quizGroupAccessRestrictionRepository.findByGroupAndStudent(quizGroup, student)
                .orElse(null);
    }
}
