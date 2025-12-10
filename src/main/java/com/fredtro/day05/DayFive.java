package com.fredtro.day05;

import com.fredtro.util.FileReader;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.LongStream;

public class DayFive {

    public static long getResultPartOne() {
        List<IdRange> ranges = getIdRanges();
        return getIds().stream()
            .filter(id -> ranges.stream().anyMatch(range -> range.contains(id)))
            .count();
    }

    public static List<IdRange> getIdRanges() {
        return FileReader.parse("/day05/input.txt")
            .stream()
            .takeWhile(s -> s == null || !s.isBlank())
            .map(s -> s.split("-"))
            .map(split -> new IdRange(Long.parseLong(split[0]), Long.parseLong(split[1])))
            .toList();
    }

    public static List<Long> getIds() {
        return FileReader.parse("/day05/input.txt")
            .stream()
            .dropWhile(line -> !line.isEmpty()) // skip until first empty
            .skip(1)
            .map(Long::parseLong)
            .toList();
    }

    private static List<IdRange> getMergedRanges() {
        List<IdRange> ranges = new ArrayList<>(getIdRanges());
        List<IdRange> mergedRanges = new ArrayList<>();
        ranges.sort(Comparator.comparingLong(r -> r.from));

        IdRange current = ranges.getFirst();

        for (int i = 1; i < ranges.size(); i++) {
            IdRange next = ranges.get(i);

            if (next.from <= current.to) {
                current = new IdRange(current.from, Math.max(current.to, next.to));
            } else {
                mergedRanges.add(current);
                current = next;
            }

            if (i == ranges.size() - 1) {
                mergedRanges.add(current);
            }
        }

        return mergedRanges;
    }

    public static long getResultPartTwo() {
        return getMergedRanges()
            .stream().mapToLong(range -> range.to - range.from + 1)
            .sum();
    }

    public enum FreshnessState {
        FRESH,
        SPOILED
    }

    record IdRange(Long from, Long to) {
        public boolean contains(Long id) {
            return id >= from && id <= to;
        }
    }
}
