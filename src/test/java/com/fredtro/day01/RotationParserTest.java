package com.fredtro.day01;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class RotationParserTest {

    private final RotationParser parser = new RotationParser();

    @Test
    void parse() {
        List<Rotation> rotations = parser.parse("day01/rotations-test.txt");
        assertThat(rotations).isNotEmpty()
            .hasOnlyElementsOfType(Rotation.class)
            .allSatisfy(rotation -> {
                assertThat(rotation.direction()).isNotNull();
                assertThat(rotation.distance()).isNotNull();
            });

    }
}