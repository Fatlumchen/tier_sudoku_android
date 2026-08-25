package de.tiersudoku.app;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/** Generates child-friendly 4x4 and classic 9x9 Sudoku puzzles. */
public final class ClassicSudokuEngine {
    public enum Difficulty {
        EASY(36), MEDIUM(46), HARD(52);

        private final int blanks;

        Difficulty(int blanks) {
            this.blanks = blanks;
        }
    }

    private final Random random;

    public ClassicSudokuEngine(Random random) {
        this.random = random;
    }

    public Puzzle nextPuzzle(int boxSize) {
        return nextPuzzle(boxSize, Difficulty.EASY);
    }

    public Puzzle nextPuzzle(int boxSize, Difficulty difficulty) {
        if (boxSize != 2 && boxSize != 3) {
            throw new IllegalArgumentException("Only 2x2 and 3x3 boxes are supported");
        }

        int size = boxSize * boxSize;
        int[][] base = new int[size][size];
        for (int row = 0; row < size; row++) {
            for (int column = 0; column < size; column++) {
                base[row][column] = (boxSize * (row % boxSize)
                        + row / boxSize + column) % size + 1;
            }
        }

        List<Integer> symbols = sequence(size);
        Collections.shuffle(symbols, random);
        List<Integer> rows = shuffledUnits(boxSize);
        List<Integer> columns = shuffledUnits(boxSize);
        int[][] solution = new int[size][size];
        for (int row = 0; row < size; row++) {
            for (int column = 0; column < size; column++) {
                solution[row][column] = symbols.get(base[rows.get(row)][columns.get(column)] - 1) + 1;
            }
        }

        int[][] values = copy(solution);
        List<Integer> positions = sequence(size * size);
        Collections.shuffle(positions, random);
        int targetBlanks = boxSize == 2 ? 6 : difficulty.blanks;
        int removed = 0;
        for (int position : positions) {
            if (removed == targetBlanks) {
                break;
            }
            int previous = values[position / size][position % size];
            values[position / size][position % size] = 0;
            if (countSolutions(values, boxSize, 2) == 1) {
                removed++;
            } else {
                values[position / size][position % size] = previous;
            }
        }
        return new Puzzle(boxSize, values, solution);
    }

    private List<Integer> shuffledUnits(int boxSize) {
        List<Integer> groups = sequence(boxSize);
        Collections.shuffle(groups, random);
        List<Integer> result = new ArrayList<>();
        for (int group : groups) {
            List<Integer> units = sequence(boxSize);
            Collections.shuffle(units, random);
            for (int unit : units) {
                result.add(group * boxSize + unit);
            }
        }
        return result;
    }

    private static List<Integer> sequence(int size) {
        List<Integer> result = new ArrayList<>();
        for (int value = 0; value < size; value++) {
            result.add(value);
        }
        return result;
    }

    private static int countSolutions(int[][] board, int boxSize, int limit) {
        int size = board.length;
        int targetRow = -1;
        int targetColumn = -1;
        int fewestCandidates = size + 1;
        for (int row = 0; row < size; row++) {
            for (int column = 0; column < size; column++) {
                if (board[row][column] != 0) {
                    continue;
                }
                int candidates = 0;
                for (int number = 1; number <= size; number++) {
                    if (canPlace(board, boxSize, row, column, number)) {
                        candidates++;
                    }
                }
                if (candidates < fewestCandidates) {
                    fewestCandidates = candidates;
                    targetRow = row;
                    targetColumn = column;
                }
            }
        }
        if (targetRow == -1) {
            return 1;
        }

        int solutions = 0;
        for (int number = 1; number <= size && solutions < limit; number++) {
            if (canPlace(board, boxSize, targetRow, targetColumn, number)) {
                board[targetRow][targetColumn] = number;
                solutions += countSolutions(board, boxSize, limit - solutions);
                board[targetRow][targetColumn] = 0;
            }
        }
        return solutions;
    }

    private static boolean canPlace(int[][] board, int boxSize, int row, int column, int number) {
        for (int index = 0; index < board.length; index++) {
            if (board[row][index] == number || board[index][column] == number) {
                return false;
            }
        }
        int firstRow = row / boxSize * boxSize;
        int firstColumn = column / boxSize * boxSize;
        for (int boxRow = 0; boxRow < boxSize; boxRow++) {
            for (int boxColumn = 0; boxColumn < boxSize; boxColumn++) {
                if (board[firstRow + boxRow][firstColumn + boxColumn] == number) {
                    return false;
                }
            }
        }
        return true;
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
