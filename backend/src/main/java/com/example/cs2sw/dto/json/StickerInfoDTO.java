package com.example.cs2sw.dto.json;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;

@Data
@AllArgsConstructor
public class StickerInfoDTO {
    private String id;

    private String name;

    private String description;

    private RarityDTO rarity;

    private ArrayList<CratesDTO> crates;

    private String image;
}
