package com.example.cs2sw.mysql.table;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "stickers")
@Data
public class StickerDTO {
    // sticker name
    @Id
    @Column(name="name", nullable = false, unique = true)
    private String name;

    // symbols shown on the sticker
    @Column(name="chars", nullable = false)
    private String chars;
}
