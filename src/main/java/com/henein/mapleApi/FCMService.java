package com.henein.mapleApi;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import com.henein.mapleApi.dto.FCMRequestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
@Slf4j
public class FCMService {
    private final FirebaseMessaging firebaseMessaging;

    public String sendMessageByToken(String token) {
        Notification notification = Notification.builder()
                .setTitle("안녕하세요?")
                .setBody("금주씨 핸드폰인가요~?")
                .build();

        Message message = Message.builder()
                .setToken(token)
                .setNotification(notification)
                .build();

        try {
            firebaseMessaging.send(message);
            return "success send";
        } catch (FirebaseMessagingException e) {
            log.info("FCMService error in 32line");
            return "error";
        }
    }
}
