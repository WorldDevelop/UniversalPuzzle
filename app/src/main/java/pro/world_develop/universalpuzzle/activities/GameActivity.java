package pro.world_develop.universalpuzzle.activities;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.DisplayMetrics;
import android.util.FloatMath;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

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
    private static Bitmap image;

    private static ImageDecomposing imageDecomposing = ImageDecomposing.INSTANCE;
    private static Context context;
    private static Random rand = new Random();
    private static Field field;
    private static DisplayMetrics display = new DisplayMetrics();
    private static GameActivity currentGame;

    private FrameLayout mainLayout;
    private FrameLayout workLayout;
    private TextView gameProcess;
    private int puzzleWidth;
    private int puzzleHeight;
    private int frameWidth;
    private int frameHeight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        initParams();
        initMainLayout();
        addFrame();

        if (field == null) {
            Bitmap[][] fragments = imageDecomposing.parse(image, countFragmentOnHeight, countFragmentOnWidth);
            field = createField(fragments);
        }
        showPuzzles(field);
    }

    private void initMainLayout() {
        int mainLayoutWidth = 3 * image.getWidth();
        int mainLayoutHeight = 3 * image.getHeight();
        ViewGroup.LayoutParams params = mainLayout.getLayoutParams();
        params.height = mainLayoutHeight;
        params.width = mainLayoutWidth;
        mainLayout.setLayoutParams(params);
        mainLayout.setOnTouchListener(new View.OnTouchListener() {
            private int dragX = 0;
            private int dragY = 0;
            float oldDist;
            String mode;
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                final int evX = (int) motionEvent.getRawX();
                final int evY = (int) motionEvent.getRawY();

                switch (motionEvent.getAction() ) {
                    case MotionEvent.ACTION_POINTER_DOWN:
                        oldDist = spacing(motionEvent);
                        mode = "ZOOM";
                        //resize
                        break;
                    case MotionEvent.ACTION_DOWN:
                        dragX = evX - (int) view.getX();
                        dragY = evY - (int) view.getY();
                        mode = "DRAG";
                        break;
                    case MotionEvent.ACTION_MOVE:
                        if (mode.equals("DRAG")) {
                            view.setX(evX - dragX);
                            view.setY(evY - dragY);
                        } else if (mode.equals("ZOOM")) {
                            float newDist = spacing(motionEvent);
                            float scale = newDist / oldDist;
                            Toast.makeText(context, String.valueOf(scale), Toast.LENGTH_LONG).show();
                            mainLayout.setScaleX(scale);
                            mainLayout.setScaleY(scale);
                        }
                        break;
                }
                return true;
            }

            private float spacing(MotionEvent event) {
                float x = event.getX(0) - event.getX(1);
                float y = event.getY(0) - event.getY(1);
                return (float) Math.sqrt(x*x + y*y);
            }
        });
        //mainLayout.setScaleX((float) 0.2);
        //mainLayout.setScaleY((float) 0.2);
        ((SeekBar) findViewById(R.id.seekBarForMainLayout)).setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                float sc = (float) ((i + 10) / 100.0);
                mainLayout.setScaleX(sc);
                mainLayout.setScaleY(sc);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    private void initParams() {
        mainLayout = (FrameLayout) findViewById(R.id.mainLayout);
        workLayout = (FrameLayout) findViewById(R.id.workLayout);
        gameProcess = (TextView) findViewById(R.id.gameProcess);
        context = GameActivity.this;
        currentGame = this;

        getWindowManager().getDefaultDisplay().getMetrics(display);
        /*
        if (display.heightPixels > display.widthPixels) {
            frameWidth = display.widthPixels - 60;
            frameHeight = image.getHeight() * frameWidth / image.getWidth();
        } else {
            frameWidth = display.widthPixels / 2 - 60;
            frameHeight = image.getHeight() * frameWidth / image.getWidth();
        }
        */
        frameWidth = image.getWidth();
        frameHeight = image.getHeight();
        puzzleWidth = frameWidth / countFragmentOnWidth;
        puzzleHeight = frameHeight / countFragmentOnHeight;
    }

    private void showPuzzles(Field field) {
        for (Layer layer : field.getLayers()) {
            if (layer.getParent() != null)
                ((FrameLayout) layer.getParent()).removeView(layer);
            mainLayout.addView(layer);

            layer.setFrame(workLayout);
            layer.setLayoutParams(workLayout.getLayoutParams());
            for (Puzzle p : layer.getPuzzles()) {
                p.setRealLocation(p.getIndI() * puzzleWidth, p.getIndJ() * puzzleHeight);
                FrameLayout.LayoutParams puzzleParams = new FrameLayout.LayoutParams(puzzleWidth, puzzleHeight);
                puzzleParams.leftMargin = p.getRealX();
                puzzleParams.topMargin = p.getRealY();
                p.setLayoutParams(puzzleParams);
            }
            if (!layer.isCanMove()) {
                ViewGroup.LayoutParams oldParams = workLayout.getLayoutParams();
                FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(oldParams.width, oldParams.height);
                params.leftMargin = 30; //hardcore
                params.topMargin = 100; //hardcore
                layer.setLayoutParams(params);
            } else if (display.heightPixels > display.widthPixels) {
                layer.setX(rand.nextInt(display.widthPixels - puzzleWidth) - layer.getPuzzles().get(0).getRealX());
                layer.setY(rand.nextInt(display.heightPixels - (50 + frameHeight + puzzleHeight)) + 50 + frameHeight - layer.getPuzzles().get(0).getRealY());
            } else {
                layer.setX(frameWidth + rand.nextInt(display.widthPixels - frameWidth - layer.getPuzzles().get(0).getRealX()) - puzzleWidth);
                layer.setY(rand.nextInt(display.heightPixels - layer.getPuzzles().get(0).getRealX() - puzzleHeight));
            }
        }
        updateGameProcess();
    }

    private Field createField(Bitmap[][] fragments) {
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
        layer.addView(puzzle);
        return layer;
    }

    @NonNull
    private Puzzle createPuzzle(Bitmap image, int i, int j) {
        Puzzle puzzle = new Puzzle(this, image, i, j);
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
        workLayout.setX(frameWidth);
        workLayout.setY(frameHeight);
    }

    public static void setCountFragmentOnHeight(int countFragmentOnHeight) {
        GameActivity.countFragmentOnHeight = countFragmentOnHeight;
        field = null;
    }

    public static void setCountFragmentOnWidth(int countFragmentOnWidth) {
        GameActivity.countFragmentOnWidth = countFragmentOnWidth;
        field = null;
    }

    public static void setImage(Bitmap image) {
        GameActivity.image = image;
        field = null;

    }

    public static Context getContext() {
        return context;
    }

    public static void updateGameProcess() {
        int process = (int) (field.getCountFixPuzzles() * 100);
        currentGame.gameProcess.setText(String.valueOf(process) + " %");

    }
}
