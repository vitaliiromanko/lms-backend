package com.portfolio.lmsbackend.model.media.image;

import com.portfolio.lmsbackend.model.content.quiz.question.Question;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "question_image")
@DiscriminatorValue("QUESTION_IMAGE")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class QuestionImage extends Image {
    @ManyToOne
    @JoinColumn(name = "question_id", nullable = false, updatable = false)
    private Question question;

    public QuestionImage(String originalFilename, String url, String publicId, Question question) {
        super(originalFilename, url, publicId);
        this.question = question;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "id = " + getId() + ", " +
                "originalFilename = " + getOriginalFilename() + ", " +
                "url = " + getUrl() + ", " +
                "publicId = " + getPublicId() + ", " +
                "question = " + getQuestion() + ", " +
                "createdAt = " + getCreatedAt() + ")";
    }
}
