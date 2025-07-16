package com.portfolio.lmsbackend.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.UUID;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class GenerateTokenUtils {
    public static String genToken() {
        return UUID.randomUUID().toString().replace("-", "");
    }
}
