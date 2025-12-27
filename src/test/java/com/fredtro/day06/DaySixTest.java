package com.fredtro.day06;

import com.fredtro.util.FileReader;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class DaySixTest {

    @Test
    void getResultPartOne() {
        Assertions.assertThat(DaySix.getResultPartOne()).isEqualTo(4277556D);
    }

    @Test
    void getResultPartTwo() {
        Assertions.assertThat(DaySix.getResultPartTwo()).isEqualTo(3263827);
    }

    @Test
    void transpose() {
        char[][] matrix = new char[][]{
            {'6', '4', ' '},
            {'2', '3', ' '},
            {'3', '1', '4'}
        };

        char[][] result = new char[matrix[0].length][3];

        for (int i = 0; i < matrix.length; i++) {

            char[] row = matrix[i];

            for (int j = 0; j < row.length; j++) {
                result[j][i] = row[j];
            }
        }

        System.out.println(Arrays.asList(result));



    }
}