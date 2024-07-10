package com.modak.notifier.adapters.out.database;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
import lombok.Data;

import java.util.UUID;
import java.time.Instant;

@Entity
@Table(name = "EMAILS")
@Data
public class EmailEntity {

    @Id
    @GeneratedValue(generator = "UUID")
    private UUID id;

    @Column(name = "RECIPIENT")
    private String recipient;

    @Column(name = "CONTENT", length = 2000)
    private String content;

    @Column(name = "TYPE")
    private String type;

    @Column(name = "SENT_AT")
    private Instant sentAt;

    public EmailEntity(String recipient, String content, String type, Instant sentAt){
        this.recipient = recipient;
        this.content = content;
        this.type = type;
        this.sentAt = sentAt;
    }

    public EmailEntity(){}
}
