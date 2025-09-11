package com.portfolio.lmsbackend.dto.staff.instructor.management.question.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.portfolio.lmsbackend.validation.annotation.ValidFillTheGapsSegments;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

import java.util.List;

@ValidFillTheGapsSegments
public class NewFillTheGapsQuestion extends NewQuestion {
    @NotEmpty
    private final List<@NotBlank String> visibleTextSegments;

    @NotEmpty
    private final List<@NotBlank String> missingTextSegments;

    @JsonCreator
    public NewFillTheGapsQuestion(
            @JsonProperty("visible_text_segments") List<String> visibleTextSegments,
            @JsonProperty("missing_text_segments") List<String> missingTextSegments
    ) {
        this.visibleTextSegments = visibleTextSegments;
        this.missingTextSegments = missingTextSegments;
    }

    @JsonProperty("visible_text_segments")
    public List<String> visibleTextSegments() {
        return visibleTextSegments;
    }

    @JsonProperty("missing_text_segments")
    public List<String> missingTextSegments() {
        return missingTextSegments;
    }
}
