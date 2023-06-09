package com.henein.mapleApi;

import com.henein.mapleApi.dto.UserMapleApi;
import com.henein.mapleApi.dto.UserNameResponseDto;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
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
    @PostMapping("/cube") //비동기 작업을 나타내는 mono다
    public List<String> getUserInfo (@RequestBody UserMapleApi userMapleApi){
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
    public String a (){
        return "접속 성공";
    }

}
