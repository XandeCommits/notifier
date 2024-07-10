package com.modak.notifier.domain.models;

import java.time.Instant;

public interface Email {

    Instant getMaxWindow();
    Integer getLimit();
    String getContent();
    EmailType getType();
    Instant getSentAt();
}
