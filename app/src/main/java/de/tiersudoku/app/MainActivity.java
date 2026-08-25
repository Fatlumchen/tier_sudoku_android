package de.tiersudoku.app;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Random;

public final class MainActivity extends Activity {
    private static final String[] ANIMALS = {"🐶", "🐱", "🐰", "🦊"};
    private static final String STATE_STARS = "stars";

    private final GameEngine engine = new GameEngine(new Random());
    private GameEngine.Puzzle puzzle;
    private GridLayout board;
    private TextView feedback;
    private TextView progress;
    private int stars;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        stars = savedInstanceState == null ? 0 : savedInstanceState.getInt(STATE_STARS);
        setContentView(createContent());
        startRound();
    }

    @Override
    protected void onSaveInstanceState(Bundle state) {
        state.putInt(STATE_STARS, stars);
        super.onSaveInstanceState(state);
    }

    private View createContent() {
        LinearLayout root = new LinearLayout(this);
        root.setOrientation(LinearLayout.VERTICAL);
        root.setGravity(Gravity.CENTER_HORIZONTAL);
        root.setPadding(dp(20), dp(28), dp(20), dp(20));
        root.setBackgroundColor(Color.rgb(255, 248, 231));

        TextView title = label(getString(R.string.headline), 32);
        title.setTextColor(Color.rgb(35, 74, 58));
        title.setTypeface(null, android.graphics.Typeface.BOLD);
        root.addView(title);

        progress = label("", 18);
        progress.setTextColor(Color.rgb(57, 118, 91));
        root.addView(progress, margins(dp(8)));

        TextView instruction = label(getString(R.string.instruction), 22);
        root.addView(instruction, margins(dp(12)));

        board = new GridLayout(this);
        board.setColumnCount(GameEngine.SIZE);
        board.setRowCount(GameEngine.SIZE);
        root.addView(board, margins(dp(8)));

        LinearLayout choices = new LinearLayout(this);
        choices.setGravity(Gravity.CENTER);
        for (int animal = 0; animal < ANIMALS.length; animal++) {
            final int choice = animal;
            Button button = new Button(this);
            button.setText(ANIMALS[animal]);
            button.setTextSize(29);
            button.setContentDescription(getString(R.string.animal_button, ANIMALS[animal]));
            button.setOnClickListener(view -> choose(choice));
            choices.addView(button, new LinearLayout.LayoutParams(dp(70), dp(64)));
        }
        root.addView(choices, margins(dp(14)));

        feedback = label("", 19);
        feedback.setGravity(Gravity.CENTER);
        root.addView(feedback, margins(dp(8)));

        Button next = new Button(this);
        next.setText(R.string.new_game);
        next.setOnClickListener(view -> startRound());
        root.addView(next, new LinearLayout.LayoutParams(dp(190), dp(56)));
        return root;
    }

    private void startRound() {
        puzzle = engine.nextPuzzle();
        feedback.setText("");
        progress.setText(getString(R.string.progress, stars));
        renderBoard();
    }

    private void renderBoard() {
        board.removeAllViews();
        for (int row = 0; row < GameEngine.SIZE; row++) {
            for (int column = 0; column < GameEngine.SIZE; column++) {
                boolean missing = row == puzzle.missingRow && column == puzzle.missingColumn;
                TextView cell = label(missing ? "?" : ANIMALS[puzzle.valueAt(row, column)], 28);
                cell.setGravity(Gravity.CENTER);
                cell.setBackgroundColor(missing ? Color.rgb(244, 124, 108) : Color.WHITE);
                GridLayout.LayoutParams params = new GridLayout.LayoutParams();
                params.width = dp(68);
                params.height = dp(68);
                params.setMargins(dp(3), dp(3), dp(3), dp(3));
                board.addView(cell, params);
            }
        }
    }

    private void choose(int animal) {
        if (puzzle.accepts(animal)) {
            stars++;
            feedback.setText(R.string.great);
            feedback.setTextColor(Color.rgb(57, 118, 91));
            progress.setText(getString(R.string.progress, stars));
            feedback.postDelayed(this::startRound, 900);
        } else {
            feedback.setText(R.string.try_again);
            feedback.setTextColor(Color.rgb(170, 67, 55));
        }
    }

    private TextView label(String text, int size) {
        TextView view = new TextView(this);
        view.setText(text);
        view.setTextSize(size);
        view.setGravity(Gravity.CENTER);
        return view;
    }

    private LinearLayout.LayoutParams margins(int vertical) {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        params.setMargins(0, vertical, 0, vertical);
        return params;
    }

    private int dp(int value) {
        return Math.round(value * getResources().getDisplayMetrics().density);
    }
}

