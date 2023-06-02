package com.henein.mapleApi.dto;

import lombok.Getter;
import lombok.SneakyThrows;

import java.util.List;

@Getter
public class CubeHistoryResponseDto {
    private List<UserNameResponseDto> cube_histories;
    private String next_cursor;
}
