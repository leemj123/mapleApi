package com.henein.mapleApi.chess;



import java.util.List;

public class Bishop extends Piece{


    public Bishop(int id, int x, int y) {
        super(id, x, y);
    }

    public Bishop(PieceInfoDto pieceInfoDto) {
        super(pieceInfoDto);
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
