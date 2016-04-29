package pro.world_develop.universalpuzzle;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import pro.world_develop.universalpuzzle.activities.GameActivity;

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
        this.setOnTouchListener(new OnTouchListener() {
            //разница между касанием и координатами рисунка
            private int dragX = 0;
            private int dragY = 0;
            //координаты рисунка
            private int X;
            private int Y;

            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                Puzzle puzzle = (Puzzle) view;
                if (!puzzle.canMove()) return true;

                final int evX = (int) motionEvent.getRawX();
                final int evY = (int) motionEvent.getRawY();
                switch (motionEvent.getAction() ){
                    case MotionEvent.ACTION_DOWN:
                        FrameLayout frameLayout = (FrameLayout) puzzle.getParent();
                        frameLayout.removeView(puzzle);
                        frameLayout.addView(puzzle);

                        X = (int) puzzle.getX();
                        Y = (int) puzzle.getY();
                        dragX = evX - X;
                        dragY = evY - Y;
                        break;
                    case MotionEvent.ACTION_MOVE:
                        X = evX - dragX;
                        Y = evY - dragY;
                        puzzle.setX(X);
                        puzzle.setY(Y);
                        break;
                    case MotionEvent.ACTION_UP:
                        if (puzzle.isOnPlace()) {
                            fixPuzzle(puzzle);
                            if (Puzzle.isEnd()) showMsg();
                        }
                }
                return true;
            }

            private void fixPuzzle(Puzzle puzzle) {
                puzzle.setX(puzzle.getRealX());
                puzzle.setY(puzzle.getRealY());
                puzzle.canMove(false);
                Puzzle.setCountPuzzleOnPlace(Puzzle.getCountPuzzleOnPlace() + 1);
            }

            private void showMsg() {
                AlertDialog.Builder builder = new AlertDialog.Builder(GameActivity.getContext());
                builder.setTitle("Congratulations!").setMessage("You have collected puzzle!!!");
                AlertDialog alert = builder.create();
                alert.show();
            }
        });
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
