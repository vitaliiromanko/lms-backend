package com.portfolio.lmsbackend.model.course;

import com.portfolio.lmsbackend.model.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "category")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
public class Category extends BaseEntity {
    @Column(name = "title", nullable = false, unique = true, length = 100)
    private String title;

    @Setter(AccessLevel.NONE)
    @OneToMany(targetEntity = Course.class, mappedBy = "category")
    private Set<Course> courses = new HashSet<>();

    public Category(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "id = " + getId() + ", " +
                "title = " + getTitle() + ", " +
                "createdAt = " + getCreatedAt() + ", " +
                "updatedAt = " + getUpdatedAt() + ")";
    }
}
