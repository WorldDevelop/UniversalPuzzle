package pro.world_develop.universalpuzzle;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.media.MediaPlayer;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import java.util.List;

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
    private Field parentField;
    private Layer parentLayer;
    private int i, j;

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
            private List<Puzzle> puzzleList;

            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                Puzzle puzzle = (Puzzle) view;
                Layer layer = puzzle.getParentLayer();

                if (!layer.isCanMove()) return true;

                final int evX = (int) motionEvent.getRawX();
                final int evY = (int) motionEvent.getRawY();
                switch (motionEvent.getAction() ){
                    case MotionEvent.ACTION_DOWN:
                        FrameLayout frameLayout = (FrameLayout) layer.getParent();
                        frameLayout.removeView(layer);
                        frameLayout.addView(layer);

                        X = (int) layer.getX();
                        Y = (int) layer.getY();
                        dragX = evX - X;
                        dragY = evY - Y;
                        break;
                    case MotionEvent.ACTION_MOVE:
                        X = evX - dragX;
                        Y = evY - dragY;
                        layer.setX(X);
                        layer.setY(Y);
                        break;
                    case MotionEvent.ACTION_UP:
                        if (layer.isOnPlace()) {
                            layer.fix();
                        }
                        /*
                        if (puzzle.isOnPlace()) {
                            fixPuzzle(puzzle);
                        }
                        puzzle.parentField.connectNearPuzzle(puzzle);
                        if (puzzle.parentField.isEnd()) showMsg();
                        */
                }
                return true;
            }

            /*
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                Puzzle puzzle = (Puzzle) view;
                if (!puzzle.canMove()) return true;

                final int evX = (int) motionEvent.getRawX();
                final int evY = (int) motionEvent.getRawY();
                switch (motionEvent.getAction() ){
                    case MotionEvent.ACTION_DOWN:
                        FrameLayout frameLayout = (FrameLayout) puzzle.getParent();
                        puzzleList = puzzle.getParentField().getPuzzleListOnSameLayer(puzzle);
                        for (Puzzle p : puzzleList) {
                            frameLayout.removeView(p);
                            frameLayout.addView(p);
                        }

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
                        puzzle.parentField.movePuzzlesInSameLayer(puzzle, puzzleList);
                        break;
                    case MotionEvent.ACTION_UP:
                        if (puzzle.isOnPlace()) {
                            fixPuzzle(puzzle);
                        }
                        puzzle.parentField.connectNearPuzzle(puzzle);
                        if (puzzle.parentField.isEnd()) showMsg();
                }
                return true;
            }
            */

            private void fixPuzzle(Puzzle puzzle) {
                puzzle.setX(puzzle.getRealX());
                puzzle.setY(puzzle.getRealY());
                puzzle.canMove(false);
                new Thread(){
                    public void run(){
                        MediaPlayer.create(GameActivity.getContext(), R.raw.click2).start();
                    }
                }.start();
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
/*
    private boolean isNearOuter() {
        Field.DynamicList list = new Field.DynamicList();
        list.puzzle = this;
        Puzzle puzzle1 = parentField.puzzles[i - 1][j];
        Puzzle puzzle2 = parentField.puzzles[i][j + 1];
        Puzzle puzzle3 = parentField.puzzles[i + 1][j];
        Puzzle puzzle4 = parentField.puzzles[i][j - 1];
        if (isNear(this, puzzle1)) list = parentField.addToList(list, puzzle1);

    }
*/
    public static boolean isNear(Puzzle p1, Puzzle p2) {
        return (Math.abs(Math.abs(p1.getX() - p2.getX()) - Math.abs(p1.getRealX() - p2.getRealX())) < 20 &&
                Math.abs(Math.abs(p1.getY() - p2.getY()) - Math.abs(p1.getRealY() - p2.getRealY())) < 20);
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

    public int getI() {
        return i;
    }

    public void setI(int i) {
        this.i = i;
    }

    public int getJ() {
        return j;
    }

    public void setJ(int j) {
        this.j = j;
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
        return Math.abs(getX() - realX) < 15 && Math.abs(getY() - realY) < 15;
    }

    public static boolean isEnd() {
        return puzzleCount == countPuzzleOnPlace;
    }

    public Field getParentField() {
        return parentField;
    }

    public void setParentField(Field parentField) {
        this.parentField = parentField;
    }

    public Layer getParentLayer() {
        return parentLayer;
    }

    public void setParentLayer(Layer parentLayer) {
        this.parentLayer = parentLayer;
    }

    public void setFieldPos(int i, int j) {
        this.i = i;
        this.j = j;
    }
}
