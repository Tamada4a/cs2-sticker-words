package com.example.cs2sw.mysql.repository;

import com.example.cs2sw.mysql.table.StickerDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public interface StickerRepository extends JpaRepository<StickerDTO, String> {
    ArrayList<StickerDTO> findByChars(final String chars);

    boolean existsByChars(final String chars);
}
