package com.home.JuniorJavaDeveloperInQA.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.home.JuniorJavaDeveloperInQA.db.entity.MyUser;
import com.home.JuniorJavaDeveloperInQA.db.entity.UserMessageEntity;
import com.home.JuniorJavaDeveloperInQA.db.repository.MyUserRepository;
import com.home.JuniorJavaDeveloperInQA.db.repository.UserMessageRepository;
import com.home.JuniorJavaDeveloperInQA.dto.UserMessage;
import com.home.JuniorJavaDeveloperInQA.security.service.JWTUtil;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserMessageService {
    private final UserMessageRepository userMessageRepository;
    private final MyUserRepository myUserRepository;
    private final JWTUtil jwtTokenUtil;
    private final ObjectMapper objectMapper;


    public UserMessageService(UserMessageRepository userMessageRepository, MyUserRepository myUserRepository, JWTUtil jwtTokenUtil, ObjectMapper objectMapper) {
        this.userMessageRepository = userMessageRepository;
        this.myUserRepository = myUserRepository;
        this.jwtTokenUtil = jwtTokenUtil;
        this.objectMapper = objectMapper;
    }

    //проверка идентичности name в сообщении и имени авторизованного пользователя
    private boolean checkAuthor(UserMessage userMessage, String stringWithToken){
        MyUser myUser = myUserRepository.findByLogin(userMessage.getName());
        return myUser != null && jwtTokenUtil.extractUsername(stringWithToken.substring(7)).equals(myUser.getLogin());
    }

    //проверка сообщения пользователя на history (true)
    private boolean checkMessage(String message){
        return message != null && message.length() > 7 && message.substring(0, 7).equals("history") && isDigit(message.substring(8));
    }

    //проверка строки на число
    private boolean isDigit(String string){
        try {
            return Integer.parseInt(string) > 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    //Выборка сообщений пользователя из БД
    private List<UserMessageEntity> getUserMessages(String message, String name){
        int limit = Integer.parseInt(message.substring(8));
        Pageable pageable = PageRequest.of(0, limit, Sort.by(Sort.Direction.DESC, "Id"));
        return userMessageRepository.findAllByUserId(myUserRepository.findByLogin(name).getId(), pageable);
    }

    //обработка входящего сообщения
    public List<String> processMessage(UserMessage userMessage, String stringWithToken) {
        List<String> arrayList = new ArrayList<>();
        if (checkAuthor(userMessage,stringWithToken)) {
            if (checkMessage(userMessage.getMessage())) {
                List<UserMessageEntity> userMessageList =getUserMessages(userMessage.getMessage(), userMessage.getName());
                    for (UserMessageEntity userMessageEntity: userMessageList) {
                        arrayList.add(userMessageEntity.getMessage());
                    }
            } else {
                if (userMessage.getMessage() != null && !userMessage.getMessage().equals("")){
                    userMessage.setUserId(myUserRepository.findByLogin(userMessage.getName()).getId());
                    UserMessageEntity userMessageEntity = objectMapper.convertValue(userMessage, UserMessageEntity.class);
                    userMessageRepository.save(userMessageEntity);
                    arrayList.add("Сообщение сохранено");

                } else {
                    arrayList.add("Сообщение не сохранено т.к. null или пустое");
                }
            }
        }
        else {
            arrayList.add("name в сообщении не соответствует авторизованному пользователю");
        }
        return arrayList;
    }
}

