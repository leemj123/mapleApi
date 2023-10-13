package com.henein.mapleApi.chess;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Color {

    White("w",0),
    Black("b",1);
     private final String title;
     private final int key;

}
