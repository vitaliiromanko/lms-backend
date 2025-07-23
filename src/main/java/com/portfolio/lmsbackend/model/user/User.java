package com.portfolio.lmsbackend.model.user;

import com.portfolio.lmsbackend.enums.user.UserStatus;
import com.portfolio.lmsbackend.enums.user.UserType;
import com.portfolio.lmsbackend.model.media.image.UserPhoto;
import com.portfolio.lmsbackend.model.token.RefreshToken;
import com.portfolio.lmsbackend.model.token.ResetPasswordToken;
import com.portfolio.lmsbackend.model.token.VerificationToken;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.portfolio.lmsbackend.enums.user.UserStatus.ACTIVE;
import static com.portfolio.lmsbackend.enums.user.UserStatus.BLOCKED;
import static jakarta.persistence.CascadeType.ALL;
import static jakarta.persistence.EnumType.STRING;

@Entity
@Table(name = "user")
@Inheritance(strategy = InheritanceType.JOINED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
public abstract class User extends BaseUserEntity implements UserDetails {
    @Setter(AccessLevel.NONE)
    @Enumerated(value = STRING)
    @Column(name = "type", nullable = false)
    private UserType type;

    @Column(name = "first_name", nullable = false, length = 100)
    private String firstName;

    @Column(name = "last_name", nullable = false, length = 100)
    private String lastName;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "photo_id", referencedColumnName = "id")
    private UserPhoto photo;

    @Column(name = "email_verified", nullable = false)
    private boolean emailVerified;

    @Enumerated(value = STRING)
    @Column(name = "status", nullable = false)
    private UserStatus status = ACTIVE;

    @OneToMany(targetEntity = VerificationToken.class,
            mappedBy = "user", cascade = ALL, orphanRemoval = true)
    private Set<VerificationToken> verificationTokens = new HashSet<>();

    @OneToMany(targetEntity = ResetPasswordToken.class,
            mappedBy = "user", cascade = ALL, orphanRemoval = true)
    private Set<ResetPasswordToken> resetPasswordTokens = new HashSet<>();

    @OneToMany(targetEntity = RefreshToken.class,
            mappedBy = "user", cascade = ALL, orphanRemoval = true)
    private Set<RefreshToken> refreshTokens = new HashSet<>();

    protected User(UserType type, String firstName, String lastName, String email,
                   String password, boolean emailVerified) {
        this.type = type;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.emailVerified = emailVerified;
    }

    @Override
    public final Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(getRoleString()));
    }

    protected abstract String getRoleString();

    @Override
    public final String getUsername() {
        return email;
    }

    @Override
    public final boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired();
    }

    @Override
    public final boolean isAccountNonLocked() {
        return status != BLOCKED;
    }

    @Override
    public final boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }

    @Override
    public final boolean isEnabled() {
        return status == ACTIVE && emailVerified;
    }
}
