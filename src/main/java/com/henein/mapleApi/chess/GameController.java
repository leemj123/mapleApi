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
    public HashMap<String, String> roomSet(){
        boolean key = new Random().nextBoolean();
        HashMap<String,String> map = new HashMap<>();

        if (key) {
            map.put("white",String.valueOf(UUID.randomUUID()));
        } else {
            map.put("black",String.valueOf(UUID.randomUUID()));
        }

        return map;
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
