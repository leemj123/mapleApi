package com.henein.mapleApi.chess;



import java.util.List;

public class King extends Piece{
    private boolean hasMoved; //캐슬링 체크용

    public King(int id, int x, int y) {
        super(id, x, y);
        this.hasMoved = false;
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
        this.hasMoved = true;
    }


}
