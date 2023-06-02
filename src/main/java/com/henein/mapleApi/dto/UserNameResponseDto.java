package com.henein.mapleApi.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor

public class UserNameResponseDto {
    private String character_name;

    public UserNameResponseDto(String character_name) {
        this.character_name = character_name;
    }
}
