package com.henein.mapleApi.chess;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/game")
@RequiredArgsConstructor
@Slf4j
public class GameController {
    private final GameMainService gameMainService;
    @GetMapping("/roomset")
    public RoomSet roomSet(){
        boolean key = new Random().nextBoolean();
        if (key) {
            return new RoomSet(PlayerRole.White,String.valueOf(UUID.randomUUID()));
        } else {
            return new RoomSet(PlayerRole.Black,String.valueOf(UUID.randomUUID()));
        }


    }
    @PostMapping("/init")
    public List<PieceInfoDto> gameSetting(@RequestHeader("Room")String roomId){
        log.info("init 들어옴"+roomId);
        return gameMainService.setting2DChessBoard(roomId);
    }
//    @GetMapping("/piece/valid-list")
//    public List<Point> getMoveValidList(@RequestHeader("Room")String roomId, @RequestBody PieceInfoDto pieceInfoDto) {
//        return gameMainService.getMoveValidList(roomId,pieceInfoDto);
//    }

}
