package com.henein.mapleApi;

import com.henein.mapleApi.dto.UserMapleApi;
import com.henein.mapleApi.dto.UserNameResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequiredArgsConstructor
@Slf4j
public class CubeController {

    private final CubeService cubeService;
//    @GetMapping("/cube") //비동기 작업을 나타내는 mono다
//    public Mono<List<UserNameResponseDto>> getUserInfo (@RequestBody UserMapleApi userMapleApi){
//        LocalDate targetDate = LocalDate.now();
//        LocalDate threeMonthsAgo = targetDate.minus(3, ChronoUnit.MONTHS);
//
//        List<Flux<UserNameResponseDto>> resultDtoList =new ArrayList<>();
//        while (targetDate.isAfter(threeMonthsAgo) || targetDate.isEqual(threeMonthsAgo)) {
//            Flux<UserNameResponseDto> dtoFlux= cubeService.getUserNameOnCube(userMapleApi,targetDate);
//            resultDtoList.add(dtoFlux);
//            targetDate = targetDate.minus(1, ChronoUnit.DAYS);
//
//        }
//        Flux<UserNameResponseDto> mergedFluxResult = Flux.merge(resultDtoList);
//        return mergedFluxResult.collectList();
//    }
    @PostMapping("/cube") //비동기 작업을 나타내는 mono다
    public Flux<UserNameResponseDto> getUserInfo (@RequestBody UserMapleApi userMapleApi){
        log.info(userMapleApi.getUserApi());
        String targetDate = "2023-05-07";
        return cubeService.getUserNameOnCube(userMapleApi,targetDate);
    }
}
