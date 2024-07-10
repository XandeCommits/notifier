package com.modak.notifier.adapters.in.http;

import com.modak.notifier.application.EmailSender;
import com.modak.notifier.domain.EmailFactory;
import com.modak.notifier.domain.models.Email;
import com.modak.notifier.domain.models.EmailType;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.UUID;

@RestController
public class EmailController {

    private final EmailSender sender;
    private final EmailFactory factory;

    @Autowired
    public EmailController(EmailSender sender, EmailFactory factory){
        this.sender = sender;
        this.factory = factory;
    }

    @PostMapping("/{user-id}/send-email")
    public ResponseEntity<String> sendEmail(
            @PathVariable("user-id") UUID userId,
            @Valid @RequestBody SendEmailRequest request,
            @RequestHeader(value = "mocked-time", required = false) Instant mockedTime) {

        Email email = factory.newEmail(
                request.getEmailType(),
                request.getEmailContent(),
                mockedTime
        );

        if (sender.sendEmail(userId, email)) {
            return new ResponseEntity<>("Ok", HttpStatus.OK);
        }

        return new ResponseEntity<>("Too many request", HttpStatus.TOO_MANY_REQUESTS);
    }
}
