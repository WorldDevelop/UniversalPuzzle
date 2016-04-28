package pro.world_develop.universalpuzzle;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.ViewGroup;
import android.widget.ImageView;

/**
 * Created by ildar on 25.04.2016.
 */
public class Puzzle extends ImageView {
    private boolean canMove;
    private int realX;
    private int realY;

    public Puzzle(Context context, Bitmap image) {
        super(context);
        this.setImageBitmap(image);
        this.setOnTouchListener(new PuzzleOnTouchListener(this));
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
}
