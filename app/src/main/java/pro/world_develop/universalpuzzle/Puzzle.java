package pro.world_develop.universalpuzzle;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageView;

/**
 * Created by ildar on 25.04.2016.
 */
public class Puzzle extends ImageView {

    public Puzzle(Context context, Bitmap bitmap, int i, int j) {
        super(context);
        this.setImageBitmap(bitmap);
        this.setOnTouchListener(new PuzzleOnTouchListener(this));
        this.setMaxHeight(10);
        this.setMaxWidth(10);
    }
}
