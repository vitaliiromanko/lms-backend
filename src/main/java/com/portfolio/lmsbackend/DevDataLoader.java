package com.portfolio.lmsbackend;

import com.portfolio.lmsbackend.model.user.Staff;
import com.portfolio.lmsbackend.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import static com.portfolio.lmsbackend.enums.user.StaffRole.ADMINISTRATOR;

@Component
@Profile("dev")
@RequiredArgsConstructor
@Slf4j
public class DevDataLoader implements CommandLineRunner {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        defaultAdminInit();
    }

    private void defaultAdminInit() {
        if (userRepository.count() == 0) {
            userRepository.save(new Staff(
                    "F_admin",
                    "L_admin",
                    "test-admin@tt.tt",
                    passwordEncoder.encode("testT001"),
                    ADMINISTRATOR
            ));
            log.info("Default administrator has been registered successfully");
        }
    }
}
