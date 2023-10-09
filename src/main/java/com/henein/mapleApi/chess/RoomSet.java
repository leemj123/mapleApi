package com.henein.mapleApi.chess;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class RoomSet {
    private PlayerRole color;
    private String roomKey;
}
