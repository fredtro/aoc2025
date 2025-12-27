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
import java.util.stream.LongStream;

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
            .map(s -> s.charAt(0))
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
                case MULTIPLY -> DoubleStream.of(column).reduce(1, (a, b) -> a * b);
            };
        }

        return result;
    }

    public static BigInteger getResultPartTwo() {
        List<String> lines = new ArrayList<>(FileReader.read("/day06/input.txt"));

        String lastLine = lines.removeLast();
        List<ColumnMetadata> columnMetadata = getColumnMetadata(lastLine);

        List<List<ColumnValue>> columns = getColumnValues(lines, columnMetadata);

        BigInteger result = BigInteger.ZERO;

        for (int i = 0; i < columns.size(); i++) {

            ColumnMetadata metadata = columnMetadata.get(i);
            List<ColumnValue> columnValues = columns.get(i);
            HashMap<Integer, StringBuilder> resultValues = new HashMap<>();

            for (var columnValue : columnValues) {
                String currentValue = columnValue.value();

                for (int j = 0; j < currentValue.length(); j++) {
                    resultValues.computeIfAbsent(j, k -> new StringBuilder()).append(currentValue.charAt(j));
                }
            }

            LongStream longStream = resultValues.values().stream().map(StringBuilder::toString)
                .map(StringUtils::strip)
                .map(Long::parseLong)
                .mapToLong(Long::longValue);

            long temp = switch (metadata.operation) {
                case SUM -> longStream.sum();
                case MULTIPLY -> longStream.reduce(1, (a, b) -> a * b);
            };

            result = result.add(BigInteger.valueOf(temp));
        }
        
        return result;
    }

    private static List<List<ColumnValue>> getColumnValues(List<String> lines, List<ColumnMetadata> columnMetadata) {
        List<List<ColumnValue>> columns = new ArrayList<>();

        for (String line : lines) {

            Matcher valueMatcher = VALUES_PATTERN.matcher(line);

            int columnNumber = 0;
            while (valueMatcher.find()) {
                var metadata = columnMetadata.get(columnNumber);

                String value = valueMatcher.group();

                Alignment alignment = valueMatcher.start() == metadata.position() ? Alignment.LEFT : Alignment.RIGHT;
                var paddedValue = switch (alignment) {
                    case LEFT -> StringUtils.rightPad(value, metadata.columnSize(), " ");
                    case RIGHT -> StringUtils.leftPad(value, metadata.columnSize(), " ");
                };

                var columnValue = new ColumnValue(paddedValue);

                if (columnNumber >= columns.size()) {
                    columns.add(new ArrayList<>());
                }
                columns.get(columnNumber).add(columnValue);

                columnNumber++;
            }

        }

        return columns;
    }

    private static List<ColumnMetadata> getColumnMetadata(String lastLine) {
        Matcher operationMatcher = OPERATION_PATTERN.matcher(lastLine);
        List<ColumnMetadata> columnMetadata = new ArrayList<>();

        while (operationMatcher.find()) {
            String group = operationMatcher.group();
            Operation operation = Operation.fromSign(group.charAt(0));
            int columnSize = group.length() - 1;

            columnMetadata.add(new ColumnMetadata(operation, columnSize, operationMatcher.start()));
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
        SUM, MULTIPLY;

        public static Operation fromSign(char sign) {
            return switch (sign) {
                case '+' -> SUM;
                case '*' -> MULTIPLY;
                default -> throw new IllegalStateException("Unexpected value: " + sign);
            };
        }

    }

    record ColumnMetadata(Operation operation, int columnSize, int position) {
    }

    record ColumnValue(String value) {
    }
}
