package com.henein.mapleApi.dto;

import lombok.Getter;
import lombok.SneakyThrows;

import java.util.List;

@Getter
public class CubeHistoryResponseDto {
    private String next_cursor;
    private List<UserNameResponseDto> cube_history;
}
