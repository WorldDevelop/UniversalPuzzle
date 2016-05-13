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
    private int realX;
    private int realY;
    private Field parentField;
    private Layer parentLayer;

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
                        puzzle.getParentField().tryMergeLayout(layer);

                        if (puzzle.getParentField().isEnd()) {
                            showMsg();
                        }
                }
                return true;
            }

            private void showMsg() {
                AlertDialog.Builder builder = new AlertDialog.Builder(GameActivity.getContext());
                builder.setTitle("Congratulations!").setMessage("You have collected puzzle!!!");
                AlertDialog alert = builder.create();
                alert.show();
            }
        });
        this.setScaleType(ScaleType.FIT_XY);
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

}
