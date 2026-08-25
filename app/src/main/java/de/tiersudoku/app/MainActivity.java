package de.tiersudoku.app;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;
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
    private static final String PREFS_STATS = "game_stats";

    private final GameEngine animalEngine = new GameEngine(new Random());
    private final ClassicSudokuEngine classicEngine = new ClassicSudokuEngine(new Random());
    private int stars;
    private ClassicSudokuEngine.Puzzle classicPuzzle;
    private int selectedRow = -1;
    private int selectedColumn = -1;
    private GridLayout classicBoard;
    private TextView classicFeedback;
    private final Handler timerHandler = new Handler(Looper.getMainLooper());
    private TextView timerView;
    private TextView bestTimeView;
    private long timerStartedAt;
    private String timerKey;
    private boolean timerRunning;
    private final Runnable timerTick = new Runnable() {
        @Override
        public void run() {
            if (!timerRunning || timerView == null) {
                return;
            }
            timerView.setText(getString(R.string.current_time,
                    formatDuration(SystemClock.elapsedRealtime() - timerStartedAt)));
            timerHandler.postDelayed(this, 100);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setStatusBarColor(Color.rgb(25, 67, 54));
        getWindow().setNavigationBarColor(cream());
        stars = savedInstanceState == null ? 0 : savedInstanceState.getInt(STATE_STARS);
        showGameSelection();
    }

    @Override
    protected void onSaveInstanceState(Bundle state) {
        state.putInt(STATE_STARS, stars);
        super.onSaveInstanceState(state);
    }

    @Override
    protected void onDestroy() {
        stopTimerWithoutResult();
        super.onDestroy();
    }

    private void showGameSelection() {
        stopTimerWithoutResult();
        LinearLayout content = page();
        content.addView(badge(getString(R.string.app_badge)), margins(dp(4)));
        content.addView(title(getString(R.string.choose_game)), margins(dp(8)));
        content.addView(label(getString(R.string.choose_game_hint), 17), margins(dp(4)));
        content.addView(gameCard("🐾", getString(R.string.animal_sudoku),
                getString(R.string.animal_subtitle), view -> showAnimalGame()), margins(dp(20)));
        content.addView(gameCard("123", getString(R.string.classic_sudoku),
                getString(R.string.classic_subtitle), view -> showSizeSelection()), margins(dp(5)));
        setPage(content);
    }

    private void showSizeSelection() {
        stopTimerWithoutResult();
        LinearLayout content = page();
        content.addView(badge(getString(R.string.classic_sudoku)), margins(dp(4)));
        content.addView(title(getString(R.string.choose_size)), margins(dp(8)));
        content.addView(label(getString(R.string.size_explanation), 18), margins(dp(10)));
        content.addView(gameCard("4×4", getString(R.string.small_sudoku),
                getString(R.string.small_subtitle),
                view -> showClassicGame(2, ClassicSudokuEngine.Difficulty.EASY)), margins(dp(14)));
        content.addView(gameCard("9×9", getString(R.string.large_sudoku),
                getString(R.string.large_subtitle), view -> showDifficultySelection()), margins(dp(5)));
        content.addView(secondaryButton(getString(R.string.back), view -> showGameSelection()),
                margins(dp(18)));
        setPage(content);
    }

    private void showDifficultySelection() {
        stopTimerWithoutResult();
        LinearLayout content = page();
        content.addView(badge(getString(R.string.large_sudoku)), margins(dp(4)));
        content.addView(title(getString(R.string.choose_difficulty)), margins(dp(8)));
        content.addView(gameCard("★", getString(R.string.difficulty_easy),
                getString(R.string.difficulty_easy_hint),
                view -> showClassicGame(3, ClassicSudokuEngine.Difficulty.EASY)), margins(dp(14)));
        content.addView(gameCard("★★", getString(R.string.difficulty_medium),
                getString(R.string.difficulty_medium_hint),
                view -> showClassicGame(3, ClassicSudokuEngine.Difficulty.MEDIUM)), margins(dp(5)));
        content.addView(gameCard("★★★", getString(R.string.difficulty_hard),
                getString(R.string.difficulty_hard_hint),
                view -> showClassicGame(3, ClassicSudokuEngine.Difficulty.HARD)), margins(dp(5)));
        content.addView(secondaryButton(getString(R.string.back), view -> showSizeSelection()),
                margins(dp(18)));
        setPage(content);
    }

    private void showAnimalGame() {
        GameEngine.Puzzle puzzle = animalEngine.nextPuzzle();
        LinearLayout content = page();
        content.addView(title(getString(R.string.animal_sudoku)), margins(dp(3)));
        TextView progress = label(getString(R.string.progress, stars), 18);
        progress.setTextColor(green());
        progress.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        progress.setBackground(rounded(Color.rgb(230, 243, 235), dp(18), 0, 0));
        progress.setPadding(dp(18), dp(7), dp(18), dp(7));
        content.addView(progress, margins(dp(6)));
        content.addView(timerPanel(), margins(dp(4)));
        content.addView(label(getString(R.string.instruction), 22), margins(dp(8)));

        GridLayout board = new GridLayout(this);
        board.setColumnCount(GameEngine.SIZE);
        board.setPadding(dp(7), dp(7), dp(7), dp(7));
        board.setBackground(rounded(Color.WHITE, dp(18), 0, 0));
        board.setElevation(dp(5));
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
        boolean[] solved = {false};
        LinearLayout choices = new LinearLayout(this);
        choices.setGravity(Gravity.CENTER);
        for (int animal = 0; animal < ANIMALS.length; animal++) {
            final int choice = animal;
            Button button = new Button(this);
            button.setText(ANIMALS[animal]);
            button.setTextSize(29);
            button.setAllCaps(false);
            button.setBackground(buttonSelector(Color.WHITE, Color.rgb(242, 247, 244), dp(16)));
            button.setElevation(dp(2));
            button.setContentDescription(getString(R.string.animal_button, ANIMALS[animal]));
            button.setOnClickListener(view -> {
                if (puzzle.accepts(choice) && !solved[0]) {
                    solved[0] = true;
                    stars++;
                    finishTimer();
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
        content.addView(secondaryButton(getString(R.string.back_to_selection), view -> showGameSelection()),
                margins(dp(5)));
        setPage(content);
        startTimer("animal");
    }

    private void showClassicGame(int boxSize, ClassicSudokuEngine.Difficulty difficulty) {
        classicPuzzle = classicEngine.nextPuzzle(boxSize, difficulty);
        selectedRow = -1;
        selectedColumn = -1;
        LinearLayout content = page();
        String difficultyName = difficultyName(difficulty);
        content.addView(title(getString(R.string.classic_title_with_difficulty,
                classicPuzzle.size, difficultyName)), margins(dp(3)));
        content.addView(timerPanel(), margins(dp(4)));
        content.addView(label(getString(R.string.classic_instruction), 17), margins(dp(5)));
        classicBoard = new GridLayout(this);
        classicBoard.setColumnCount(classicPuzzle.size);
        classicBoard.setElevation(dp(6));
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
            button.setTextColor(Color.WHITE);
            button.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
            button.setBackground(buttonSelector(green(), Color.rgb(31, 84, 65), dp(14)));
            button.setOnClickListener(view -> enterClassicNumber(choice));
            numbers.addView(button, new LinearLayout.LayoutParams(dp(58), dp(52)));
        }
        content.addView(numbers, margins(dp(5)));
        content.addView(actionButton(getString(R.string.new_game),
                view -> showClassicGame(boxSize, difficulty)),
                margins(dp(6)));
        content.addView(secondaryButton(getString(R.string.back_to_selection), view -> showGameSelection()),
                margins(dp(4)));
        setPage(content);
        renderClassicBoard();
        String timerStatsKey = boxSize == 2 ? "classic_2"
                : "classic_3_" + difficulty.name().toLowerCase(java.util.Locale.ROOT);
        startTimer(timerStatsKey);
    }

    private String difficultyName(ClassicSudokuEngine.Difficulty difficulty) {
        switch (difficulty) {
            case MEDIUM:
                return getString(R.string.difficulty_medium);
            case HARD:
                return getString(R.string.difficulty_hard);
            case EASY:
            default:
                return getString(R.string.difficulty_easy);
        }
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
            if (classicPuzzle.isComplete()) {
                finishTimer();
            }
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
        content.setPadding(dp(20), dp(28), dp(20), dp(32));
        GradientDrawable background = new GradientDrawable(
                GradientDrawable.Orientation.TOP_BOTTOM,
                new int[]{Color.rgb(247, 252, 249), cream()});
        content.setBackground(background);
        return content;
    }

    private void setPage(LinearLayout content) {
        ScrollView scroll = new ScrollView(this);
        scroll.setFillViewport(true);
        scroll.addView(content);
        setContentView(scroll);
        content.setAlpha(0f);
        content.setTranslationY(dp(8));
        content.animate().alpha(1f).translationY(0f).setDuration(220).start();
    }

    private TextView title(String text) {
        TextView title = label(text, 31);
        title.setTextColor(Color.rgb(35, 74, 58));
        title.setTypeface(Typeface.create("sans-serif", Typeface.BOLD));
        return title;
    }

    private Button actionButton(String text, View.OnClickListener listener) {
        Button button = new Button(this);
        button.setText(text);
        button.setTextSize(17);
        button.setTextColor(Color.WHITE);
        button.setAllCaps(false);
        button.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        button.setBackground(buttonSelector(green(), Color.rgb(31, 84, 65), dp(16)));
        button.setElevation(dp(3));
        button.setOnClickListener(listener);
        button.setMinWidth(dp(230));
        button.setMinHeight(dp(54));
        return button;
    }

    private Button secondaryButton(String text, View.OnClickListener listener) {
        Button button = actionButton(text, listener);
        button.setTextColor(green());
        button.setElevation(0);
        button.setBackground(buttonSelector(Color.TRANSPARENT,
                Color.rgb(230, 243, 235), dp(16), green(), dp(1)));
        return button;
    }

    private View gameCard(String icon, String heading, String subtitle, View.OnClickListener listener) {
        LinearLayout card = new LinearLayout(this);
        card.setOrientation(LinearLayout.HORIZONTAL);
        card.setGravity(Gravity.CENTER_VERTICAL);
        card.setPadding(dp(18), dp(17), dp(18), dp(17));
        card.setBackground(buttonSelector(Color.WHITE, Color.rgb(239, 248, 243), dp(22)));
        card.setElevation(dp(5));
        card.setClickable(true);
        card.setFocusable(true);
        card.setOnClickListener(listener);
        card.setContentDescription(heading + ". " + subtitle);

        TextView iconView = label(icon, icon.length() > 3 ? 22 : 29);
        iconView.setTextColor(green());
        iconView.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        iconView.setGravity(Gravity.CENTER);
        iconView.setBackground(rounded(Color.rgb(230, 243, 235), dp(18), 0, 0));
        card.addView(iconView, new LinearLayout.LayoutParams(dp(70), dp(70)));

        LinearLayout copy = new LinearLayout(this);
        copy.setOrientation(LinearLayout.VERTICAL);
        copy.setPadding(dp(16), 0, dp(6), 0);
        TextView headingView = label(heading, 20);
        headingView.setGravity(Gravity.START);
        headingView.setTextColor(Color.rgb(28, 58, 48));
        headingView.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        TextView subtitleView = label(subtitle, 14);
        subtitleView.setGravity(Gravity.START);
        subtitleView.setTextColor(Color.rgb(86, 105, 97));
        copy.addView(headingView);
        copy.addView(subtitleView, margins(dp(2)));
        card.addView(copy, new LinearLayout.LayoutParams(0,
                LinearLayout.LayoutParams.WRAP_CONTENT, 1f));

        TextView arrow = label("›", 34);
        arrow.setTextColor(coral());
        card.addView(arrow);
        card.setMinimumWidth(dp(280));
        return card;
    }

    private View timerPanel() {
        LinearLayout panel = new LinearLayout(this);
        panel.setOrientation(LinearLayout.HORIZONTAL);
        panel.setGravity(Gravity.CENTER);
        panel.setPadding(dp(5), dp(5), dp(5), dp(5));
        panel.setBackground(rounded(Color.WHITE, dp(18), 0, 0));
        panel.setElevation(dp(2));

        timerView = statLabel(getString(R.string.current_time, "00:00.0"));
        bestTimeView = statLabel(getString(R.string.best_time, "–"));
        panel.addView(timerView);
        panel.addView(bestTimeView);
        return panel;
    }

    private TextView statLabel(String text) {
        TextView view = label(text, 14);
        view.setTextColor(Color.rgb(35, 74, 58));
        view.setTypeface(Typeface.create("sans-serif-medium", Typeface.NORMAL));
        view.setPadding(dp(14), dp(8), dp(14), dp(8));
        return view;
    }

    private void startTimer(String key) {
        stopTimerWithoutResult();
        timerKey = key;
        timerStartedAt = SystemClock.elapsedRealtime();
        timerRunning = true;
        long best = getSharedPreferences(PREFS_STATS, MODE_PRIVATE).getLong(key, 0);
        bestTimeView.setText(getString(R.string.best_time, best == 0 ? "–" : formatDuration(best)));
        timerTick.run();
    }

    private void finishTimer() {
        if (!timerRunning) {
            return;
        }
        long elapsed = SystemClock.elapsedRealtime() - timerStartedAt;
        timerRunning = false;
        timerHandler.removeCallbacks(timerTick);
        timerView.setText(getString(R.string.current_time, formatDuration(elapsed)));

        long best = getSharedPreferences(PREFS_STATS, MODE_PRIVATE).getLong(timerKey, 0);
        if (best == 0 || elapsed < best) {
            best = elapsed;
            getSharedPreferences(PREFS_STATS, MODE_PRIVATE).edit().putLong(timerKey, best).apply();
            bestTimeView.setText(getString(R.string.new_best_time, formatDuration(best)));
        } else {
            bestTimeView.setText(getString(R.string.best_time, formatDuration(best)));
        }
    }

    private void stopTimerWithoutResult() {
        timerRunning = false;
        timerHandler.removeCallbacks(timerTick);
    }

    private String formatDuration(long milliseconds) {
        long minutes = milliseconds / 60_000;
        long seconds = milliseconds / 1_000 % 60;
        long tenths = milliseconds / 100 % 10;
        return String.format(java.util.Locale.GERMANY, "%02d:%02d.%d", minutes, seconds, tenths);
    }

    private TextView badge(String text) {
        TextView badge = label(text.toUpperCase(), 12);
        badge.setTextColor(green());
        badge.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        badge.setLetterSpacing(0.08f);
        badge.setPadding(dp(14), dp(6), dp(14), dp(6));
        badge.setBackground(rounded(Color.rgb(230, 243, 235), dp(20), 0, 0));
        return badge;
    }

    private StateListDrawable buttonSelector(int normal, int pressed, int radius) {
        return buttonSelector(normal, pressed, radius, Color.TRANSPARENT, 0);
    }

    private StateListDrawable buttonSelector(int normal, int pressed, int radius,
            int strokeColor, int strokeWidth) {
        StateListDrawable selector = new StateListDrawable();
        selector.addState(new int[]{android.R.attr.state_pressed},
                rounded(pressed, radius, strokeColor, strokeWidth));
        selector.addState(new int[]{}, rounded(normal, radius, strokeColor, strokeWidth));
        return selector;
    }

    private GradientDrawable rounded(int color, int radius, int strokeColor, int strokeWidth) {
        GradientDrawable drawable = new GradientDrawable();
        drawable.setColor(color);
        drawable.setCornerRadius(radius);
        if (strokeWidth > 0) {
            drawable.setStroke(strokeWidth, strokeColor);
        }
        return drawable;
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

    private int cream() {
        return Color.rgb(255, 248, 231);
    }

    private int dp(int value) {
        return Math.round(value * getResources().getDisplayMetrics().density);
    }
}
