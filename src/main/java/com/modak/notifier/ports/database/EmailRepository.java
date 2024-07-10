package com.modak.notifier.ports.database;

import com.modak.notifier.adapters.out.database.EmailEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.Instant;
import java.util.UUID;

public interface EmailRepository extends JpaRepository<EmailEntity, UUID> {

    long countByRecipientAndSentAtBetweenAndType(
            String recipient,
            Instant startInstant,
            Instant endInstant,
            String type
    );
}
