package com.astafievvadim.mm_backend.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private final JavaMailSender mailSender;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendVerificationCode(String toEmail, String code) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, "utf-8");

        String subject = "Your Verification Code";
        String content = "<p>Hello,</p>"
                + "<p>Your verification code is <b>" + code + "</b></p>"
                + "<p>Please enter this code to complete your login.</p>";

        helper.setTo(toEmail);
        helper.setSubject(subject);
        helper.setText(content, true); // true = HTML

        mailSender.send(message);
    }
}
