package com.henein.mapleApi.chess;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GameMainService {
    private final ChessBoardSetting chessBoardSetting;
    private final RedisService redisService;

    public List<PieceInfoDto> setting2DChessBoard(String roomId) {

        TwoDBoard twoDBoard = new TwoDBoard();
        twoDBoard.inItBoard(chessBoardSetting.setting(twoDBoard.MAX_X, twoDBoard.MAX_Y));


        return twoDBoard.getBoard().stream().map(piece -> {
            PieceInfoDto dto = new PieceInfoDto(piece);
            redisService.savePiece(dto,roomId);
            return dto;
        }).collect(Collectors.toList());
    }

    public PieceInfoDto movePiece(String roomId,PieceInfoDto pieceInfoDto) {
        Piece piece = bringPieceInfo(roomId, pieceInfoDto.getId());
        if (!piece.isValidMove(pieceInfoDto,redisService.getAllInGamePieceList(roomId))) {
            throw new RuntimeException();
        }
        return redisService.updatePiece(roomId, pieceInfoDto);
    }
    public List<Point> getMoveValidList(String roomId,int id) {
        Piece piece = bringPieceInfo(roomId, id);
        return piece.getValidMoveList(redisService.getAllInGamePieceList(roomId));
    }


    private Piece bringPieceInfo(String roomId,int pieceId){

        if (9<= pieceId && pieceId <= 24) {
            return new Pawn(redisService.checkPieceLocation(roomId, pieceId));
        }
        switch (pieceId) {
            case 1: case 8: case 25: case 32:
                return new Rook(redisService.checkPieceLocation(roomId, pieceId));
            case 2: case 7: case 26: case 31:
                return new Knight(redisService.checkPieceLocation(roomId, pieceId));
            case 3: case 6: case 27: case 30:
                return new Bishop(redisService.checkPieceLocation(roomId, pieceId));
            case 4: case 28:
                return new Queen(redisService.checkPieceLocation(roomId, pieceId));
            case 5: case 29:
                return new King(redisService.checkPieceLocation(roomId, pieceId));
            default:
                throw new RuntimeException();

        }

    }
}
