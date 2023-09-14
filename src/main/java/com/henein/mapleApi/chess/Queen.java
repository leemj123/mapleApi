package com.henein.mapleApi.chess;



import java.util.List;

public class Queen extends Piece{
    public Queen(int id, int x, int y) {
        super(id, x, y);
    }

    @Override
    public List<Point> getValidMoveList(PieceInfoDto pieceInfoDto) {
        return null;
    }

    @Override
    public boolean isValidMove(PieceInfoDto pieceInfoDto) {
        return false;
    }

    @Override
    public void move() {

    }


}
