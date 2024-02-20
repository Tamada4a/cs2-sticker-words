package com.example.cs2sw.controller;

import com.example.cs2sw.dto.StickersBlockDTO;
import com.example.cs2sw.exception.CustomException;
import com.example.cs2sw.service.GenerateWordService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.ArrayList;

@RestController
public record GenerateWordController(GenerateWordService generateWordService) {
    @GetMapping(value = "/generateWord")
    public ResponseEntity<ArrayList<StickersBlockDTO>> generateWord(@RequestParam(defaultValue = "5") int maxStickers,
                                                                    @RequestParam boolean isUnusualSticks,
                                                                    @RequestParam String wordToGenerate) throws IOException, CustomException {
        return ResponseEntity.ok(generateWordService.generateWord(maxStickers, isUnusualSticks, wordToGenerate));
    }
}
