package com.example.cs2sw.service;

import com.example.cs2sw.dto.StickerImageDTO;
import com.example.cs2sw.dto.StickersBlockDTO;
import com.example.cs2sw.dto.StickersColDTO;
import com.example.cs2sw.dto.json.CratesDTO;
import com.example.cs2sw.dto.json.StickerInfoDTO;
import com.example.cs2sw.exception.CustomException;
import com.example.cs2sw.mysql.repository.StickerRepository;
import com.example.cs2sw.mysql.table.StickerDTO;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.*;

@Service
public record GenerateWordService(StickerRepository stickerRepository) {
    public ArrayList<StickersBlockDTO> generateWord(int maxStickers, boolean isUnusualSticks, String wordToGenerate) throws IOException, CustomException {
        if (maxStickers < 1 || maxStickers > 5)
            throw new CustomException("The maximum number of stickers should be in the range from 1 to 5", HttpStatus.BAD_REQUEST);

        Set<ArrayList<String>> charCombinations = findCharCombinations(replaceSpacesToUpperCase(wordToGenerate));

        ArrayList<StickerInfoDTO> stickersJsonData = getStickersFromJSON();

        ArrayList<StickersBlockDTO> stickersBlockList = new ArrayList<>();
        for (ArrayList<String> charList : charCombinations) {
            if (charList.size() != maxStickers)
                continue;

            ArrayList<StickersColDTO> stickersColList = new ArrayList<>();
            for (int idx = 0; idx < charList.size(); ++idx) {
                String chars = charList.get(idx);
                ArrayList<StickerImageDTO> stickerImageList = new ArrayList<>();
                if (stickerRepository.existsByChars(chars))
                    stickerImageList.addAll(stickersDtoToStickersImageDto(stickerRepository.findByChars(chars), stickersJsonData, isUnusualSticks));
                if (isCharInAutographData(chars, stickersJsonData))
                    stickerImageList.addAll(getAutographsByChars(idx, charList.size(), isUnusualSticks, chars, stickersJsonData));

                if (!stickerImageList.isEmpty())
                    stickersColList.add(new StickersColDTO(chars, stickerImageList));
            }

            if (stickersColList.size() == charList.size())
                stickersBlockList.add(new StickersBlockDTO(String.join(" ", charList), stickersColList));
        }

        stickersBlockList.sort(Comparator.comparingInt(o -> o.getCharKey().split(" ").length));

        return stickersBlockList;
    }


    private ArrayList<StickerImageDTO> stickersDtoToStickersImageDto(ArrayList<StickerDTO> stickersByCharComb, ArrayList<StickerInfoDTO> stickersJsonData, boolean isUnusualSticks) {
        ArrayList<StickerImageDTO> stickersResult = new ArrayList<>();

        for (StickerDTO stickerDTO : stickersByCharComb) {
            if (isCorrectAutograph(isUnusualSticks, stickerDTO.getName().toUpperCase(Locale.ROOT)))
                stickersResult.add(new StickerImageDTO(getStickerImageSrc(stickerDTO.getName(), stickersJsonData), stickerDTO.getName()));
        }

        return stickersResult;
    }


    private String getStickerImageSrc(String stickerName, ArrayList<StickerInfoDTO> stickersJsonData) {
        for (StickerInfoDTO stickerInfoDTO : stickersJsonData) {
            if (stickerInfoDTO.getName().contains(stickerName))
                return stickerInfoDTO.getImage();
        }
        return "";
    }


    private String replaceSpacesToUpperCase(String word) {
        return word.replaceAll(" ", "").toUpperCase(Locale.ROOT);
    }


    // Source: https://www.techiedelight.com/find-combinations-non-overlapping-substrings-string/
    private static Set<ArrayList<String>> findCharCombinations(String s) {
        Set<ArrayList<String>> combinations = new HashSet<>();

        // base case
        if (s == null || s.length() == 0) {
            return combinations;
        }
        // string to store non-overlapping substrings
        Deque<String> substring = new ArrayDeque<>();

        // find all non-overlapping substrings
        findNonOverlappingCombinations(s, substring, combinations);

        return combinations;
    }


