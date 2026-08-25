package de.tiersudoku.app;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.Random;

public final class MainActivity extends Activity {
    private static final String[] ANIMALS = {"🐶", "🐱", "🐰", "🦊"};
    private static final String STATE_STARS = "stars";

    private final GameEngine animalEngine = new GameEngine(new Random());
    private final ClassicSudokuEngine classicEngine = new ClassicSudokuEngine(new Random());
    private int stars;
    private ClassicSudokuEngine.Puzzle classicPuzzle;
    private int selectedRow = -1;
    private int selectedColumn = -1;
    private GridLayout classicBoard;
    private TextView classicFeedback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        stars = savedInstanceState == null ? 0 : savedInstanceState.getInt(STATE_STARS);
        showGameSelection();
    }

    @Override
    protected void onSaveInstanceState(Bundle state) {
        state.putInt(STATE_STARS, stars);
        super.onSaveInstanceState(state);
    }

    private void showGameSelection() {
        LinearLayout content = page();
        content.addView(title(getString(R.string.choose_game)));
        content.addView(label(getString(R.string.choose_game_hint), 18), margins(dp(8)));
        content.addView(actionButton(getString(R.string.animal_sudoku), view -> showAnimalGame()),
                margins(dp(18)));
        content.addView(actionButton(getString(R.string.classic_sudoku), view -> showSizeSelection()),
                margins(dp(8)));
        setPage(content);
    }

    private void showSizeSelection() {
        LinearLayout content = page();
        content.addView(title(getString(R.string.choose_size)));
        content.addView(label(getString(R.string.size_explanation), 18), margins(dp(10)));
        content.addView(actionButton(getString(R.string.small_sudoku), view -> showClassicGame(2)),
                margins(dp(14)));
        content.addView(actionButton(getString(R.string.large_sudoku), view -> showClassicGame(3)),
                margins(dp(8)));
        content.addView(actionButton(getString(R.string.back), view -> showGameSelection()),
                margins(dp(18)));
        setPage(content);
    }

    private void showAnimalGame() {
        GameEngine.Puzzle puzzle = animalEngine.nextPuzzle();
        LinearLayout content = page();
        content.addView(title(getString(R.string.animal_sudoku)));
        TextView progress = label(getString(R.string.progress, stars), 18);
        content.addView(progress, margins(dp(6)));
        content.addView(label(getString(R.string.instruction), 22), margins(dp(8)));

        GridLayout board = new GridLayout(this);
        board.setColumnCount(GameEngine.SIZE);
        for (int row = 0; row < GameEngine.SIZE; row++) {
            for (int column = 0; column < GameEngine.SIZE; column++) {
                boolean missing = row == puzzle.missingRow && column == puzzle.missingColumn;
                TextView cell = label(missing ? "?" : ANIMALS[puzzle.valueAt(row, column)], 28);
                cell.setGravity(Gravity.CENTER);
                cell.setBackgroundColor(missing ? coral() : Color.WHITE);
                board.addView(cell, cellParams(dp(68), row, column, 2));
            }
        }
        content.addView(board, margins(dp(8)));

        TextView feedback = label("", 19);
        LinearLayout choices = new LinearLayout(this);
        choices.setGravity(Gravity.CENTER);
        for (int animal = 0; animal < ANIMALS.length; animal++) {
            final int choice = animal;
            Button button = new Button(this);
            button.setText(ANIMALS[animal]);
            button.setTextSize(29);
            button.setContentDescription(getString(R.string.animal_button, ANIMALS[animal]));
            button.setOnClickListener(view -> {
                if (puzzle.accepts(choice)) {
                    stars++;
                    feedback.setText(R.string.great);
                    feedback.setTextColor(green());
                    feedback.postDelayed(this::showAnimalGame, 900);
                } else {
                    feedback.setText(R.string.try_again);
                    feedback.setTextColor(Color.rgb(170, 67, 55));
                }
            });
            choices.addView(button, new LinearLayout.LayoutParams(dp(70), dp(64)));
        }
        content.addView(choices, margins(dp(10)));
        content.addView(feedback, margins(dp(4)));
        content.addView(actionButton(getString(R.string.new_game), view -> showAnimalGame()), margins(dp(8)));
        content.addView(actionButton(getString(R.string.back_to_selection), view -> showGameSelection()),
                margins(dp(5)));
        setPage(content);
    }

    private void showClassicGame(int boxSize) {
        classicPuzzle = classicEngine.nextPuzzle(boxSize);
        selectedRow = -1;
        selectedColumn = -1;
        LinearLayout content = page();
        content.addView(title(getString(R.string.classic_title, classicPuzzle.size)));
        content.addView(label(getString(R.string.classic_instruction), 17), margins(dp(5)));
        classicBoard = new GridLayout(this);
        classicBoard.setColumnCount(classicPuzzle.size);
        content.addView(classicBoard, margins(dp(6)));
        classicFeedback = label(getString(R.string.select_cell), 17);
        content.addView(classicFeedback, margins(dp(4)));

        GridLayout numbers = new GridLayout(this);
        numbers.setColumnCount(Math.min(classicPuzzle.size, 5));
        for (int number = 1; number <= classicPuzzle.size; number++) {
            final int choice = number;
            Button button = new Button(this);
            button.setText(String.valueOf(number));
            button.setTextSize(18);
            button.setOnClickListener(view -> enterClassicNumber(choice));
            numbers.addView(button, new LinearLayout.LayoutParams(dp(58), dp(52)));
        }
        content.addView(numbers, margins(dp(5)));
        content.addView(actionButton(getString(R.string.new_game), view -> showClassicGame(boxSize)),
                margins(dp(6)));
        content.addView(actionButton(getString(R.string.back_to_selection), view -> showGameSelection()),
                margins(dp(4)));
        setPage(content);
        renderClassicBoard();
    }

    private void renderClassicBoard() {
        classicBoard.removeAllViews();
        int available = getResources().getDisplayMetrics().widthPixels - dp(32);
        int cellSize = Math.min(dp(58), available / classicPuzzle.size);
        for (int row = 0; row < classicPuzzle.size; row++) {
            for (int column = 0; column < classicPuzzle.size; column++) {
                final int cellRow = row;
                final int cellColumn = column;
                int value = classicPuzzle.valueAt(row, column);
                Button cell = new Button(this);
                cell.setText(value == 0 ? "" : String.valueOf(value));
                cell.setTextSize(classicPuzzle.size == 9 ? 14 : 20);
                cell.setPadding(0, 0, 0, 0);
                boolean selected = row == selectedRow && column == selectedColumn;
                int fillColor = selected ? coral() : value == 0
                        ? Color.WHITE : Color.rgb(220, 235, 225);
                cell.setBackground(new SudokuCellDrawable(fillColor, Color.rgb(35, 74, 58),
                        row, column, classicPuzzle.size, classicPuzzle.boxSize,
                        getResources().getDisplayMetrics().density));
                cell.setEnabled(value == 0);
                cell.setOnClickListener(view -> {
                    selectedRow = cellRow;
                    selectedColumn = cellColumn;
                    classicFeedback.setText(R.string.choose_number);
                    renderClassicBoard();
                });
                GridLayout.LayoutParams params = new GridLayout.LayoutParams();
                params.width = cellSize;
                params.height = cellSize;
                classicBoard.addView(cell, params);
            }
        }
    }

    private void enterClassicNumber(int number) {
        if (selectedRow < 0) {
            classicFeedback.setText(R.string.select_cell_first);
            return;
        }
        if (classicPuzzle.enter(selectedRow, selectedColumn, number)) {
            selectedRow = -1;
            selectedColumn = -1;
            classicFeedback.setText(classicPuzzle.isComplete() ? R.string.sudoku_complete : R.string.correct);
            classicFeedback.setTextColor(green());
            renderClassicBoard();
        } else {
            classicFeedback.setText(R.string.wrong_number);
            classicFeedback.setTextColor(Color.rgb(170, 67, 55));
        }
    }

    private LinearLayout page() {
        LinearLayout content = new LinearLayout(this);
        content.setOrientation(LinearLayout.VERTICAL);
        content.setGravity(Gravity.CENTER_HORIZONTAL);
        content.setPadding(dp(16), dp(24), dp(16), dp(24));
        content.setBackgroundColor(Color.rgb(255, 248, 231));
        return content;
    }

    private void setPage(LinearLayout content) {
        ScrollView scroll = new ScrollView(this);
        scroll.setFillViewport(true);
        scroll.addView(content);
        setContentView(scroll);
    }

    private TextView title(String text) {
        TextView title = label(text, 30);
        title.setTextColor(Color.rgb(35, 74, 58));
        title.setTypeface(null, android.graphics.Typeface.BOLD);
        return title;
    }

    private Button actionButton(String text, View.OnClickListener listener) {
        Button button = new Button(this);
        button.setText(text);
        button.setTextSize(17);
        button.setOnClickListener(listener);
        button.setMinWidth(dp(230));
        return button;
    }

    private TextView label(String text, int size) {
        TextView view = new TextView(this);
        view.setText(text);
        view.setTextSize(size);
        view.setGravity(Gravity.CENTER);
        return view;
    }

    private GridLayout.LayoutParams cellParams(int size, int row, int column, int boxSize) {
        GridLayout.LayoutParams params = new GridLayout.LayoutParams();
        params.width = size;
        params.height = size;
        params.setMargins(column % boxSize == 0 ? dp(2) : dp(1),
                row % boxSize == 0 ? dp(2) : dp(1), dp(1), dp(1));
        return params;
    }

    private LinearLayout.LayoutParams margins(int vertical) {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        params.setMargins(0, vertical, 0, vertical);
        return params;
    }

    private int green() {
        return Color.rgb(57, 118, 91);
    }

    private int coral() {
        return Color.rgb(244, 124, 108);
    }

    private int dp(int value) {
        return Math.round(value * getResources().getDisplayMetrics().density);
    }
}
