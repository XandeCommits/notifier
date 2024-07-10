package com.modak.notifier.adapters.in.http.config;

import com.modak.notifier.domain.models.EmailTypeEnum;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StringToEmailTypeConverter implements Converter<String, EmailTypeEnum> {

    @Override
    public EmailTypeEnum convert(String source) {
        try {
            return EmailTypeEnum.valueOf(source.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid value for emailType: " + source);
        }
    }
}
