package com.fredtro.day01;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class RotationParser {

    public List<Rotation> parse(String filename) {
        InputStream is = getClass().getResourceAsStream("/" + filename);
        if (is == null) {
            throw new IllegalStateException("Resource '/rotations.txt' not found on classpath");
        }

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8))) {
            return reader.lines()
                .map(String::trim)
                .map(line -> new Rotation(line.substring(0, 1), Integer.parseInt(line.substring(1))))
                .toList();
        } catch (Exception e) {
            throw new RuntimeException("Failed to read rotations resource", e);
        }

    }

}
