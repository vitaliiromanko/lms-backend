package com.portfolio.lmsbackend.security;

public record JwtPair(
        String accessToken,
        String refreshToken
) {
}
