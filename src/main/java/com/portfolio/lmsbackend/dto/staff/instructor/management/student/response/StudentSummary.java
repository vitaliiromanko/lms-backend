package com.portfolio.lmsbackend.dto.staff.instructor.management.student.response;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.portfolio.lmsbackend.model.user.Student;

import java.util.UUID;

public class StudentSummary {
    private final UUID id;
    private final String firstName;
    private final String lastName;
    private final String email;
    private final String photoUrl;

    public StudentSummary(Student student) {
        this(
                student.getId(),
                student.getFirstName(),
                student.getLastName(),
                student.getEmail(),
                student.getPhoto() != null ? student.getPhoto().getUrl() : null
        );
    }

    @JsonCreator
    protected StudentSummary(
            @JsonProperty("id") UUID id,
            @JsonProperty("first_name") String firstName,
            @JsonProperty("last_name") String lastName,
            @JsonProperty("email") String email,
            @JsonProperty("photo_url") String photoUrl
    ) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.photoUrl = photoUrl;
    }

    @JsonProperty("id")
    public UUID id() {
        return id;
    }

    @JsonProperty("first_name")
    public String firstName() {
        return firstName;
    }

    @JsonProperty("last_name")
    public String lastName() {
        return lastName;
    }

    @JsonProperty("email")
    public String email() {
        return email;
    }

    @JsonProperty("photo_url")
    public String photoUrl() {
        return photoUrl;
    }
}
