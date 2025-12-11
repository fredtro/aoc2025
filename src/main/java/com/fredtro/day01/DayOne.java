package com.fredtro.day01;

import com.fredtro.util.FileReader;

import java.util.List;
import java.util.Objects;

public class DayOne {

    // keep default start position as instance default but don't rely on instance state between calls
    private static final int DEFAULT_POSITION = 50;
    private static final int RING_SIZE = 100;

    /**
     * Count whenever dial is exactly at position 0
     */
    public static int getResultPartOne() {
        List<Rotation> rotations = getRotations();

        int position = DEFAULT_POSITION;
        int zeroCounter = 0;

        for (var rotation : rotations) {
            var dir = rotation.direction();
            var dist = rotation.distance();

            position = switch (dir) {
                case "L" -> Math.floorMod(position - dist, RING_SIZE);
                case "R" -> Math.floorMod(position + dist, RING_SIZE);
                default -> throw new IllegalStateException("Unexpected direction: " + dir);
            };

            if (position == 0) {
                zeroCounter++;
            }
        }

        return zeroCounter;
    }



    /**
     *
     * Count whenever dial crosses or is exactly at position 0
     */
    public static int getResultPartTwo() {
        List<Rotation> rotations = getRotations();
        Objects.requireNonNull(rotations, "rotations must not be null");

        int position = DEFAULT_POSITION;
        int zeroCounter = 0;

        for (var rotation : rotations) {
            var direction = rotation.direction();
            var distance = rotation.distance();

            switch (direction) {
                case "R" -> {
                    int sum = position + distance;
                    // Count multiples of RING_SIZE in (position, raw]
                    zeroCounter += Math.floorDiv(sum, RING_SIZE);
                    position = Math.floorMod(sum, RING_SIZE);
                }

                case "L" -> {
                    int difference = position - distance;
                    // Count multiples of RING_SIZE in [raw, position-1]
                    zeroCounter += Math.floorDiv(position - 1, RING_SIZE) - Math.floorDiv(difference - 1, RING_SIZE);
                    position = Math.floorMod(difference, RING_SIZE);
                }

                default -> throw new IllegalStateException("Unexpected direction: " + direction);
            }
        }

        return zeroCounter;
    }

    private static List<Rotation> getRotations() {
        return FileReader.parse("/day01/input.txt",
            s -> new Rotation(s.substring(0, 1), Integer.parseInt(s.substring(1))));
    }

    public record Rotation(String direction, Integer distance) {
    }
}
