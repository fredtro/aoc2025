package com.fredtro.day01;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class DialTest {

    private final Dial dial = new Dial();
    private final RotationParser parser = new RotationParser();

    private static final String ROTATIONS_FILE = "day01/rotations.txt";
    private static final String ROTATIONS_TEST_FILE = "day01/rotations-test.txt";

    @Test
    void rotateAndCountWhenZero() {
        //assertThat(dial.rotateAndCountWhenZero(parser.parse(ROTATIONS_FILE))).isEqualTo(1097);
        assertThat(dial.rotateAndCountAllZero(parser.parse(ROTATIONS_FILE))).isEqualTo(7101);
    }
}