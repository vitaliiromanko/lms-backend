package com.portfolio.lmsbackend.model.content.quiz.question;

import com.portfolio.lmsbackend.enums.content.quiz.QuestionType;
import com.portfolio.lmsbackend.model.media.image.QuestionImage;
import com.portfolio.lmsbackend.model.user.Staff;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.proxy.HibernateProxy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import static jakarta.persistence.CascadeType.ALL;
import static jakarta.persistence.EnumType.STRING;

@Entity
@Table(name = "question")
@Inheritance(strategy = InheritanceType.JOINED)
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public abstract class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false, updatable = false)
    private UUID id;

    @Enumerated(value = STRING)
    @Column(name = "type", nullable = false, updatable = false)
    private QuestionType type;

    @ManyToOne
    @JoinColumn(name = "group_id", nullable = false, updatable = false)
    private QuestionGroup group;

    @OneToMany(mappedBy = "question", cascade = ALL, orphanRemoval = true)
    @OrderColumn(name = "position")
    private final List<QuestionImage> images = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "created_by_id", nullable = false, updatable = false)
    private Staff createdBy;

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    protected Question(QuestionType type, QuestionGroup group, Staff createdBy) {
        this.type = type;
        this.group = group;
        this.createdBy = createdBy;
    }

    public abstract String getTextWithImagePlaceholders();

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        Question that = (Question) o;
        return getId() != null && Objects.equals(getId(), that.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
}
