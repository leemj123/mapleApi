package com.henein.mapleApi.chess;


import java.util.List;

public class Rook extends Piece {
    private boolean hasMoved;

    public Rook(int id, int x, int y) {
        super(id, x, y);
        this.hasMoved = false;
    }
    public Rook(PieceInfoDto pieceInfoDto) {
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
