package com.fredtro.day04;

import com.fredtro.util.FileReader;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class DayFour {

    public static int getResultPartOne() {
        var lines = getLines().toArray(char[][]::new);
        return getRemovableRolls(lines).count();
    }

    public static int getResultPartTwo() {
        char[][] grid = getLines().toArray(char[][]::new);

        RemovedRollsFromGrid removedRollsFromGrid = new RemovedRollsFromGrid(0, grid);
        int rollsRemovedTotal = 0;
        do {
            removedRollsFromGrid = getRemovableRolls(removedRollsFromGrid.grid);
            rollsRemovedTotal += removedRollsFromGrid.count;
        } while (removedRollsFromGrid.count != 0);

        return rollsRemovedTotal;
    }

    private static RemovedRollsFromGrid getRemovableRolls(char[][] grid) {
        int rowSize = grid[0].length;
        int count = 0;
        char[][] modifiedGrid = Arrays.stream(grid).map(char[]::clone).toArray(char[][]::new);

        for (int i = 0; i < grid.length; i++) {

            char[] line = grid[i];
            for (int j = 0; j < line.length; j++) {
                if (!isRoll(line[j])) continue;

                Position currentPosition = new Position(i, j);
                long neighbourCount = getNeighbourCount(currentPosition, grid, rowSize);
                if (neighbourCount < 4) {
                    count++;
                    modifiedGrid[i][j] = '.';
                }
            }
        }

        return new RemovedRollsFromGrid(count, modifiedGrid);
    }


    private static long getNeighbourCount(Position currentPosition, char[][] grid, int rowSize) {

        int currentRow = currentPosition.rowNumber;
        int currentColumn = currentPosition.index;

        return Stream.of(
                new Position(currentRow - 1, currentColumn - 1),
                new Position(currentRow - 1, currentColumn),
                new Position(currentRow - 1, currentColumn + 1),
                new Position(currentRow, currentColumn - 1),
                new Position(currentRow, currentColumn + 1),
                new Position(currentRow + 1, currentColumn - 1),
                new Position(currentRow + 1, currentColumn),
                new Position(currentRow + 1, currentColumn + 1)
            )
            .filter(p -> isValidPosition(p, grid))
            .map(p -> getCharAtPosition(p, grid))
            .filter(DayFour::isRoll)
            .count();
    }

    private static boolean isValidPosition(Position position, char[][] grid) {
        int rowSize = grid[0].length;
        return position.rowNumber >= 0 && position.rowNumber <= grid.length - 1
            && position.index >= 0 && position.index <= rowSize - 1;
    }

    private static Character getCharAtPosition(Position position, char[][] grid) {
        try {
            return grid[position.rowNumber][position.index];
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Invalid position: " + position);
            return null;
        }

    }

    private static boolean isRoll(Character c) {
        return c == '@';
    }

    private static List<char[]> getLines() {
        return FileReader.parse("/day04/input.txt", String::toCharArray);
    }

    record RemovedRollsFromGrid(int count, char[][] grid) {
    }

    record Position(int rowNumber, int index) {
    }
}
