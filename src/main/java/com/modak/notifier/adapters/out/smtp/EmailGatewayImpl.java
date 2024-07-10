package com.modak.notifier.adapters.out.smtp;

import com.modak.notifier.ports.smtp.EmailGatewayPort;
import org.springframework.stereotype.Service;

@Service
public class EmailGatewayImpl implements EmailGatewayPort {

    @Override
    public boolean send(String emailAddress, String content) {

        System.out.println("Email sent to:" + emailAddress + "\nContent: " + content);

        return true;
    }
}
