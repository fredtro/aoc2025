package com.fredtro.day03;

import com.fredtro.util.FileReader;

import java.util.*;

public class DayThree {

    public static int getResultPartOne() {
        return getLines().stream()
            .map(String::toCharArray)
            .map(bank -> {
                List<Element> elements = new ArrayList<>();
                for (int i = 0; i < bank.length; i++) {
                    elements.add(new Element(i, bank[i]));
                }

                Element max = Collections.max(elements, Comparator.comparingInt(Element::value));

                if (max.index == bank.length - 1) {
                    elements.remove(max);
                    return List.of(Collections.max(elements, Comparator.comparingInt(Element::value)), max);
                } else { // all elements after max
                    var elementsAfterMax = elements.subList(max.index + 1, elements.size());

                    return List.of(max, Collections.max(elementsAfterMax, Comparator.comparingInt(Element::value)));
                }
            }).peek(System.out::println).mapToInt(elements -> {
                int a = elements.getFirst().value - '0';
                int b = elements.getLast().value - '0';
                return a * 10 + b;
            }).sum();
    }

    public static long getResultPartTwo() {
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
