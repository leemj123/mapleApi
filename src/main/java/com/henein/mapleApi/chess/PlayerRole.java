package com.henein.mapleApi.chess;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum PlayerRole {
    White(true,"white"),
    Black(false,"black");

    private final boolean key;
    private final String role;
}
