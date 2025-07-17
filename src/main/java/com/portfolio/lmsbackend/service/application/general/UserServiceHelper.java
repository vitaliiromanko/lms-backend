package com.portfolio.lmsbackend.service.application.general;

import com.portfolio.lmsbackend.exception.user.UserNotFoundException;
import com.portfolio.lmsbackend.model.token.VerificationToken;
import com.portfolio.lmsbackend.model.user.User;
import com.portfolio.lmsbackend.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class UserServiceHelper {
    private final UserRepository userRepository;
    private final AuthSmtpMailSender authSmtpMailSender;

    public <T extends User> T findByIdAndTypeOrThrow(String id, Class<T> type) {
        return castOptionalUser(userRepository.findById(UUID.fromString(id)), type);
    }

    public <T extends User> T findByEmailAndTypeOrThrow(String email, Class<T> type) {
        return castOptionalUser(userRepository.findByEmail(email), type);
    }

    public void sendVerificationToken(User user, String originUrl) {
        VerificationToken verificationToken = new VerificationToken(user);
        user.getVerificationTokens().add(verificationToken);
        user = userRepository.save(user);
        authSmtpMailSender.sendVerificationMessage(user.getEmail(), verificationToken.getToken(), originUrl);
    }

    private <T extends User> T castOptionalUser(Optional<User> optionalUser, Class<T> type) {
        return optionalUser
                .filter(type::isInstance)
                .map(type::cast)
                .orElseThrow(UserNotFoundException::new);
    }
}
