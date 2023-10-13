package com.henein.mapleApi.chess;

import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/game")
@RequiredArgsConstructor
@Slf4j
@Api(tags = {"재욱이 것들"})
public class GameController {
    private final GameMainService gameMainService;
    private final RedisService redisService;
    @Operation(summary = "룸 키와 컬러 발급받기")
    @GetMapping("/roomset")
    public RoomSet roomSet() {
        boolean key = new Random().nextBoolean();
        if (key) {
            return new RoomSet(PlayerRole.White, String.valueOf(UUID.randomUUID()));
        } else {
            return new RoomSet(PlayerRole.Black, String.valueOf(UUID.randomUUID()));
        }
    }
    @Operation(summary = "룸키로 전체 기물 정보 리셋하기")
    @PostMapping("/init")
    public List<PieceInfoDto> gameSetting(@RequestHeader("Room")String roomId){
        return gameMainService.setting2DChessBoard(roomId);
    }
    @Operation(summary = "기물 id로 움직일 수 있는 칸 찾기")
    @GetMapping("/piece/valid-list/{id}")
    public List<Point> getMoveValidList(@RequestHeader("Room")String roomId, @PathVariable int id) {
        return gameMainService.getMoveValidList(roomId,id);
    }
    @Operation(summary = "기물 움직이기")
    @PostMapping("piece/move")
    public PieceInfoDto movePiece(@RequestHeader("Room")String roomId, @RequestBody PieceInfoDto pieceInfoDto) {
        return gameMainService.movePiece(roomId,pieceInfoDto);
    }
    @Operation(summary = "기물 id로 기물 정보들 받아오기")
    @GetMapping("piece/info/{id}")
    public PieceInfoDto whereIsMyPiece(@RequestHeader("Room")String roomId, @PathVariable int id) {
        return redisService.checkPieceLocation(roomId,id);
    }

}
