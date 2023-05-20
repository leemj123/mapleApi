package com.henein.mapleApi;

import com.henein.mapleApi.dto.CubeHistoryResponseDto;
import com.henein.mapleApi.dto.UserMapleApi;
import com.henein.mapleApi.dto.UserNameResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

import javax.transaction.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class CubeService {
    private final WebClient webClient;
    @Transactional
    public Flux<UserNameResponseDto> getUserNameOnCube(UserMapleApi userMapleApi, String date){
        String url = "cube-use-results?count=1000&date=" + date;
        return this.webClient.get()
                .uri(url)
                .header(HttpHeaders.AUTHORIZATION, userMapleApi.getUserApi())
                .retrieve()
                .bodyToFlux(CubeHistoryResponseDto.class)
                .flatMapIterable(CubeHistoryResponseDto::getCube_histories)
                .distinct(UserNameResponseDto::getCharacter_name);
    }
}
