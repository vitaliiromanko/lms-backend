package com.portfolio.lmsbackend.security;

import com.portfolio.lmsbackend.exception.token.InvalidRefreshTokenException;
import com.portfolio.lmsbackend.exception.user.UserBlockedException;
import com.portfolio.lmsbackend.model.token.RefreshToken;
import com.portfolio.lmsbackend.model.user.User;
import com.portfolio.lmsbackend.repository.token.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.stream.Collectors;

import static java.time.temporal.ChronoUnit.MINUTES;

@Component
@RequiredArgsConstructor
public class JwtMethods {
    private final RefreshTokenRepository refreshTokenRepository;
    private final JwtEncoder jwtEncoder;

    private static final String SCOPE_DELIMITER = " ";

    @Value("${application.security.jwt.access-token.lifetime}")
    private Long accessTokenLifetime;
    @Value("${application.security.jwt.refresh-token.lifetime}")
    private Long refreshTokenLifetime;

    public JwtPair genJwtPair(User user) {
        return new JwtPair(
                genAccessToken(user),
                genRefreshToken(user)
        );
    }

    public JwtPair refresh(String refreshToken) {
        RefreshToken refToken = refreshTokenRepository.findByToken(refreshToken).orElseThrow(
                InvalidRefreshTokenException::new);

        if (LocalDateTime.now().isAfter(refToken.getExpiredAt())) {
            refreshTokenRepository.delete(refToken);
            throw new InvalidRefreshTokenException();
        }

        User user = refToken.getUser();
        refreshTokenRepository.delete(refToken);

        if (!user.isAccountNonLocked()) {
            throw new UserBlockedException();
        }

        return genJwtPair(user);
    }

    private String genAccessToken(User user) {
        Instant now = Instant.now();
        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("self")
                .issuedAt(now)
                .expiresAt(now.plus(accessTokenLifetime, MINUTES))
                .subject(user.getId().toString())
                .claim("scope", createScope(user.getAuthorities()))
                .build();
        return jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
    }

    private String genRefreshToken(User user) {
        return refreshTokenRepository.save(new RefreshToken(
                user,
                LocalDateTime.now().plusMinutes(refreshTokenLifetime)
        )).getToken();
    }


    private String createScope(Collection<? extends GrantedAuthority> authorities) {
        return authorities.stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(SCOPE_DELIMITER));
    }
}
