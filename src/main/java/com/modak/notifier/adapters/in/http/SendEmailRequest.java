package com.modak.notifier.adapters.in.http;

import com.modak.notifier.domain.models.EmailTypeEnum;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import jakarta.validation.constraints.NotBlank;

@Data
public class SendEmailRequest {

    @NotNull(message = "emailType is required")
    private EmailTypeEnum emailType;

    @NotBlank(message = "emailContent is required")
    private String emailContent;

}
