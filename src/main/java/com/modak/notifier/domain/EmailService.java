package com.modak.notifier.domain;

import com.modak.notifier.domain.models.Email;

public class EmailService {

    public static boolean isSpam(Email email, Long emailsSentCount){
        return emailsSentCount >= email.getLimit();
    }
}
