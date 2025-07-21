package com.portfolio.lmsbackend.service.infrastructure.email;

public interface SmtpMailSender {
    void sendMessage(String emailTo, String subject, String text);
}
