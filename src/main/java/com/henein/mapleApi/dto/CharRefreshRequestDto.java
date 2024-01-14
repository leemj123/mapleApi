package com.henein.mapleApi.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class CharRefreshRequestDto {
    private List<String> nameList;
    private List<String> ocidList;
}
