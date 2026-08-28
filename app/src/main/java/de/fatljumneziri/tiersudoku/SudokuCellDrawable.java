package de.fatljumneziri.tiersudoku;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;

/** Draws thin cell lines and clearly visible thick Sudoku-box boundaries. */
final class SudokuCellDrawable extends Drawable {
    private final Paint fillPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Paint linePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final int row;
    private final int column;
    private final int boardSize;
    private final int boxSize;
    private final float thinWidth;
    private final float thickWidth;

    SudokuCellDrawable(int fillColor, int lineColor, int row, int column,
            int boardSize, int boxSize, float density) {
        fillPaint.setColor(fillColor);
        linePaint.setColor(lineColor);
        linePaint.setStyle(Paint.Style.STROKE);
        this.row = row;
        this.column = column;
        this.boardSize = boardSize;
        this.boxSize = boxSize;
        thinWidth = Math.max(1f, density);
        thickWidth = Math.max(3f, density * 4f);
    }

    @Override
    public void draw(Canvas canvas) {
        float width = getBounds().width();
        float height = getBounds().height();
        canvas.drawRect(0, 0, width, height, fillPaint);

        drawVertical(canvas, 0, column % boxSize == 0 ? thickWidth : thinWidth, height);
        drawHorizontal(canvas, 0, row % boxSize == 0 ? thickWidth : thinWidth, width);
        if (column == boardSize - 1) {
            drawVertical(canvas, width, thickWidth, height);
        }
        if (row == boardSize - 1) {
            drawHorizontal(canvas, height, thickWidth, width);
        }
    }

    private void drawVertical(Canvas canvas, float x, float strokeWidth, float height) {
        linePaint.setStrokeWidth(strokeWidth);
        float inset = strokeWidth / 2f;
        float safeX = x == 0 ? inset : x - inset;
        canvas.drawLine(safeX, 0, safeX, height, linePaint);
    }

    private void drawHorizontal(Canvas canvas, float y, float strokeWidth, float width) {
        linePaint.setStrokeWidth(strokeWidth);
        float inset = strokeWidth / 2f;
        float safeY = y == 0 ? inset : y - inset;
        canvas.drawLine(0, safeY, width, safeY, linePaint);
    }

    @Override
    public void setAlpha(int alpha) {
        fillPaint.setAlpha(alpha);
        linePaint.setAlpha(alpha);
        invalidateSelf();
    }

    @Override
    public void setColorFilter(ColorFilter colorFilter) {
        fillPaint.setColorFilter(colorFilter);
        linePaint.setColorFilter(colorFilter);
        invalidateSelf();
    }

    @Override
    @SuppressWarnings("deprecation")
    public int getOpacity() {
        return PixelFormat.OPAQUE;
    }
}

