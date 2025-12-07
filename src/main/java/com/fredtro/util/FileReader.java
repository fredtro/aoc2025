package com.fredtro.util;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.function.Function;

public class FileReader {

    public static List<String> parse(String location) {
        return parse(location, Function.identity());
    }

    public static <T> List<T> parse(String location, Function<String, T> mapper) {
        InputStream is = FileReader.class.getResourceAsStream(location);
        if (is == null) {
            throw new IllegalStateException("Couldn't load file: " + location);
        }

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8))) {
            return reader.lines()
                .map(String::trim)
                .map(mapper)
                .toList();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
