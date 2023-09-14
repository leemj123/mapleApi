package com.henein.mapleApi.chess;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/game")
@RequiredArgsConstructor
public class GameController {
    private final GameMainService gameMainService;
    @GetMapping("/roomset")
    public String roomSet(){
        return String.valueOf(UUID.randomUUID());
    }
    @PostMapping("/init")
    public PieceInfoDto[][] gameSetting(@RequestHeader("Room")String roomId){
        return gameMainService.setting2DChessBoard(roomId);
    }
//    @GetMapping("/piece/valid-list")
//    public List<Point> getMoveValidList(@RequestHeader("Room")String roomId, @RequestBody PieceInfoDto pieceInfoDto) {
//        return gameMainService.getMoveValidList(roomId,pieceInfoDto);
//    }

}
