package com.home.JuniorJavaDeveloperInQA.db.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "user_message_entity")
public class UserMessageEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String message;
    private long userId;
}
