package com.modak.notifier.domain.models;

import java.time.Duration;
import java.time.Instant;

public class MarketingEmail implements Email {

    private final Integer limit;
    private final String content;
    private final EmailTypeEnum type;
    private final Instant sentAt;

    public MarketingEmail(String content, Instant sentAt){
        this.limit = 3;
        this.content = content;
        this.type = EmailTypeEnum.MARKETING;
        this.sentAt = sentAt != null ? sentAt : Instant.now();
    }

    @Override
    public Instant getMaxWindow() {
        return this.sentAt.minus(Duration.ofHours(1));
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
    public EmailTypeEnum getType() {
        return this.type;
    }

    @Override
    public Instant getSentAt(){
        return this.sentAt;
    }
}
