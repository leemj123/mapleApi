package com.henein.mapleApi.dto;

import lombok.Getter;

@Getter
public class ChessDto {
    private int id;
    private int x;
    private int y;
    public ChessDto() {
        this.id = 16;
        this.x = 3;
        this.y = 6;
    }
}
