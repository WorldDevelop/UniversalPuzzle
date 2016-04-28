package pro.world_develop.universalpuzzle;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.ViewGroup;
import android.widget.ImageView;

/**
 * Created by ildar on 25.04.2016.
 */
public class Puzzle extends ImageView {

    public Puzzle(Context context, Bitmap image) {
        super(context);
        this.setImageBitmap(image);
        this.setOnTouchListener(new PuzzleOnTouchListener(this));
        this.setScaleType(ScaleType.FIT_XY);
    }
}
