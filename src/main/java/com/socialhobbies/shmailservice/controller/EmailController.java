package com.socialhobbies.shmailservice.controller;

import com.socialhobbies.shmailservice.dto.Email;
import com.socialhobbies.shmailservice.service.EmailService;
import com.socialhobbies.shmailservice.util.HeaderUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.MessagingException;

@RestController
@RequestMapping("/api/v1/email")
public class EmailController {

    private final EmailService emailService;

    @Value("${spring.application.name}")
    private String applicationName;
    private static final String ENTITY_NAME = "email";


    @Autowired
    public EmailController(EmailService emailService) {
        this.emailService = emailService;
    }

    @PostMapping
    public ResponseEntity sendEmail(@RequestBody Email req) throws MessagingException {
        emailService.sendEmail(req);
        return ResponseEntity.noContent().headers(
                HeaderUtil.createEntitySendingAlert(applicationName, false, ENTITY_NAME, req.getTo())).build();

    }
}
