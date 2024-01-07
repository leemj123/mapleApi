package com.henein.mapleApi.controller;

import com.henein.mapleApi.service.CubeService;
import com.henein.mapleApi.dto.UserMapleApi;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import java.util.Set;

@RestController
@RequiredArgsConstructor
@Slf4j
public class CubeController {

    private final CubeService cubeService;

    @Value("${apikey}")
    private String apiKey;
    @PostMapping("/cube")
    public Mono<Set<String>> getUserInfo (@RequestBody UserMapleApi userMapleApi, @RequestParam String key){
        if (!key.matches(apiKey)) {
            throw new RuntimeException();
        }
        return cubeService.getUserNameOnCube(userMapleApi);
    }

}
