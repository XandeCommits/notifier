package com.modak.notifier.application;

import com.modak.notifier.adapters.out.database.EmailEntity;
import com.modak.notifier.domain.EmailService;
import com.modak.notifier.domain.models.Email;
import com.modak.notifier.ports.database.EmailRepository;
import com.modak.notifier.ports.smtp.EmailGatewayPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Component
public class EmailSender {

    @Autowired
    EmailGatewayPort gatewayPort;

    @Autowired
    EmailRepository emailRepository;

    @Transactional
    public boolean sendEmail(UUID userId, Email email){

        String emailAddress = getEmailAddress(userId);
        Long emailsSentCount = getNumberOfSentEmails(emailAddress, email);

        EmailEntity entity = new EmailEntity(
                emailAddress,
                email.getContent(),
                email.getType().toString(),
                email.getSentAt()
        );

        return !EmailService.isSpam(email, emailsSentCount) &&
                emailRepository.save(entity).getId() != null &&
                gatewayPort.send(emailAddress, email.getContent());
    }

    private Long getNumberOfSentEmails(String emailAddress, Email email){
        return emailRepository.countByRecipientAndSentAtBetweenAndType(
                emailAddress,
                email.getMaxWindow(),
                email.getSentAt(),
                email.getType().toString());
    }

    private String getEmailAddress(UUID userId){
        return userId.toString() + "@email.com";
    }
}
