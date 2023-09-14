package com.henein.mapleApi.chess;

import lombok.Getter;

@Getter
public class TwoDBoard {
    public final int MAX_X = 8;
    public final int MAX_Y = 8;
    private Piece[][] board;
    public TwoDBoard(){
        Piece[][] board = new Piece[MAX_X][MAX_Y];
    }

    public void inItBoard(Piece[][] board) {
        this.board = board;
    }

}
