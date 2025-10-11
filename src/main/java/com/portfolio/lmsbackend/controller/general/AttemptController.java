package com.portfolio.lmsbackend.controller.general;

import com.portfolio.lmsbackend.dto.general.attempt.request.FinishAttemptRequest;
import com.portfolio.lmsbackend.dto.general.attempt.request.ResetAnswerRequest;
import com.portfolio.lmsbackend.dto.general.attempt.request.StartAttemptRequest;
import com.portfolio.lmsbackend.dto.general.attempt.request.SubmitAnswerRequest;
import com.portfolio.lmsbackend.dto.general.attempt.response.GetAttemptAnswerResponse;
import com.portfolio.lmsbackend.dto.general.attempt.response.GetAttemptResponse;
import com.portfolio.lmsbackend.dto.general.attempt.response.StartAttemptResponse;
import com.portfolio.lmsbackend.service.application.general.AttemptService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

import static com.portfolio.lmsbackend.utils.StringsHelper.SUCCESS_MESSAGE;
import static org.springframework.http.HttpStatus.CREATED;

@Validated
@RestController
@RequestMapping("/attempt")
@RequiredArgsConstructor
@SecurityRequirement(name = "Bearer Authentication")
public class AttemptController {
    private final AttemptService attemptService;

    @PostMapping
    @PreAuthorize("@attemptSecurity.canStartAttempt(#authentication, #startAttemptRequest)")
    public ResponseEntity<StartAttemptResponse> startAttempt(
            @CurrentSecurityContext(expression = "authentication") Authentication authentication,
            @Valid @RequestBody StartAttemptRequest startAttemptRequest
    ) {
        return ResponseEntity.status(CREATED).body(attemptService.startAttempt(
                UUID.fromString(authentication.getName()), startAttemptRequest));
    }

    @GetMapping("/{attemptId}/answer/{answerPosition}/full")
    @PreAuthorize("@attemptSecurity.canGetAttempt(authentication, #attemptId)")
    public ResponseEntity<GetAttemptResponse> getAttempt(
            @PathVariable @NotNull UUID attemptId,
            @PathVariable @NotNull @PositiveOrZero Integer answerPosition
    ) {
        return ResponseEntity.ok().body(attemptService.getAttempt(attemptId, answerPosition));
    }

    @GetMapping("/{attemptId}/answer/{answerPosition}")
    @PreAuthorize("@attemptSecurity.canGetAttemptAnswer(authentication, #attemptId)")
    public ResponseEntity<GetAttemptAnswerResponse> getAttemptAnswer(
            @PathVariable @NotNull UUID attemptId,
            @PathVariable @NotNull @PositiveOrZero Integer answerPosition
    ) {
        return ResponseEntity.ok().body(attemptService.getAttemptAnswer(attemptId, answerPosition));
    }

    @PutMapping("/answer/submit")
    @PreAuthorize("@attemptSecurity.canSubmitAnswer(authentication, #submitAnswerRequest)")
    public ResponseEntity<String> submitAnswer(
            @Valid @RequestBody SubmitAnswerRequest submitAnswerRequest
    ) {
        attemptService.submitAnswer(submitAnswerRequest);
        return ResponseEntity.ok().body(SUCCESS_MESSAGE);
    }

    @PutMapping("/answer/reset")
    @PreAuthorize("@attemptSecurity.canResetAnswer(authentication, #resetAnswerRequest)")
    public ResponseEntity<String> resetAnswer(
            @Valid @RequestBody ResetAnswerRequest resetAnswerRequest
    ) {
        attemptService.resetAnswer(resetAnswerRequest);
        return ResponseEntity.ok().body(SUCCESS_MESSAGE);
    }

    @PutMapping("/finish")
    @PreAuthorize("@attemptSecurity.canFinishAttempt(authentication, #finishAttemptRequest)")
    public ResponseEntity<String> finishAttempt(
            @Valid @RequestBody FinishAttemptRequest finishAttemptRequest
    ) {
        attemptService.finishAttempt(finishAttemptRequest);
        return ResponseEntity.ok().body(SUCCESS_MESSAGE);
    }
}
