package com.henein.mapleApi.error;

import com.henein.mapleApi.FCMService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class FcmController {
    private final FCMService fcmService;
    @GetMapping("/fcmtest")
    public String dkdkd(@RequestParam String token) {
        return fcmService.sendMessageByToken(token);
    }

}
