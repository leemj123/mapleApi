package com.henein.mapleApi;


import com.henein.mapleApi.dto.CubeHistoryResponseDto;
import com.henein.mapleApi.dto.UserMapleApi;
import com.henein.mapleApi.dto.UserNameResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.*;


import static com.henein.mapleApi.CubeController.tryCount;

@Service
@Transactional
@RequiredArgsConstructor
public class CubeService {
    private final WebClient webClient;


    @Transactional
    public List<UserNameResponseDto> getUserNameOnCube(UserMapleApi userMapleApi, LocalDate date) {

        String defaultUrl = "cube-use-results?count=1000&";
        String url = defaultUrl +"date="+date;
        List<UserNameResponseDto> userNameList = new ArrayList<>();
        boolean cursor;
        do {

            Mono<Map<String, Object>> target = this.webClient.get()
                    .uri(url)
                    .header(HttpHeaders.AUTHORIZATION, userMapleApi.getUserApi())
                    .retrieve()
                    .bodyToMono(CubeHistoryResponseDto.class)
                    .flatMap(response -> {
                        String newNextCursor = response.getNext_cursor();
                        Flux<UserNameResponseDto> userNames = Flux.fromIterable(response.getCube_histories())
                                .map(cubeHistory -> new UserNameResponseDto(cubeHistory.getCharacter_name()))
                                .map(UserNameResponseDto::getCharacter_name)
                                .distinct()
                                .map(UserNameResponseDto::new);
                        return userNames.collectList().map(userNamesList -> {
                            Map<String, Object> resultMap = new HashMap<>();
                            resultMap.put("t1", userNamesList);
                            resultMap.put("t2", newNextCursor);
                            return resultMap;
                        });
                    }).subscribeOn(Schedulers.elastic()); //비동기 작업을 별도의 스레드 풀에서 실행하도록 설정합니다.
            tryCount++;
            Map<String, Object> resultMap = target.block();
            List<UserNameResponseDto> userNamesList = (List<UserNameResponseDto>) resultMap.get("t1");
            userNameList.addAll(userNamesList); // 결과에 저장함
            if (!target.block().get("t2").equals("")) {
                url = defaultUrl+"cursor="+target.block().get("t2");
                cursor = true;
            } else {
                cursor = false;
            }
        } while(cursor && tryCount <= 50);


        return userNameList;
    }
}
