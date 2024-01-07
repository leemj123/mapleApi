package com.henein.mapleApi.controller;

import com.henein.mapleApi.dto.TestDto;
import com.henein.mapleApi.service.CubeService;
import com.henein.mapleApi.dto.UserMapleApi;
import com.henein.mapleApi.dto.UserNameResponseDto;
import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@Slf4j
public class CubeController {

    private final CubeService cubeService;
    public static int tryCount = 0;
    @Value("${apikey}")
    private String apiKey;
    @PostMapping("/cube") //비동기 작업을 나타내는 mono다
    public List<String> getUserInfo (@RequestBody UserMapleApi userMapleApi, @RequestParam String key){
        if (!key.matches(apiKey)) {
            throw new RuntimeException();
        }
        log.info("cube 접속");
        LocalDate targetDate = userMapleApi.getRecentDay();
        List<UserNameResponseDto> userNamesList = new ArrayList<>();

        while (!(targetDate.isBefore(userMapleApi.getPastDay()))) {
            log.info(targetDate.toString());
            List<UserNameResponseDto> userName = cubeService.getUserNameOnCube(userMapleApi,targetDate);
            userNamesList.addAll(userName);
            targetDate = targetDate.minus(1, ChronoUnit.DAYS);
        }

        List<String> result = userNamesList.stream()
        .map(UserNameResponseDto::getCharacter_name)
        .distinct()
        .collect(Collectors.toList());

        return result;
    }
    @GetMapping()
    public TestDto a (@RequestParam(required = false)String string, @RequestParam(required = false) int num){
        TestDto testDto = new TestDto();
        log.info("come");
        log.info(string);
        log.info(String.valueOf(num));
        testDto.setHello("박지훈");
        testDto.setNumber(19980515);
        testDto.setText("너가 준 URL 파라미터는: " + string + " 과 " + num +" 인듯 ㅋ.ㅋ");
        return testDto;
    }

}
