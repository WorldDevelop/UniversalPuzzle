package pro.world_develop.universalpuzzle;

import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

/**
 * Created by ildar on 25.04.2016.
 */
public class PuzzleOnTouchListener implements View.OnTouchListener {
    private ImageView img;

    public PuzzleOnTouchListener(ImageView img) {
        this.img = img;
    }

    //разница между касанием и координатами рисунка
    private int dragX = 0;
    private int dragY = 0;
    //координаты рисунка
    private int X;
    private int Y;

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        final int evX = (int) event.getRawX();
        final int evY = (int) event.getRawY();
        switch (event.getAction() ){
            case MotionEvent.ACTION_DOWN:
                X = (int) img.getX();
                Y = (int) img.getY();
                dragX = evX - X;
                dragY = evY - Y;
                break;
            case MotionEvent.ACTION_MOVE:
                X = evX - dragX;
                Y = evY - dragY;
                v.setX(X);
                v.setY(Y);
                break;
        }
        return true;
    }
}
