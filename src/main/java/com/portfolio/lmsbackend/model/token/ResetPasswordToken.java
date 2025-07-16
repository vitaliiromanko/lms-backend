package com.portfolio.lmsbackend.model.token;

import com.portfolio.lmsbackend.model.user.User;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "reset_password_token")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class ResetPasswordToken extends BaseTokenEntity {
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false, updatable = false)
    private User user;

    public ResetPasswordToken(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "id = " + getId() + ", " +
                "token = " + getToken() + ", " +
                "createdAt = " + getCreatedAt() + ", " +
                "user = " + getUser() + ")";
    }
}
