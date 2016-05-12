package pro.world_develop.universalpuzzle;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by User on 12.05.2016.
 */
public class Layer extends FrameLayout {
    List<Puzzle> puzzles;

    public Layer(Context context) {
        super(context);
    }

    public Layer(Context context, Puzzle puzzle) {
        super(context);
        puzzles = new ArrayList<>();
        puzzles.add(puzzle);
    }

    public List<Puzzle> getPuzzles() {
        return puzzles;
    }

    public void setPuzzles(List<Puzzle> puzzles) {
        this.puzzles = puzzles;
    }
}
