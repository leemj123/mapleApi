package com.henein.mapleApi.service;


import com.henein.mapleApi.dto.CubeHistoryResponseDto;
import com.henein.mapleApi.dto.UserMapleApi;
import com.henein.mapleApi.dto.UserNameResponseDto;
import com.henein.mapleApi.webFluxError.CustomException;
import com.henein.mapleApi.webFluxError.ErrorResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
@Slf4j
//완벽한 비동기를 위한 재귀함수
/*
* Spring WebFlux와 같은 리액티브 프로그래밍 환경에서는, 각각의 재귀 호출이 새로운 이벤트 루프 또는 스케줄러 작업으로 예약되기 때문에,
*  전통적인 재귀 호출에서 발생하는 스택 오버플로우 문제가 일반적으로 발생하지 않습니다. 이는 "트램폴린" 방식으로 알려져 있으며,
*  함수 호출이 직접적인 스택 호출이 아닌 스케줄된 작업으로 변환되어 스택 오버플로우 위험을 피할 수 있게 합니다.
*
* 그럼에도 밑의 코드에서 try 횟수가 최대 50임으로 재귀를 사용했음, 스케줄된 작업이라도 재귀는 메모리 사용량이 크기 때문
* */

public class CubeService {
    private final WebClient webClient;

    public Mono<Set<String>> getUserNameOnCube(UserMapleApi userMapleApi) {
        LocalDate targetDate = userMapleApi.getRecentDay();
        Set<String> userNamesList = new HashSet<>();
        String url = "v1/histroy/cube?count=100&date=" + targetDate;
        AtomicInteger tryCount = new AtomicInteger(0); // 이 부분 클래스 끝에 설명있음
        return getUserNameOnDate(userMapleApi, targetDate, userNamesList, url, tryCount);
    }
    private Mono<Set<String>> getUserNameOnDate(UserMapleApi userMapleApi, LocalDate targetDate, Set<String> userNamesList, String url, AtomicInteger tryCount) {
        if (targetDate.isBefore(userMapleApi.getPastDay()) || 50 < tryCount.get()) {
            return Mono.just(userNamesList);
        }
        log.info("getUserNameOnDate 호출함!");

        return this.webClient.get()
                .uri(url)
                .header("x-nxopen-api-key", userMapleApi.getUserApi())
                .retrieve()
                .onStatus(HttpStatus::is4xxClientError, response ->
                        response.bodyToMono(ErrorResponse.class).flatMap(errorResponse ->
                                Mono.error(new CustomException("Error: " +errorResponse.getError().getMessage())))
                )
                .bodyToMono(CubeHistoryResponseDto.class)
                .flatMap(response -> {
                    String nextCursor = response.getNext_cursor();
                    Set<String> characterNames = response.getCube_history().stream()
                            .map(UserNameResponseDto::getCharacter_name)
                            .collect(Collectors.toSet());
                    userNamesList.addAll(characterNames);

                    if (nextCursor.isEmpty() || nextCursor.isBlank() ) {
                        //다음 커서가 없으면 날짜 +1일 해서 넘어감
                        log.info("다음 커서가 없엉"+targetDate.toString());
                        String nextUrl = "v1/histroy/cube?count=100&date=" + targetDate.minus(1, ChronoUnit.DAYS);
                        LocalDate nextDate = targetDate.minus(1,ChronoUnit.DAYS);
                        tryCount.incrementAndGet();
                        return getUserNameOnDate(userMapleApi, nextDate, userNamesList, nextUrl, tryCount);

                    } else {
                        //커서가 있으면 커서로 이동
                        log.info("커서 있엉!" + targetDate.toString());
                        String nextUrl = "v1/histroy/cube?count=100&cursor=" + nextCursor;
                        tryCount.incrementAndGet();
                        return getUserNameOnDate(userMapleApi, targetDate, userNamesList, nextUrl, tryCount);
                    }
                });
    }

    /**
    * 위 코드에서  int tryCount = 0 ; tryCount += 1; 을 하지 못하는 이유
    *
    * Java의 람다 표현식 내에서는 외부 지역 변수를 변경할 수 없습니다. 이는 람다 표현식이 사용되는 컨텍스트가 다를 수 있기 때문에,
    *  외부 변수의 상태 변경을 허용하지 않는 Java의 설계 원칙에 따른 것입니다.
    *
    * 해결방법!
    * -> 변경 가능한 래퍼(wrapper)를 사용하는 것입니다.
    *  예를 들어, AtomicInteger 같은 스레드 안전한 래퍼 클래스를 사용하여 카운터를 관리할 수 있습니다.
    *  AtomicInteger는 내부적으로 값을 변경할 수 있으므로 람다 표현식 내에서도 사용할 수 있습니다.
    * */


//    public Mono<Set<String>> getUserNameOnCube(UserMapleApi userMapleApi) {
//
//        LocalDate targetDate = userMapleApi.getRecentDay();
//        Set<String> userNamesList = new HashSet<>();
//
//        //탐색할 날짜가 음수가 되지 않을때까지
//        while (!(targetDate.isBefore(userMapleApi.getPastDay()))) {
//            //webClient 설정
//            String url = "v1/histroy/cube?count=100&date="+targetDate;
//            log.info(String.valueOf(LocalTime.now()));
//
//            boolean cursor;
//            do {
//                Mono<Map<String,Object>> nexonApiResponse = this.webClient.get()
//                        .uri(url)
//                        .header(HttpHeaders.AUTHORIZATION, userMapleApi.getUserApi())
//                        .retrieve()
//                        .bodyToMono(CubeHistoryResponseDto.class)
//                        .flatMap(response -> {
//                            String nextCursor = response.getNext_cursor();
//                            Set<String> characterNames = response.getCube_history().stream()
//                                    .map(UserNameResponseDto::getCharacter_name)
//                                    .collect(Collectors.toSet());
//
//                            Map<String, Object> resultResponse = new HashMap<>();
//                            resultResponse.put("t1", characterNames);
//                            resultResponse.put("t2", nextCursor);
//
//                            return Mono.just(resultResponse);
//                        });
//
//                //횟수 증가
//
//                // Mono에서 직접 .get() 메서드를 사용하여 값을 추출할 수 없습니다. 대신, Mono의 결과를 처리하려면 리액티브 스트림의 연산을 사용해야 합니다.
//                nexonApiResponse.subscribe(APIresult -> {
//                    // 결과에 저장함
//                    Set<String> characterNames = (Set<String>) APIresult.get("t1");
//                    userNamesList.addAll(characterNames);
//
//                    if (APIresult.get("t2").equals("")) {
//                        url = "v1/histroy/cube?count=100&cursor="+APIresult.get("t1");
//                        cursor = true;
//                    } else {
//                        cursor = false;
//                    }
//
//                });
//                tryCount++;
//
//            } while(cursor && tryCount <= 50);
//
//            targetDate = targetDate.minus(1, ChronoUnit.DAYS);
//        }
//
//        return Mono.just(userNamesList);
//    }
}
/*
* public class CubeService {
    private final WebClient webClient;




}
* */