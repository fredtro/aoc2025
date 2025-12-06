package com.fredtro.day02;

import com.fredtro.util.FileReader;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

public class InvalidIdFinder {


    public static long getSolutionA() {
        List<IdRange> ranges = getIdRanges();

        return ranges.stream()
            .flatMapToLong(idRange -> LongStream.range(idRange.from(), idRange.to() + 1))
            .mapToObj(Long::toString)
            .filter(id -> !id.startsWith("0"))
            .filter(id -> id.length() % 2 == 0)
            .filter(id -> {
                String firstHalf = id.substring(0, id.length() / 2);
                String secondHalf = id.substring(id.length() / 2);

                return firstHalf.equals(secondHalf);
            })
            .mapToLong(Long::parseLong).sum();
    }

    public static long getSolutionB() {
        List<IdRange> ranges = getIdRanges();

        return ranges.stream()
            .flatMapToLong(idRange -> LongStream.range(idRange.from(), idRange.to() + 1))
            .mapToObj(Long::toString)
            .filter(id -> !id.startsWith("0"))
            .filter(id -> {

                for (int i = 1; i <= id.length() / 2; i++) {
                    var slice = id.substring(0, i);
                    Pattern p = Pattern.compile(slice);

                    var matcher = p.matcher(id);
                    int matches = 0;
                    while (matcher.find()) { // 31755323497 -- 31755323497
                        matches++;
                    }

                    if ((matches * slice.length()) == id.length()) {
                        return true;
                    }
                }

                return false;
            })
            .mapToLong(Long::parseLong).sum();
            //.collect(Collectors.joining(", "));
    }


    public static List<IdRange> getIdRanges() {
        return FileReader.parse("/day02/ids.txt")
            .stream()
            .flatMap(s -> Arrays.stream(s.split(",")))
            .map(range -> {
                String[] split = range.split("-");
                return new IdRange(Long.parseLong(split[0]), Long.parseLong(split[1]));
            })
            .toList();
    }

    public record IdRange(long from, long to) {
    }
}
