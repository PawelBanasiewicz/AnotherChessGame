package chess.utils;

import java.util.Map;

public class MoveMaps {
    static public Map<String, Integer> stringToIntColumnMap = Map.of(
            "A", 0,
            "B", 1,
            "C", 2,
            "D", 3,
            "E", 4,
            "F", 5,
            "G", 6,
            "H", 7);

    static public Map<String, Integer> stringToIntRowMap = Map.of(
            "8", 0,
            "7", 1,
            "6", 2,
            "5", 3,
            "4", 4,
            "3", 5,
            "2", 6,
            "1", 7);


    static public Map<Integer, String> intToStringColumnMap = Map.of(
            0, "A",
            1, "B",
            2, "C",
            3, "D",
            4, "E",
            5, "F",
            6, "G",
            7, "H");

    static public Map<Integer, String> intToStringRowMap = Map.of(
            0, "8",
            1, "7",
            2, "6",
            3, "5",
            4, "4",
            5, "3",
            6, "2",
            7, "1");
}