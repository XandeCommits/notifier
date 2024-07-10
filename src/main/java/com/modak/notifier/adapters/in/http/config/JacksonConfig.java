package com.modak.notifier.adapters.in.http.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.modak.notifier.domain.models.EmailTypeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class JacksonConfig {

    private final EmailTypeDeserializer emailTypeDeserializer;

    @Autowired
    public JacksonConfig(EmailTypeDeserializer emailTypeDeserializer) {
        this.emailTypeDeserializer = emailTypeDeserializer;
    }

    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        SimpleModule module = new SimpleModule();
        module.addDeserializer(EmailTypeEnum.class, emailTypeDeserializer);
        objectMapper.registerModule(module);
        return objectMapper;
    }
}