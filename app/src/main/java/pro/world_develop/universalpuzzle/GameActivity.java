package pro.world_develop.universalpuzzle;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import java.util.Random;

public class GameActivity extends Activity {
    private static int COUNT_FRAGMENT_ON_HEIGHT = 2;
    private static int COUNT_FRAGMENT_ON_WIDTH = 4;
    private static ImageDecomposing imageDecomposing = ImageDecomposing.INSTANCE;
    private FrameLayout mainLayout;
    private FrameLayout workLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        mainLayout = (FrameLayout) findViewById(R.id.mainLayout);
        workLayout = (FrameLayout) findViewById(R.id.workLayout);
        setImage();
    }

    private void setImage() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        Bitmap image = BitmapFactory.decodeResource(getBaseContext().getResources(), R.drawable.test_image2);
        int imageWidth = displayMetrics.widthPixels - 60;
        int imageHeight = image.getHeight() * imageWidth / image.getWidth();

        ViewGroup.LayoutParams params = workLayout.getLayoutParams();
        params.height = imageHeight;
        params.width = imageWidth;
        workLayout.setLayoutParams(params);


        Bitmap[][] bitmaps = imageDecomposing.parse(image, COUNT_FRAGMENT_ON_HEIGHT, COUNT_FRAGMENT_ON_WIDTH);
        int puzzleWidth = imageWidth / COUNT_FRAGMENT_ON_WIDTH;
        int puzzleHeight = imageHeight / COUNT_FRAGMENT_ON_HEIGHT;
        Random rand = new Random();
        for (int i = 0; i < bitmaps.length; i++) {
            for (int j = 0; j < bitmaps[0].length; j++) {
                Puzzle puzzle = new Puzzle(this, bitmaps[i][j]);
                mainLayout.addView(puzzle);
                puzzle.setRealLocation(30 + i*puzzleWidth, 50 + j*puzzleHeight);

                FrameLayout.LayoutParams puzzleParams = new FrameLayout.LayoutParams(puzzleWidth, puzzleHeight);
                puzzleParams.leftMargin = rand.nextInt(displayMetrics.widthPixels - puzzleWidth);
                puzzleParams.topMargin = rand.nextInt(displayMetrics.heightPixels - (50 + imageHeight + puzzleHeight)) + 50 + imageHeight;
                puzzle.setLayoutParams(puzzleParams);
            }
        }

        imageHeight++;
    }
}
