package com.smarthome.shmailservice.service;

import com.smarthome.shmailservice.dto.EmailRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
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

    //  TODO:  @Async
    public void sentEmail(EmailRequest req) throws MessagingException {
        Mail mailMessage = new Mail();
        mailMessage.setFrom(req.getFrom());
        mailMessage.setTo(req.getTo());
        mailMessage.setSubject(req.getSubject());
        mailMessage.setMessage(req.getText());
        send(mailMessage);
    }

    private void send(Mail mailMessage) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();

        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(
                message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, StandardCharsets.UTF_8.name());
        mimeMessageHelper.setTo(mailMessage.getTo());
        mimeMessageHelper.setText(mailMessage.getMessage(), true);
        mimeMessageHelper.setSubject(mailMessage.getSubject());
        mimeMessageHelper.setFrom(mailMessage.getFrom());
        mailSender.send(message);
    }
}
