package com.fredtro.day07;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class DaySevenTest {

    @Test
    void getResultPartOne() {
        Assertions.assertThat(DaySeven.getResultPartOne()).isEqualTo(21);
    }
}