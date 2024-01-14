package com.henein.mapleApi.controller;

import com.henein.mapleApi.dto.CharRefreshRequestDto;
import com.henein.mapleApi.dto.CharacterBasic;
import com.henein.mapleApi.service.CharacterService;
import com.henein.mapleApi.service.CubeService;
import com.henein.mapleApi.dto.UserMapleApi;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import java.util.List;
import java.util.Set;

@RestController
@RequiredArgsConstructor
@Slf4j
public class NexonApiController {

    private final CubeService cubeService;
    private final CharacterService characterService;

    @Value("${nexon.apikey}")
    private String apiKey;
    @PostMapping("/cube")
    public Mono<Set<String>> getUserInfo (@RequestBody UserMapleApi userMapleApi, @RequestParam String key){
        if (!key.matches(apiKey)) {
            throw new RuntimeException();
        }
        return cubeService.getUserNameOnCube(userMapleApi);
    }

    //단일 요청
    @GetMapping("/character/single")
    public Mono<CharacterBasic> getOneCharInfoWithOcid (@RequestParam(required = false) String name, @RequestParam(required = false) String ocid, @RequestParam String key) {
        if (!key.matches(apiKey))
            throw new RuntimeException();

        return ocid == null ?  characterService.getOcid(name) :  characterService.getOneCharInfoWithOcid(ocid);
    }

    //복수 요청
    @PostMapping("/character/multiple")
    public Mono<List<CharacterBasic>> getCharInfoListWithOcid (@RequestParam String key, @RequestBody CharRefreshRequestDto charRefreshRequestDto) {
        if (!key.matches(apiKey))
            throw new RuntimeException();

        return characterService.getCharInfoList(charRefreshRequestDto);
    }


}
