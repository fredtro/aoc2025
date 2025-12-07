package com.fredtro.day04;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class RemovableRollsCalculatorTest {


    @Test
    public void testCalculateA() {
        assertThat(RemovableRollsCalculator.getResultA()).isEqualTo(13);
    }

    @Test
    public void testCalculateB() {
        assertThat(RemovableRollsCalculator.getResultB()).isEqualTo(43);
    }
}