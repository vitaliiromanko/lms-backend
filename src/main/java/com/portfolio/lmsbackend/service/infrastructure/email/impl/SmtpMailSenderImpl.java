package com.portfolio.lmsbackend.service.infrastructure.email.impl;

import com.portfolio.lmsbackend.exception.email.EmailNotSentException;
import com.portfolio.lmsbackend.service.infrastructure.email.SmtpMailSender;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;

@Service
@RequiredArgsConstructor
public class SmtpMailSenderImpl implements SmtpMailSender {
    private final JavaMailSender javaMailSender;

    @Value("${application.mail.sender.username}")
    private String emailSender;
    @Value("${application.mail.sender.enabled}")
    private boolean isSenderEnabled;

    public void sendMessage(String emailTo, String subject, String text) {
        if (isSenderEnabled) {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message);
            try {
                helper.setFrom(new InternetAddress(emailSender, "IMS"));
                helper.setTo(emailTo);
                helper.setSubject(subject);
                helper.setText(text);
                javaMailSender.send(message);
            } catch (MessagingException | UnsupportedEncodingException e) {
                throw new EmailNotSentException();
            }
        }
    }
}
