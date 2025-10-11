package com.portfolio.lmsbackend.validation.validator;

import com.portfolio.lmsbackend.dto.general.attempt.request.GapAnswerSegmentRequest;
import com.portfolio.lmsbackend.dto.general.attempt.request.SubmitFillTheGapsAnswerRequest;
import com.portfolio.lmsbackend.validation.annotation.UniqueMissingTextSegmentId;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

public class UniqueMissingTextSegmentIdValidator
        implements ConstraintValidator<UniqueMissingTextSegmentId, SubmitFillTheGapsAnswerRequest> {
    @Override
    public boolean isValid(SubmitFillTheGapsAnswerRequest request, ConstraintValidatorContext context) {
        if (request.gapAnswerSegments() == null) {
            return true;
        }

        Set<UUID> uniqueIds = request.gapAnswerSegments().stream()
                .map(GapAnswerSegmentRequest::missingTextSegmentId)
                .collect(Collectors.toSet());

        return uniqueIds.size() == request.gapAnswerSegments().size();
    }
}
