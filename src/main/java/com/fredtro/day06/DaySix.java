package com.fredtro.day06;

import com.fredtro.util.FileReader;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.math.linear.MatrixUtils;
import org.apache.commons.math.linear.RealMatrix;

import java.math.BigInteger;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;

public class DaySix {

    private static final Pattern OPERATION_PATTERN = Pattern.compile("(\\S\\s*)");
    private static final Pattern VALUES_PATTERN = Pattern.compile("(\\d+)");

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
        List<String> lines = new ArrayList<>(FileReader.read("/day06/input.txt"));

        String lastLine = lines.removeLast();
        Matcher operationMatcher = OPERATION_PATTERN.matcher(lastLine);
        Map<Integer, ColumnMetadata> columnMetadata = getColumnMetadata(operationMatcher);

        Map<ColumnMetadata, List<ColumnValue>> columns = new HashMap<>();

        lines.forEach(line -> {
            Matcher valueMatcher = VALUES_PATTERN.matcher(line);
            int valueCount = 0;

            while (valueMatcher.find()) {
                var metadata = columnMetadata.get(valueCount);

                String value = valueMatcher.group();

                Alignment alignment = valueMatcher.start() == metadata.position() ? Alignment.LEFT : Alignment.RIGHT;
                var paddedValue = switch (alignment) {
                    case LEFT -> StringUtils.rightPad(value, metadata.columnSize(), " ");
                    case RIGHT -> StringUtils.leftPad(value, metadata.columnSize(), " ");
                };

                var columnValue = new ColumnValue(
                    paddedValue,
                    alignment
                );

                columns.computeIfAbsent(metadata, k -> new ArrayList<>()).add(columnValue);

                valueCount++;
            }

        });

        long result = 0;
        //
        for (Map.Entry<ColumnMetadata, List<ColumnValue>> entry : columns.entrySet()) {

            ColumnMetadata metadata = entry.getKey();
            List<ColumnValue> columnValues = entry.getValue();
            HashMap<Integer, StringBuilder> resultValues = new HashMap<>();

            for (var columnValue : columnValues) {

                String currentValue = columnValue.value();

                for (int i = 0; i < currentValue.length(); i++) {
                    resultValues.computeIfAbsent(i, k -> new StringBuilder()).append(currentValue.charAt(i));
                }
            }

            IntStream intStream = resultValues.values().stream().map(StringBuilder::toString)
                .map(StringUtils::strip)
                .map(Integer::parseInt)
                .mapToInt(Integer::intValue);

            int temp = switch (metadata.operation) {
                case SUM -> intStream.sum();
                case MINUS -> intStream.reduce(0, (a, b) -> a - b);
                case MULTIPLY -> intStream.reduce(1, (a, b) -> a * b);
                case DIVIDE -> intStream.reduce(1, (a, b) -> a / b);
            };

            result += temp;
        }

        return result;
    }

    private static Map<Integer, ColumnMetadata> getColumnMetadata(Matcher operationMatcher) {
        Map<Integer, ColumnMetadata> columnMetadata = new HashMap<>();

        int count = 0;
        while (operationMatcher.find()) {
            String group = operationMatcher.group();
            Operation operation = Operation.fromSign(group.charAt(0) + "");
            int columnSize = group.length() - 1;

            columnMetadata.put(count, new ColumnMetadata(operation, columnSize, operationMatcher.start()));
            count++;
        }
        return columnMetadata;
    }


    public static List<String> getLines() {
        return FileReader.parse("/day06/input.txt");
    }

    enum Alignment {
        LEFT,
        RIGHT
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

    record ColumnMetadata(Operation operation, int columnSize, int position) {
    }

    record ColumnValue(String value, Alignment alignment) {
    }
}
