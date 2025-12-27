package com.fredtro.day07;

import com.fredtro.util.FileReader;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class DaySeven {

    static final char START = 'S';
    static final char SPLITTER = '^';

    public static long getResultPartOne() {
        List<String> lines = getLines();
        Set<Integer> activeColumns = new HashSet<>();
        long splitCount = 0;

        for (String line : lines) {
            Set<Integer> nextActiveColumns = new HashSet<>();

            for (int j = 0; j < line.length(); j++) {
                char c = line.charAt(j);

                if (c == 'S') {
                    nextActiveColumns.add(j);
                } else if (c == '^' && activeColumns.contains(j)) {
                    splitCount++;  
                    nextActiveColumns.add(j - 1);
                    nextActiveColumns.add(j + 1);
                } else if (activeColumns.contains(j)) {
                    nextActiveColumns.add(j);
                }
            }

            activeColumns = nextActiveColumns;
        }

        return splitCount;
    }

    public static List<String> getLines() {
        return FileReader.parse("/day07/input.txt");
    }
    

}
 