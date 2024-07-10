package com.modak.notifier.domain;

import com.modak.notifier.domain.models.*;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.HashMap;

@Component
public class EmailFactory {

    private final Map<EmailTypeEnum, BiFunction<String, Instant, Email>> emailConstructors = new HashMap<>();

    public EmailFactory(){
        this.emailConstructors.put(EmailTypeEnum.STATUS, StatusEmail::new);
        this.emailConstructors.put(EmailTypeEnum.MARKETING, MarketingEmail::new);
        this.emailConstructors.put(EmailTypeEnum.NEWS, NewsEmail::new);
    }

    public Email newEmail(EmailTypeEnum emailType, String emailContent, Instant sentAt){
        return this.emailConstructors.get(emailType).apply(emailContent, sentAt);
    }
}
