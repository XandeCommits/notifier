package com.modak.notifier.domain.models;

import java.time.Duration;
import java.time.Instant;

public class StatusEmail implements Email {

    private final Integer limit;
    private final String content;
    private final EmailType type;
    private final Instant sentAt;

    public StatusEmail(String content, Instant sentAt){
        this.limit = 2;
        this.content = content;
        this.type = EmailType.STATUS;
        this.sentAt = sentAt != null ? sentAt : Instant.now();
    }

    @Override
    public Instant getMaxWindow() {
        return this.sentAt.minus(Duration.ofMinutes(1));
    }

    @Override
    public Integer getLimit() {
        return this.limit;
    }

    @Override
    public String getContent() {
        return this.content;
    }

    @Override
    public EmailType getType() {
        return this.type;
    }

    @Override
    public Instant getSentAt(){
        return this.sentAt;
    }
}
