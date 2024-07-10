package com.modak.notifier.adapters.in.http;

import com.modak.notifier.domain.models.EmailType;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import jakarta.validation.constraints.NotBlank;

@Data
public class SendEmailRequest {

    @NotNull(message = "emailType is required")
    private EmailType emailType;

    @NotBlank(message = "emailContent is required")
    private String emailContent;

}
