package com.fredtro.day06;

import com.fredtro.util.FileReader;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.math.linear.MatrixUtils;
import org.apache.commons.math.linear.RealMatrix;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;

public class DaySix {

    public static double getResultPartOne() {

        List<List<String>> lines = getLines()
            .stream()
            .map(line -> Arrays.stream(line.split(" ")).filter(ObjectUtils::isNotEmpty).toList())
            .collect(Collectors.toList());

        // last line contains the operations
        List<Operation> operations = lines.removeLast()
            .stream()
            .map(Operation::fromSign)
            .toList();

        // crate a 2-dimensonal matrix from the numbers
        double[][] numbers = lines.stream()
            .map(row -> row.stream()
                .mapToDouble(Double::parseDouble)
                .toArray())
            .toArray(double[][]::new);
        RealMatrix matrix = MatrixUtils.createRealMatrix(numbers);

        double result = 0D;

        for (int col = 0; col < matrix.getColumnDimension(); col++) {
            double[] column = matrix.getColumn(col);

            Operation operation = operations.get(col);

            result += switch (operation) {
                case SUM -> DoubleStream.of(column).sum();
                case MINUS -> DoubleStream.of(column).reduce(0, (a, b) -> a - b);
                case MULTIPLY -> DoubleStream.of(column).reduce(1, (a, b) -> a * b);
                case DIVIDE -> DoubleStream.of(column).reduce(1, (a, b) -> a / b);
            };
        }

        return result;
    }

    public static long getResultPartTwo() {
        return 0;
    }

    public static List<String> getLines() {
        return FileReader.parse("/day06/input.txt");
    }

    enum Operation {
        SUM("+"), MINUS("-"), MULTIPLY("*"), DIVIDE("/");
        private final String sign;

        Operation(String sign) {
            this.sign = sign;
        }

        public static Operation fromSign(String sign) {
            return switch (sign) {
                case "+" -> SUM;
                case "-" -> MINUS;
                case "*" -> MULTIPLY;
                case "/" -> DIVIDE;
                default -> throw new IllegalStateException("Unexpected value: " + sign);
            };
        }

    }
}
