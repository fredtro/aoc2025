package com.fredtro.day03;

import com.fredtro.util.FileReader;

import java.util.*;
import java.util.stream.IntStream;

public class JoltageCalculator {

    public static int getResultA() {
        return getLines().stream()
            .map(String::toCharArray)
            .map(chars -> {

                // Represent each character with index and value to be able to order by index later
                List<Element> elements = IntStream.range(0, chars.length)
                    .mapToObj(i -> new Element(i, chars[i]))
                    .toList();

                // Find global max
                Element max = elements.stream()
                    .max(Comparator.comparingInt(Element::value))
                    .orElseThrow();

                // Determine second element depending on max index
                // If max is on last position - get max from all but max
                Element second = (max.index() == chars.length - 1)
                    ? elements.stream()
                    .filter(e -> e != max)
                    .max(Comparator.comparingInt(Element::value))
                    .orElseThrow()
                    : elements.stream()
                    .filter(e -> e.index() > max.index())
                    .max(Comparator.comparingInt(Element::value))
                    .orElseThrow();

                return List.of(max, second);
            })
            .mapToInt(pair -> {
                int a = pair.get(0).value() - '0';
                int b = pair.get(1).value() - '0';
                return a * 10 + b;
            })
            .sum();
    }

    public static long getResultB() {
        return getLines().stream()
            .map(String::toCharArray)
            .map(chars -> computeResultForLine(chars, 12))
            .mapToLong(Long::longValue)
            .sum();
    }

    private static long computeResultForLine(char[] chars, int totalSpots) {

        // Create elements ahead of time
        List<Element> elements = new ArrayList<>(chars.length);
        for (int i = 0; i < chars.length; i++) {
            elements.add(new Element(i, chars[i]));
        }

        Set<Element> result = new LinkedHashSet<>(totalSpots);
        Element max = getMax(elements);

        while (result.size() < totalSpots && max != null) {

            int remainingSlots = totalSpots - result.size() - 1;
            int itemsRight = chars.length - max.index() - 1;

            if (itemsRight >= remainingSlots) {
                // we can safely pick this max
                result.add(max);

                int idx = max.index();
                // only keep elements strictly to the right
                elements = elements.stream()
                    .filter(e -> e.index() > idx)
                    .toList();

                max = getMax(elements);

            } else {
                // we must search left side instead
                int idx = max.index();
                List<Element> leftSide = elements.stream()
                    .filter(e -> e.index() < idx && !result.contains(e))
                    .toList();

                max = getMax(leftSide);
            }
        }

        // Build number
        long value = 0;
        for (Element e : result) {
            value = value * 10 + (e.value() - '0');
        }

        return value;
    }

    private static Element getMax(List<Element> elements) {
        return elements.stream()
            .max(Comparator.comparingInt(Element::value))
            .orElse(null);
    }


    private static List<String> getLines() {
        return FileReader.parse("/day03/input.txt");
    }

    record Element(int index, char value) {
        @Override
        public String toString() {
            return "index: " + index + ", value: " + (value);
        }
    }
}
