package com.modak.notifier.domain.models;

import java.time.Instant;

public interface Email {

    Instant getMaxWindow();
    Integer getLimit();
    String getContent();
    EmailTypeEnum getType();
    Instant getSentAt();
}
