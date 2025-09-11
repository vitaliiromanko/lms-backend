package com.portfolio.lmsbackend.model.content.quiz.question;

import com.portfolio.lmsbackend.enums.content.quiz.QuestionType;
import com.portfolio.lmsbackend.model.user.Staff;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static jakarta.persistence.CascadeType.ALL;

@Entity
@Table(name = "choice_question")
@Inheritance(strategy = InheritanceType.JOINED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public abstract class ChoiceQuestion extends Question {
    @Lob
    @Column(name = "text", nullable = false, updatable = false)
    private String text;

    @OneToMany(mappedBy = "choiceQuestion", cascade = ALL, orphanRemoval = true)
    @OrderColumn(name = "position")
    @Size(min = 2)
    @NotEmpty
    @Valid
    private List<ChoiceOption> options = new ArrayList<>();

    @Column(name = "shuffle_options", nullable = false, updatable = false)
    private Boolean shuffleOptions;

    protected ChoiceQuestion(QuestionType type, QuestionGroup group, Staff createdBy,
                             String text, List<ChoiceOption> options, Boolean shuffleOptions) {
        super(type, group, createdBy);
        this.text = text;

        options.forEach(o -> o.setChoiceQuestion(this));
        this.options = options;

        this.shuffleOptions = shuffleOptions;
    }

    @Override
    public String getTextWithImagePlaceholders() {
        return Stream.concat(
                        Stream.of(text),
                        options.stream()
                                .map(ChoiceOption::getText)
                )
                .collect(Collectors.joining(" "));
    }
}
