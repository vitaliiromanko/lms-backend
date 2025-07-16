package com.portfolio.lmsbackend.model.token;

import com.portfolio.lmsbackend.model.user.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "refresh_token")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class RefreshToken extends BaseTokenEntity {
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false, updatable = false)
    private User user;

    @Column(name = "expired_at", nullable = false, updatable = false)
    private LocalDateTime expiredAt;

    public RefreshToken(User user, LocalDateTime expiredAt) {
        this.user = user;
        this.expiredAt = expiredAt;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "id = " + getId() + ", " +
                "token = " + getToken() + ", " +
                "createdAt = " + getCreatedAt() + ", " +
                "expiredAt = " + getExpiredAt() + ", " +
                "user = " + getUser() + ")";
    }
}
