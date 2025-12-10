package com.fredtro.day05;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class DayFiveTest {

    @Test
    void test_getResultPartOne(){
        Assertions.assertThat(DayFive.getResultPartOne()).isEqualTo(3);
    }

    @Test
    void test_getResultPartTwo(){
        Assertions.assertThat(DayFive.getResultPartTwo()).isEqualTo(14);
    }
}