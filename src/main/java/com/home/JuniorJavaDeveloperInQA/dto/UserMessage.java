package com.home.JuniorJavaDeveloperInQA.dto;

import lombok.Data;

@Data
public class UserMessage {
    private long id;
    private String name;
    private String message;
    private long userId;
}
