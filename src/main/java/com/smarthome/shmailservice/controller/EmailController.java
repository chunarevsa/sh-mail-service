package com.smarthome.shmailservice.controller;

import com.smarthome.shmailservice.dto.EmailRequest;
import com.smarthome.shmailservice.service.EmailService;
import com.smarthome.shmailservice.dto.EmailRequest;
import com.smarthome.shmailservice.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.MessagingException;

@RestController
@RequestMapping("/v1/sent")
public class EmailController {

    private final EmailService emailService;

    @Autowired
    public EmailController(EmailService emailService) {
        this.emailService = emailService;
    }

    //POST /sent (auth-service)
    @PostMapping
    public ResponseEntity sentEmail(EmailRequest req) throws MessagingException {
        try {
            emailService.sentEmail(req);
            return ResponseEntity.ok().build();
        } catch (MessagingException e) {
            return ResponseEntity.internalServerError().build(); // TODO: add Exception
        }
    }
}
