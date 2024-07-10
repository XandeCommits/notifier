package com.modak.notifier.adapters.in.http.config;

import com.modak.notifier.domain.models.EmailType;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StringToEmailTypeConverter implements Converter<String, EmailType> {

    @Override
    public EmailType convert(String source) {
        try {
            return EmailType.valueOf(source.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid value for emailType: " + source);
        }
    }
}
