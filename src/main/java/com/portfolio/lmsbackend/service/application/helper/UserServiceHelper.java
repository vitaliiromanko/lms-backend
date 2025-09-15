package com.portfolio.lmsbackend.service.application.helper;

import com.portfolio.lmsbackend.exception.user.UserNotFoundException;
import com.portfolio.lmsbackend.model.token.VerificationToken;
import com.portfolio.lmsbackend.model.user.Staff;
import com.portfolio.lmsbackend.model.user.Student;
import com.portfolio.lmsbackend.model.user.User;
import com.portfolio.lmsbackend.repository.user.UserRepository;
import com.portfolio.lmsbackend.service.application.general.AuthSmtpMailSender;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;

@Component
@RequiredArgsConstructor
public class UserServiceHelper {
    private final UserRepository userRepository;
    private final AuthSmtpMailSender authSmtpMailSender;

    public User findByIdOrThrow(String id) {
        return getOrThrow(userRepository.findById(UUID.fromString(id)));
    }

    public User findByEmailOrThrow(String email) {
        return getOrThrow(userRepository.findByEmail(email));
    }

    public <T extends User> T findByIdAndTypeOrThrow(String id, Class<T> type) {
        return castUser(findByIdOrThrow(id), type);
    }

    public void sendVerificationToken(User user, String originUrl) {
        VerificationToken verificationToken = new VerificationToken(user);
        user.getVerificationTokens().add(verificationToken);
        user = userRepository.save(user);
        authSmtpMailSender.sendVerificationMessage(user.getEmail(), verificationToken.getToken(), originUrl);
    }

    public <R> R mapUserTo(User user, Function<Staff, R> staffMapper, Function<Student, R> studentMapper) {
        return switch (user) {
            case Staff staff -> staffMapper.apply(staff);
            case Student student -> studentMapper.apply(student);
            default -> throw unexpectedUserType(user);
        };
    }

    public static IllegalStateException unexpectedUserType(User user) {
        return new IllegalStateException("Unexpected user type: " + user.getClass().getName());
    }

    private User getOrThrow(Optional<User> optionalUser) {
        return optionalUser.orElseThrow(UserNotFoundException::new);
    }

    private <T extends User> T castUser(User user, Class<T> type) {
        if (type.isInstance(user)) {
            return type.cast(user);
        }
        throw new UserNotFoundException();
    }
}
