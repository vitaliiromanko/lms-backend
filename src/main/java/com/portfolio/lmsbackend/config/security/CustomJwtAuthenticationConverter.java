package com.portfolio.lmsbackend.config.security;

import com.portfolio.lmsbackend.model.user.User;
import com.portfolio.lmsbackend.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

import java.util.UUID;

import static com.portfolio.lmsbackend.utils.StringsHelper.USER_NOT_FOUND_MESSAGE;

@Component
@RequiredArgsConstructor
public class CustomJwtAuthenticationConverter implements Converter<Jwt, AbstractAuthenticationToken> {
    private final UserRepository userRepository;

    @Override
    public AbstractAuthenticationToken convert(Jwt jwt) {
        User user = userRepository.findById(UUID.fromString(jwt.getSubject()))
                .orElseThrow(() -> new UsernameNotFoundException(USER_NOT_FOUND_MESSAGE));
        return new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
    }
}
