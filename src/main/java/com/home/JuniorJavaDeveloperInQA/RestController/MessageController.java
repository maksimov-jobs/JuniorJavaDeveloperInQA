package com.home.JuniorJavaDeveloperInQA.RestController;

import com.home.JuniorJavaDeveloperInQA.dto.UserMessage;
import com.home.JuniorJavaDeveloperInQA.service.UserMessageService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class MessageController {
    private final UserMessageService userMessageService;

    public MessageController(UserMessageService userMessageService) {
        this.userMessageService = userMessageService;
    }

    @PostMapping("/message")
    @ResponseStatus(HttpStatus.OK)
    public List<String> readUserMessage(@RequestBody UserMessage userMessage, @RequestHeader("Authorization") String token){
        return userMessageService.processMessage(userMessage,token);
    }
}
