package com.henein.mapleApi.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class FCMRequestDto {
    private Long userId;
    private String title;
    private String body;

    @Builder
    public FCMRequestDto (Long userId, String title, String body) {
        this.userId =userId;
        this.title = title;
        this.body = body;
    }
}

