package com.fredtro.day01;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class DayOneTest {

    private final DayOne dayOne = new DayOne();

    @Test
    void test_getResultPartOne() {
        assertThat(dayOne.getResultPartOne()).isEqualTo(3);
    }

    @Test
    void test_getResultPartTwo() {
        assertThat(dayOne.getResultPartTwo()).isEqualTo(6);
    }
}