package com.henein.mapleApi.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class CubeHistoryResponseDto {
    private List<UserNameResponseDto> cube_histories;
}
