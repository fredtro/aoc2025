package com.fredtro.day06;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DaySixTest {

    @Test
    void getResultPartOne() {
        Assertions.assertThat(DaySix.getResultPartOne()).isEqualTo(4277556);
    }

    @Test
    void getResultPartTwo() {
    }
}