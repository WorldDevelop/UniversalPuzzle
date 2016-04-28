package pro.world_develop.universalpuzzle;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageView;

/**
 * Created by ildar on 25.04.2016.
 */
public class Puzzle extends ImageView {
    private static int puzzleCount;
    private static int countPuzzleOnPlace;

    private boolean canMove;
    private int realX;
    private int realY;

    public Puzzle(Context context, Bitmap image) {
        super(context);
        this.setImageBitmap(image);
        this.setOnTouchListener(new PuzzleOnTouchListener());
        this.setScaleType(ScaleType.FIT_XY);
        canMove = true;
    }

    public void setRealLocation(int x, int y) {
        realX = x;
        realY = y;
    }

    public int getRealX() {
        return realX;
    }

    public int getRealY() {
        return realY;
    }

    public boolean canMove() {
        return canMove;
    }

    public void canMove(boolean canMove) {
        this.canMove = canMove;
    }

    public static void setPuzzleCount(int puzzleCount) {
        Puzzle.puzzleCount = puzzleCount;
    }

    public static int getCountPuzzleOnPlace() {
        return countPuzzleOnPlace;
    }

    public static void setCountPuzzleOnPlace(int countPuzzleOnPlace) {
        Puzzle.countPuzzleOnPlace = countPuzzleOnPlace;
    }

    public boolean isOnPlace() {
        return Math.abs(getX() - realX) < 10 && Math.abs(getY() - realY) < 10;
    }

    public static boolean isEnd() {
        return puzzleCount == countPuzzleOnPlace;
    }
}
