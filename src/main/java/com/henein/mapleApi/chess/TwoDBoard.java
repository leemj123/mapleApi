package com.henein.mapleApi.chess;

import lombok.Getter;

import java.util.List;

@Getter
public class TwoDBoard {
    public final int MAX_X = 8;
    public final int MAX_Y = 8;
    private List<Piece> board;

    public void inItBoard(List<Piece> board) {
        this.board = board;
    }

}
