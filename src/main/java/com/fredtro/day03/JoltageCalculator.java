package com.fredtro.day03;

import com.fredtro.util.FileReader;

import java.util.Comparator;
import java.util.List;
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
