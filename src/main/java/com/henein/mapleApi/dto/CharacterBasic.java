package com.henein.mapleApi.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CharacterBasic {
    private String ocid;
    private String character_name;
    private String world_name;
    private String character_class;
    private int character_level;
    private String character_image;
}
