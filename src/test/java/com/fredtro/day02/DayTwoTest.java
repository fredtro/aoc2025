package com.fredtro.day02;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class DayTwoTest {

    @Test
    void getResultPartOne() {
        assertThat(DayTwo.getResultPartOne()).isEqualTo(1227775554L);
    }

    @Test
    void getResultPartTwo() {
        assertThat(DayTwo.getResultPartTwo()).isEqualTo(4174379265L);
    }
}