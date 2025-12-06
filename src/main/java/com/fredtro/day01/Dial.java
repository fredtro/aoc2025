package com.fredtro.day01;

import java.util.List;
import java.util.Objects;

public class Dial {

    // keep default start position as instance default but don't rely on instance state between calls
    private static final int DEFAULT_POSITION = 50;
    private static final int RING_SIZE = 100;

    public int rotateAndCountWhenZero(List<Rotation> rotations) {
        Objects.requireNonNull(rotations, "rotations must not be null");

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

    public int rotateAndCountAllZero(List<Rotation> rotations) {
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

}
