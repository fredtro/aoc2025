package com.fredtro.day03;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class DayThreeTest {

    @Test
    void getResultPartOne() {
        Assertions.assertThat(DayThree.getResultPartOne()).isEqualTo(357L);
    }

    @Test
    void getResultPartTwo() {
        Assertions.assertThat(DayThree.getResultPartTwo()).isEqualTo(3121910778619L);
    }
}