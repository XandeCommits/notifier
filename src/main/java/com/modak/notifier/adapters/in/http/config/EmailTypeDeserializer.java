package com.modak.notifier.adapters.in.http.config;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.modak.notifier.domain.models.EmailTypeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class EmailTypeDeserializer extends StdDeserializer<EmailTypeEnum> {

    @Autowired
    private StringToEmailTypeConverter stringToEmailTypeConverter;

    public EmailTypeDeserializer() {
        this(null);
    }

    public EmailTypeDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public EmailTypeEnum deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        JsonNode node = jsonParser.getCodec().readTree(jsonParser);
        String value = node.asText().toUpperCase();
        return stringToEmailTypeConverter.convert(value);
    }
}
