package de.tiersudoku.app;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/** Generates child-friendly 4x4 and classic 9x9 Sudoku puzzles. */
public final class ClassicSudokuEngine {
    private final Random random;

    public ClassicSudokuEngine(Random random) {
        this.random = random;
    }

    public Puzzle nextPuzzle(int boxSize) {
        if (boxSize != 2 && boxSize != 3) {
            throw new IllegalArgumentException("Only 2x2 and 3x3 boxes are supported");
        }

        int size = boxSize * boxSize;
        int[][] solution = new int[size][size];
        int shift = random.nextInt(size);
        for (int row = 0; row < size; row++) {
            for (int column = 0; column < size; column++) {
                solution[row][column] = (boxSize * (row % boxSize)
                        + row / boxSize + column + shift) % size + 1;
            }
        }

        int[][] values = copy(solution);
        List<Integer> positions = new ArrayList<>();
        for (int position = 0; position < size * size; position++) {
            positions.add(position);
        }
        Collections.shuffle(positions, random);
        int blanks = boxSize == 2 ? 6 : 30;
        for (int index = 0; index < blanks; index++) {
            int position = positions.get(index);
            values[position / size][position % size] = 0;
        }
        return new Puzzle(boxSize, values, solution);
    }

    private static int[][] copy(int[][] source) {
        int[][] result = new int[source.length][];
        for (int row = 0; row < source.length; row++) {
            result[row] = source[row].clone();
        }
        return result;
    }

    public static final class Puzzle {
        public final int boxSize;
        public final int size;
        private final int[][] values;
        private final int[][] solution;

        private Puzzle(int boxSize, int[][] values, int[][] solution) {
            this.boxSize = boxSize;
            this.size = boxSize * boxSize;
            this.values = values;
            this.solution = solution;
        }

        public int valueAt(int row, int column) {
            return values[row][column];
        }

        public boolean isBlank(int row, int column) {
            return valueAt(row, column) == 0;
        }

        public boolean enter(int row, int column, int number) {
            if (!isBlank(row, column) || number != solution[row][column]) {
                return false;
            }
            values[row][column] = number;
            return true;
        }

        public boolean isComplete() {
            for (int[] row : values) {
                for (int value : row) {
                    if (value == 0) {
                        return false;
                    }
                }
            }
            return true;
        }

        int solutionAt(int row, int column) {
            return solution[row][column];
        }
    }
}

