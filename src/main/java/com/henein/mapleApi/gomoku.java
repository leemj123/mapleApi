package com.henein.mapleApi;

import com.henein.mapleApi.dto.GomokuResultDto;
import com.henein.mapleApi.dto.LoginRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
public class gomoku {
    @GetMapping("/normal-get")
    public String normalGet() {
        return "get success";
    }
    @GetMapping("/header-test-get")
    public String headerGet(HttpServletRequest request) {
        String AT = request.getHeader("Authorization");
        return "헤더에 넣어 보내준 값은: "+AT.toString();
    }
    @PostMapping("/dto-post")
    public String postGameResult(@RequestBody GomokuResultDto gomokuResultDto) {

        return "희주니가 보내준 결과값: "+gomokuResultDto.getGameResult();
    }
    @PostMapping("/login")
    public String loginRequest(@RequestBody LoginRequest loginRequest, HttpServletResponse response) {
        if (loginRequest.getId().equals("parkhjId") && loginRequest.getPw().equals("parkhjPw")) {
            response.addHeader("Authorization","Bearer ParkHJ");
            return "are you request this?: " + loginRequest.getId() + ", " + loginRequest.getPw()+"\n"
                    + "And I add DummyToken In your Header";
        }
        return "401 UNAuthorization... that's wrong Id,Pw";

    }
}
