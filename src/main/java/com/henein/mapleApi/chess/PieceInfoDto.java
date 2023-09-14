package com.henein.mapleApi.chess;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PieceInfoDto {
    int id;
    int x;
    int y;
    public PieceInfoDto(Piece piece){
        this.id = piece.getId();
        this.x = piece.getX();
        this.y = piece.getY();
    }
}
