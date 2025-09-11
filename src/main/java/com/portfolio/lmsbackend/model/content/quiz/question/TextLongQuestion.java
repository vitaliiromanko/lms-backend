package com.portfolio.lmsbackend.model.content.quiz.question;

import com.portfolio.lmsbackend.model.user.Staff;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static com.portfolio.lmsbackend.enums.content.quiz.QuestionType.TEXT_LONG;

@Entity
@Table(name = "text_long_question")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class TextLongQuestion extends Question {
    @Lob
    @Column(name = "text", nullable = false, updatable = false)
    private String text;

    public TextLongQuestion(QuestionGroup questionGroup, Staff createdBy, String text) {
        super(TEXT_LONG, questionGroup, createdBy);
        this.text = text;
    }

    @Override
    public String getTextWithImagePlaceholders() {
        return text;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "id = " + getId() + ", " +
                "type = " + getType() + ", " +
                "group = " + getGroup() + ", " +
                "text = " + getText() + ", " +
                "createdBy = " + getCreatedBy() + ", " +
                "createdAt = " + getCreatedAt() + ")";
    }
}
