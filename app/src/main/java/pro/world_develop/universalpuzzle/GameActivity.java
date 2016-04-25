package pro.world_develop.universalpuzzle;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.FrameLayout;
import android.widget.ImageView;

public class GameActivity extends AppCompatActivity {
    private static ImageDecomposing imageDecomposing = ImageDecomposing.INSTANCE;
    private FrameLayout mainLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        mainLayout = (FrameLayout) findViewById(R.id.mainLayout);
        setImage();
    }

    private void setImage() {
        Bitmap image = BitmapFactory.decodeResource(getBaseContext().getResources(), R.drawable.test_image2);
        Bitmap[][] bitmaps = imageDecomposing.parse(image, 5, 5);
        for (int i = 0; i < bitmaps.length; i++) {
            for (int j = 0; j < bitmaps[0].length; j++) {
                Puzzle puzzle = new Puzzle(this, bitmaps[i][j], i, j);
                //ImageView imageView = new ImageView(this);
                //imageView.setImageBitmap(bitmaps[i][j]);
                //imageView.setOnTouchListener(new PuzzleOnTouchListener(imageView));
                mainLayout.addView(puzzle);
            }
        }
    }
}
