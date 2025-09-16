package com.portfolio.lmsbackend.controller;

import com.portfolio.lmsbackend.dto.Views;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.Set;
import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ControllerViewHelper {
    public static MappingJacksonValue wrapResponseWithView(Object response, Authentication authentication) {
        MappingJacksonValue wrapper = new MappingJacksonValue(response);
        wrapper.setSerializationView(resolveView(authentication));
        return wrapper;
    }

    private static Class<?> resolveView(Authentication authentication) {
        Set<String> roles = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toSet());

        if (roles.contains("ROLE_ADMINISTRATOR")) {
            return Views.Detailed.class;
        }
        if (roles.contains("ROLE_INSTRUCTOR")) {
            return Views.General.class;
        }
        return Views.Basic.class;
    }
}
