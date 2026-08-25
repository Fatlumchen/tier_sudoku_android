package de.tiersudoku.app;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import org.junit.Test;

public final class ClassicSudokuEngineTest {
    @Test
    public void createsValidFourByFourAndNineByNineSolutions() {
        assertValid(new ClassicSudokuEngine(new Random(2)).nextPuzzle(2));
        assertValid(new ClassicSudokuEngine(new Random(3)).nextPuzzle(3));
    }

    @Test
    public void onlyCorrectNumbersFillBlankCells() {
        ClassicSudokuEngine.Puzzle puzzle = new ClassicSudokuEngine(new Random(8)).nextPuzzle(2);
        for (int row = 0; row < puzzle.size; row++) {
            for (int column = 0; column < puzzle.size; column++) {
                if (puzzle.isBlank(row, column)) {
                    int answer = puzzle.solutionAt(row, column);
                    assertFalse(puzzle.enter(row, column, answer % puzzle.size + 1));
                    assertTrue(puzzle.enter(row, column, answer));
                    assertEquals(answer, puzzle.valueAt(row, column));
                    return;
                }
            }
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void rejectsUnsupportedBoxSizes() {
        new ClassicSudokuEngine(new Random()).nextPuzzle(4);
    }

    private void assertValid(ClassicSudokuEngine.Puzzle puzzle) {
        for (int index = 0; index < puzzle.size; index++) {
            Set<Integer> row = new HashSet<>();
            Set<Integer> column = new HashSet<>();
            for (int position = 0; position < puzzle.size; position++) {
                row.add(puzzle.solutionAt(index, position));
                column.add(puzzle.solutionAt(position, index));
            }
            assertEquals(puzzle.size, row.size());
            assertEquals(puzzle.size, column.size());
        }

        for (int boxRow = 0; boxRow < puzzle.boxSize; boxRow++) {
            for (int boxColumn = 0; boxColumn < puzzle.boxSize; boxColumn++) {
                Set<Integer> box = new HashSet<>();
                for (int row = 0; row < puzzle.boxSize; row++) {
                    for (int column = 0; column < puzzle.boxSize; column++) {
                        box.add(puzzle.solutionAt(boxRow * puzzle.boxSize + row,
                                boxColumn * puzzle.boxSize + column));
                    }
                }
                assertEquals(puzzle.size, box.size());
            }
        }
    }
}
