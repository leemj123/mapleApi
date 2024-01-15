package com.henein.mapleApi.service;

import com.henein.mapleApi.dto.CharRefreshRequestDto;
import com.henein.mapleApi.dto.CharacterBasic;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CharacterService {


    private final WebClient webClient;


    public Mono<CharacterBasic> getOcid(String name){
        String api = "id?character_name="+name;

        return this.webClient.get()
                .uri(api)
                .retrieve()
                .bodyToMono(Map.class)
                .flatMap(result -> this.getOneCharInfoWithOcid((String) result.get("ocid")));
    }
    public Mono<CharacterBasic> getOneCharInfoWithOcid(String ocid)  {
        String api;
        if ( LocalTime.now().isAfter(LocalTime.of(0,0)) && LocalTime.now().isBefore(LocalTime.of(6,0)) ) {
            api = "character/basic?ocid="+ocid+"&date="+ LocalDate.now().minusDays(2);
        } else {
            api = "character/basic?ocid="+ocid+"&date="+ LocalDate.now().minusDays(1);
        }
        //오늘의 바로 전날을 타겟

        return this.webClient.get()
                .uri(api)
                .retrieve()
                .bodyToMono(CharacterBasic.class)
                .map(result -> {
                    result.setOcid(ocid);
                    return result;
                });

    }

    public Mono<List<CharacterBasic>> getCharInfoList(CharRefreshRequestDto charRefreshRequestDto) {
        Flux<CharacterBasic> fromNameList = Flux
                .fromIterable(charRefreshRequestDto.getNameList())
                .flatMap(name -> this.getOcid(name));

        Flux<CharacterBasic> fromOcidList = Flux
                .fromIterable(charRefreshRequestDto.getOcidList())
                .flatMap(ocid -> this.getOneCharInfoWithOcid(ocid));

        return Flux.merge(fromNameList, fromOcidList)
                .collectList();
    }
}
