package pro.world_develop.universalpuzzle;

import android.app.AlertDialog;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

/**
 * Created by ildar on 25.04.2016.
 */
public class PuzzleOnTouchListener implements View.OnTouchListener {
    //разница между касанием и координатами рисунка
    private int dragX = 0;
    private int dragY = 0;
    //координаты рисунка
    private int X;
    private int Y;

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        Puzzle puzzle = (Puzzle) v;
        if (!puzzle.canMove()) return true;

        final int evX = (int) event.getRawX();
        final int evY = (int) event.getRawY();
        switch (event.getAction() ){
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
}
