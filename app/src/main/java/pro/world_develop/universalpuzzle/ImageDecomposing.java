package pro.world_develop.universalpuzzle;

import android.graphics.Bitmap;
import android.graphics.Picture;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.PictureDrawable;
import android.media.Image;
import android.widget.ImageView;

/**
 * Created by User on 24.04.2016.
 */
public enum ImageDecomposing {
    INSTANCE;

    public Bitmap[][] parse(Bitmap image, int countSegmentsOnHeight, int countSegmentOnWidth) {
        Bitmap[][] decomposition = new Bitmap[countSegmentOnWidth][countSegmentsOnHeight];
        int widthOfSegment = image.getWidth() / countSegmentOnWidth;
        int heightOfSegment = image.getHeight() / countSegmentsOnHeight;

        for (int i = 0; i < decomposition.length; i++) {
            for (int j = 0; j < decomposition[0].length; j++) {
                decomposition[i][j] = generateImage(image, i, j, widthOfSegment, heightOfSegment);
            }
        }

        return decomposition;
    }

    private Bitmap generateImage(Bitmap src, int i, int j, int width, int height) {
        Bitmap res = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
        for (int x = 0; x < res.getWidth(); x++) {
            for (int y = 0; y < res.getHeight(); y++) {
                res.setPixel(x, y, src.getPixel(i*width + x, j*height + y));
            }
        }
        return res;
    }
}
