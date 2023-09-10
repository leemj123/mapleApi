package com.henein.mapleApi;

import com.henein.mapleApi.dto.UserMapleApi;
import com.henein.mapleApi.dto.UserNameResponseDto;
import com.henein.mapleApi.dto.ChessDto;
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
        LocalDate targetDate = LocalDate.now().minus(1,ChronoUnit.DAYS);
        LocalDate limitDate = targetDate.minus(49, ChronoUnit.DAYS);
        List<UserNameResponseDto> userNamesList = new ArrayList<>();

        while (targetDate.isAfter(limitDate) || targetDate.isEqual(limitDate) || tryCount <=50) {
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
    public ChessDto a (){
        ChessDto chessDto = new ChessDto();
        log.info("들어왔어용");
        return chessDto;
    }
    @PostMapping()
    public String b(@RequestBody ChessDto chessDto) {
        log.info(chessDto.getId() + "+" + chessDto.getX()+ "+" + chessDto.getY());
        return "재욱아 너가 보낸것의 x,y 값은: " + (chessDto.getX()+ chessDto.getY());
    }

}
