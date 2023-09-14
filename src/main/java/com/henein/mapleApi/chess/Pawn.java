package com.henein.mapleApi.chess;


import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class Pawn extends Piece{

    public Pawn(int id, int x, int y) {
        super(id, x, y);
    }
    public Pawn(PieceInfoDto pieceInfoDto) {
        super(pieceInfoDto);
    }

    @Override
    public List<Point> getValidMoveList(PieceInfoDto pieceInfoDto) {
        List<Point> validMoves = new ArrayList<>();

        // 흑백 판별
        int direction;
        if (9<= pieceInfoDto.getId() && pieceInfoDto.getId() <= 16) { //흑
            direction = 1;
        }
        else if(17 <= pieceInfoDto.getId() && pieceInfoDto.getId() <= 24) {// 백
            direction = -1;
        } else {
            throw new RuntimeException("this is not Pawn");
        }

        //한칸 전진
        Point oneStepMove = new Point(pieceInfoDto.getX(), pieceInfoDto.getY() + direction);
        validMoves.add(oneStepMove);

        //두칸 전진
        if (!(this.isHasMoved())) {
            Point twoStepMove = new Point(pieceInfoDto.getX(), pieceInfoDto.getY() + (2*direction));
            validMoves.add(twoStepMove);
        }
        //대각선에 기물있는지 판별


        return validMoves;
    }

    @Override
    public boolean isValidMove(PieceInfoDto pieceInfoDto) {
        List<Point> validMoves = getValidMoveList(pieceInfoDto);
        Point piecePoint = new Point(pieceInfoDto);
        return validMoves.contains(piecePoint); // 리스트에서 검사하여 있으면 true
    }

    @Override
    public void move() {
    }


}
