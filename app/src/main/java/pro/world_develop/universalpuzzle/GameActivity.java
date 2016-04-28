package pro.world_develop.universalpuzzle;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import java.util.Random;

public class GameActivity extends Activity {
    private static int countFragmentOnHeight = 1;
    private static int countFragmentOnWidth = 2;

    private static ImageDecomposing imageDecomposing = ImageDecomposing.INSTANCE;
    private static Context context;
    private static Random rand = new Random();
    private static DisplayMetrics display = new DisplayMetrics();

    private FrameLayout mainLayout;
    private FrameLayout workLayout;
    private int puzzleWidth;
    private int puzzleHeight;
    private int frameWidth;
    private int frameHeight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        new LockOrientation(this).lock();

        mainLayout = (FrameLayout) findViewById(R.id.mainLayout);
        workLayout = (FrameLayout) findViewById(R.id.workLayout);
        context = GameActivity.this;

        Bitmap image = getImage();
        Bitmap[][] fragments = imageDecomposing.parse(image, countFragmentOnHeight, countFragmentOnWidth);
        initParams(image);
        addFrame();
        setImage(fragments);
    }

    private Bitmap getImage() {
        return BitmapFactory.decodeResource(getBaseContext().getResources(), R.drawable.test_image2);
    }

    private void initParams(Bitmap image) {
        getWindowManager().getDefaultDisplay().getMetrics(display);
        frameWidth = display.widthPixels - 60;
        frameHeight = image.getHeight() * frameWidth / image.getWidth();
        puzzleWidth = frameWidth / countFragmentOnWidth;
        puzzleHeight = frameHeight / countFragmentOnHeight;

        Puzzle.setPuzzleCount(countFragmentOnHeight * countFragmentOnWidth);
        Puzzle.setCountPuzzleOnPlace(0);
    }

    private void setImage(Bitmap[][] fragments) {
        for (int i = 0; i < fragments.length; i++) {
            for (int j = 0; j < fragments[0].length; j++) {
                addPuzzle(new Puzzle(this, fragments[i][j]), i, j);
            }
        }
    }

    private void addFrame() {
        ViewGroup.LayoutParams params = workLayout.getLayoutParams();
        params.height = frameHeight;
        params.width = frameWidth;
        workLayout.setLayoutParams(params);
    }

    private void addPuzzle(Puzzle puzzle, int iInd, int jInd) {
        mainLayout.addView(puzzle);
        puzzle.setRealLocation(30 + iInd * puzzleWidth, 50 + jInd * puzzleHeight);

        FrameLayout.LayoutParams puzzleParams = new FrameLayout.LayoutParams(puzzleWidth, puzzleHeight);
        puzzleParams.leftMargin = rand.nextInt(display.widthPixels - puzzleWidth);
        puzzleParams.topMargin = rand.nextInt(display.heightPixels - (50 + frameHeight + puzzleHeight)) + 50 + frameHeight;
        puzzle.setLayoutParams(puzzleParams);
    }

    public static void setCountFragmentOnHeight(int countFragmentOnHeight) {
        GameActivity.countFragmentOnHeight = countFragmentOnHeight;
    }

    public static void setCountFragmentOnWidth(int countFragmentOnWidth) {
        GameActivity.countFragmentOnWidth = countFragmentOnWidth;
    }

    public static Context getContext() {
        return context;
    }
}
