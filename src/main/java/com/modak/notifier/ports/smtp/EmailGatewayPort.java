package com.modak.notifier.ports.smtp;

public interface EmailGatewayPort {
    boolean send(String emailAddress, String content);
}
