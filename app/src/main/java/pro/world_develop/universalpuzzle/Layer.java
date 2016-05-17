package pro.world_develop.universalpuzzle;

import android.content.Context;
import android.media.MediaPlayer;
import android.text.Layout;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.FrameLayout;

import java.util.ArrayList;
import java.util.List;

import pro.world_develop.universalpuzzle.activities.GameActivity;

/**
 * Created by User on 12.05.2016.
 */
public class Layer extends FrameLayout {

    public static enum MODE {
        WITH_SOUND,
        WITHOUT_SOUND;
    }

    private List<Puzzle> puzzles;
    private boolean canMove;
    private FrameLayout frame;

    public Layer(Context context) {
        super(context);
    }

    public Layer(Context context, Puzzle puzzle, FrameLayout frame) {
        super(context);
        puzzles = new ArrayList<>();
        puzzles.add(puzzle);
        canMove = true;
        this.frame = frame;
    }

    public List<Puzzle> getPuzzles() {
        return puzzles;
    }

    public void setFrame(FrameLayout frame) {
        this.frame = frame;
    }

    public boolean isCanMove() {
        return canMove;
    }

    public boolean isOnPlace() {
        return Math.abs(getX() - frame.getX()) < 15 && Math.abs(getY() - frame.getY()) < 15;
    }

    public void fix(MODE mode) {
        setX(frame.getX());
        setY(frame.getY());
        canMove = false;
        if (mode.equals(MODE.WITH_SOUND)) {
            new Thread() {
                public void run() {
                    MediaPlayer.create(GameActivity.getContext(), R.raw.click).start();
                }
            }.start();
        }

        FrameLayout parent = (FrameLayout) this.getParent();
        parent.removeView(this);
        parent.addView(this, 2);
        GameActivity.updateGameProcess();
    }
}
