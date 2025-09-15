package com.portfolio.lmsbackend.dto.staff.instructor.management.quiz.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.portfolio.lmsbackend.validation.annotation.NonNegativeDuration;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.Duration;
import java.util.UUID;

public class CreateQuizForNewGroupRequest extends CreateQuizRequest {
    @NotNull
    private final UUID sectionId;

    @NotBlank
    @Size(max = 100)
    private final String title;

    @NonNegativeDuration
    private final Duration duration;

    @Min(value = 1)
    private final Integer maxAttempts;

    @JsonCreator
    public CreateQuizForNewGroupRequest(
            @JsonProperty("section_id") UUID sectionId,
            @JsonProperty("title") String title,
            @JsonProperty("duration") Duration duration,
            @JsonProperty("max_attempts") Integer maxAttempts,
            @JsonProperty("new_quiz") NewQuiz newQuiz
    ) {
        super(newQuiz);
        this.sectionId = sectionId;
        this.title = title;
        this.duration = duration;
        this.maxAttempts = maxAttempts;
    }

    @JsonProperty("section_id")
    public UUID sectionId() {
        return sectionId;
    }

    @JsonProperty("title")
    public String title() {
        return title;
    }

    @JsonProperty("duration")
    public Duration duration() {
        return duration;
    }

    @JsonProperty("max_attempts")
    public Integer maxAttempts() {
        return maxAttempts;
    }
}
