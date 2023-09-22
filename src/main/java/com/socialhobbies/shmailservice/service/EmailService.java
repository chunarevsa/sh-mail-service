package com.socialhobbies.shmailservice.service;

import com.socialhobbies.shmailservice.dto.Email;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.nio.charset.StandardCharsets;

@Service
public class EmailService {

    private final JavaMailSender mailSender;

    @Autowired
    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Async
    public void sendEmail(Email req) throws MessagingException {
        Email mailMessage = new Email();
        mailMessage.setFrom(req.getFrom());
        mailMessage.setTo(req.getTo());
        mailMessage.setSubject(req.getSubject());
        mailMessage.setText(req.getText());
        send(mailMessage);
    }

    private void send(Email email) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();

        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(
                message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, StandardCharsets.UTF_8.name());
        mimeMessageHelper.setTo(email.getTo());
        mimeMessageHelper.setText(email.getText(), true);
        mimeMessageHelper.setSubject(email.getSubject());
        mimeMessageHelper.setFrom(email.getFrom());
        mailSender.send(message);
    }
}
