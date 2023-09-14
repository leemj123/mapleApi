package com.henein.mapleApi.chess;


import lombok.Getter;

import java.util.List;

@Getter
public abstract class Piece {

    private int id;
    private int x;
    private int y;
    private boolean hasMoved;

    public Piece(int id, int x, int y) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.hasMoved = false;
    }
    public Piece (PieceInfoDto pieceInfoDto){
        this.id = pieceInfoDto.getId();
        this.x = pieceInfoDto.getX();
        this.y = pieceInfoDto.getY();
    }

    public abstract List<Point> getValidMoveList(PieceInfoDto pieceInfoDto);

    public abstract boolean isValidMove(PieceInfoDto pieceInfoDto);

    public abstract void move();
}
