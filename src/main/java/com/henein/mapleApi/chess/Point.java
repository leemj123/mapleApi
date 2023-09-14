package com.henein.mapleApi.chess;


public class Point {
    private int x;
    private int y;

    public Point(PieceInfoDto pieceInfoDto) {
        this.x = pieceInfoDto.getX();
        this.y = pieceInfoDto.getY();
    }
    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }
}
