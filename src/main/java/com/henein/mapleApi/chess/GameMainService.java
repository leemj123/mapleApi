package com.henein.mapleApi.chess;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GameMainService {
    private final ChessBoardSetting chessBoardSetting;

    public PieceInfoDto[][] setting2DChessBoard(String roomId) {

        TwoDBoard twoDBoard = new TwoDBoard();
        twoDBoard.inItBoard(chessBoardSetting.setting(twoDBoard.MAX_X, twoDBoard.MAX_Y));

        return Arrays.stream(twoDBoard.getBoard())
                .map(row -> Arrays.stream(row)
                        .map(piece -> {
                            if ( piece == null) return null;
                            return new PieceInfoDto(piece);
                        })
                        .toArray(PieceInfoDto[]::new))
                .toArray(PieceInfoDto[][]::new);
    }

}
