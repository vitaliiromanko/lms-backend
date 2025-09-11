package com.portfolio.lmsbackend.dto.staff.instructor.management.question.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;

public abstract class NewChoiceQuestion extends NewQuestion {
    @NotBlank
    private final String text;

    @NotEmpty
    @Size(min = 2)
    private final List<@Valid NewChoiceOption> options;

    @NotNull
    private final Boolean shuffleOptions;

    @JsonCreator
    protected NewChoiceQuestion(
            @JsonProperty("text") String text,
            @JsonProperty("options") List<NewChoiceOption> options,
            @JsonProperty("shuffle_options") Boolean shuffleOptions
    ) {
        this.text = text;
        this.options = options;
        this.shuffleOptions = shuffleOptions;
    }

    @JsonProperty("text")
    public String text() {
        return text;
    }

    @JsonProperty("options")
    public List<NewChoiceOption> options() {
        return options;
    }

    @JsonProperty("shuffle_options")
    public Boolean shuffleOptions() {
        return shuffleOptions;
    }
}
