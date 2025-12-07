package com.fredtro.day04;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class DayFourTest {

    @Test
    public void test_getResultPartOne() {
        assertThat(DayFour.getResultPartOne()).isEqualTo(13);
    }

    @Test
    public void test_getResultPartTwo() {
        assertThat(DayFour.getResultPartTwo()).isEqualTo(43);
    }
}