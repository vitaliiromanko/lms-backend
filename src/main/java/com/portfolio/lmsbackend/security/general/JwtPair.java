package com.portfolio.lmsbackend.security.general;

public record JwtPair(
        String accessToken,
        String refreshToken
) {
}
