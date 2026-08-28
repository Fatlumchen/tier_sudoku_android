package de.fatljumneziri.tiersudoku;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import org.junit.Test;

public final class GameEngineTest {
    @Test
    public void everyRowAndColumnContainsEveryAnimalOnce() {
        GameEngine.Puzzle puzzle = new GameEngine(new Random(4)).nextPuzzle();
        for (int index = 0; index < GameEngine.SIZE; index++) {
            Set<Integer> row = new HashSet<>();
            Set<Integer> column = new HashSet<>();
            for (int position = 0; position < GameEngine.SIZE; position++) {
                row.add(puzzle.valueAt(index, position));
                column.add(puzzle.valueAt(position, index));
            }
            assertEquals(GameEngine.SIZE, row.size());
            assertEquals(GameEngine.SIZE, column.size());
        }
    }

    @Test
    public void onlyTheMissingAnimalIsAccepted() {
        GameEngine.Puzzle puzzle = new GameEngine(new Random(9)).nextPuzzle();
        assertTrue(puzzle.accepts(puzzle.answer()));
        assertFalse(puzzle.accepts((puzzle.answer() + 1) % GameEngine.SIZE));
    }
}