    // Find all combinations of non-overlapping substrings of a given string
    private static void findNonOverlappingCombinations(String str, Deque<String> substring, Set<ArrayList<String>> combinations) {
        // if all characters of the input string are processed,
        // add the output string to result
        if (str.length() == 0) {
            combinations.add(new ArrayList<>(substring));
            return;
        }

        // add each substring `str[0, i]` to the output string and recur for
        // remaining substring `str[i+1, n-1]`
        for (int i = 0; i < str.length(); i++) {
            // push substring `str[0, i]` into the output string
            substring.addLast(str.substring(0, i + 1));

            // recur for the remaining string `str[i+1, n-1]`
            findNonOverlappingCombinations(str.substring(i + 1), substring, combinations);

            // backtrack: remove current substring from the output
            substring.pollLast();
        }
    }


    // Get all stickers from JSON provided by https://github.com/ByMykel/CSGO-API
    private ArrayList<StickerInfoDTO> getStickersFromJSON() throws IOException {
        Type listType = new TypeToken<ArrayList<StickerInfoDTO>>() {
        }.getType();
        JsonReader reader = new JsonReader(new FileReader("src/main/resources/stickers_data.json"));
        return new Gson().fromJson(reader, listType);
    }


    private boolean isCharInAutographData(String charsComb, ArrayList<StickerInfoDTO> stickersJsonData) {
        for (StickerInfoDTO stickerInfoDTO : stickersJsonData) {
            ArrayList<CratesDTO> crates = stickerInfoDTO.getCrates();
            if (crates.isEmpty())
                continue;

            if (!crates.get(0).getName().contains("Autograph"))
                continue;

            String[] splitStickerName = stickerInfoDTO.getName().split(" \\| ");

            if (splitStickerName[1].toUpperCase(Locale.ROOT).startsWith(charsComb))
                return true;
        }
        return false;
    }


    private ArrayList<StickerImageDTO> getAutographsByChars(int idx, int charListSize, boolean isUnusualSticks, String charsComb, ArrayList<StickerInfoDTO> stickersJsonData) {
        ArrayList<StickerImageDTO> stickersResult = new ArrayList<>();

        for (StickerInfoDTO stickerInfoDTO : stickersJsonData) {
            ArrayList<CratesDTO> crates = stickerInfoDTO.getCrates();
            if (crates.isEmpty())
                continue;

            if (!crates.get(0).getName().contains("Autograph"))
                continue;

            String[] splitStickerName = stickerInfoDTO.getName().split(" \\| ");

            String playerNick = splitStickerName[1].toUpperCase(Locale.ROOT);

            if (isCharOnCorrectPlace(playerNick, charsComb, idx, charListSize) && isCorrectAutograph(isUnusualSticks, playerNick)) {
                stickersResult.add(new StickerImageDTO(stickerInfoDTO.getImage(), stickerInfoDTO.getName()));
            }
        }

        return stickersResult;
    }


    private boolean isCharOnCorrectPlace(String playerNick, String charsComb, int idx, int charListSize) {
        if (idx == 0)
            return playerNick.startsWith(charsComb);

        if (idx == (charListSize - 1))
            return playerNick.endsWith(charsComb);

        return playerNick.contains(charsComb);
    }


    private boolean isCorrectAutograph(boolean isUnusualSticks, String playerNick) {
        if (!isUnusualSticks)
            return !playerNick.contains("(HOLO") && !playerNick.contains("(GOLD") && !playerNick.contains("(GLITTER")
                    && !playerNick.contains("(LENTICULAR") && !playerNick.contains("(FOIL") && !playerNick.contains("CHAMPION)");

        return true;
    }
}
