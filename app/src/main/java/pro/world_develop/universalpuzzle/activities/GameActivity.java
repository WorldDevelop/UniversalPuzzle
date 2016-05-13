package pro.world_develop.universalpuzzle.activities;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.DisplayMetrics;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import pro.world_develop.universalpuzzle.Field;
import pro.world_develop.universalpuzzle.ImageDecomposing;
import pro.world_develop.universalpuzzle.Layer;
import pro.world_develop.universalpuzzle.Puzzle;
import pro.world_develop.universalpuzzle.R;

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

        mainLayout = (FrameLayout) findViewById(R.id.mainLayout);
        workLayout = (FrameLayout) findViewById(R.id.workLayout);
        context = GameActivity.this;

        Bitmap image = getImage();
        Bitmap[][] fragments = imageDecomposing.parse(image, countFragmentOnHeight, countFragmentOnWidth);
        initParams(image);
        addFrame();
        setPuzzles(fragments);
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
    }

    private Field setPuzzles(Bitmap[][] fragments) {
        List<Layer> layers = new ArrayList<>();
        for (int i = 0; i < fragments.length ; i++) {
            for (int j = 0; j < fragments[0].length; j++) {
                Layer layer = createLayer(fragments[i][j], i, j);
                layers.add(layer);
            }
        }
        return new Field(layers);
    }

    private Layer createLayer(Bitmap image, int i, int j) {
        Puzzle puzzle = createPuzzle(image, i, j);

        Layer layer = new Layer(getContext(), puzzle, workLayout);
        layer.setLayoutParams(workLayout.getLayoutParams());
        layer.addView(puzzle);
        layer.setX(rand.nextInt(display.widthPixels - puzzleWidth) - puzzle.getRealX());
        layer.setY(rand.nextInt(display.heightPixels - (50 + frameHeight + puzzleHeight)) + 50 + frameHeight - puzzle.getRealY());

        mainLayout.addView(layer);
        return layer;
    }

    @NonNull
    private Puzzle createPuzzle(Bitmap image, int i, int j) {
        Puzzle puzzle = new Puzzle(this, image);
        puzzle.setRealLocation(i * puzzleWidth, j * puzzleHeight);
        FrameLayout.LayoutParams puzzleParams = new FrameLayout.LayoutParams(puzzleWidth, puzzleHeight);
        puzzleParams.leftMargin = puzzle.getRealX();
        puzzleParams.topMargin = puzzle.getRealY();
        puzzle.setLayoutParams(puzzleParams);
        return puzzle;
    }

    private void addFrame() {
        ViewGroup.LayoutParams params = workLayout.getLayoutParams();
        params.height = frameHeight;
        params.width = frameWidth;
        workLayout.setLayoutParams(params);
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
