package com.example.cs2sw.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;

@Data
@AllArgsConstructor
public class StickersColDTO {
    private String colName;

    private ArrayList<StickerImageDTO> stickersList;
}
