package de.tiersudoku.app;

import java.util.Random;

/** Creates small 4x4 Latin-square puzzles that children can solve visually. */
public final class GameEngine {
    public static final int SIZE = 4;

    private final Random random;

    public GameEngine(Random random) {
        this.random = random;
    }

    public Puzzle nextPuzzle() {
        int[][] solved = new int[SIZE][SIZE];
        int offset = random.nextInt(SIZE);
        for (int row = 0; row < SIZE; row++) {
            for (int column = 0; column < SIZE; column++) {
                solved[row][column] = (row + column + offset) % SIZE;
            }
        }
        int missingRow = random.nextInt(SIZE);
        int missingColumn = random.nextInt(SIZE);
        return new Puzzle(solved, missingRow, missingColumn);
    }

    public static final class Puzzle {
        private final int[][] solution;
        public final int missingRow;
        public final int missingColumn;

        private Puzzle(int[][] solution, int missingRow, int missingColumn) {
            this.solution = solution;
            this.missingRow = missingRow;
            this.missingColumn = missingColumn;
        }

        public int valueAt(int row, int column) {
            return solution[row][column];
        }

        public int answer() {
            return valueAt(missingRow, missingColumn);
        }

        public boolean accepts(int animal) {
            return animal == answer();
        }
    }
}

