package pro.world_develop.universalpuzzle;

import android.media.MediaPlayer;

import java.util.List;

import pro.world_develop.universalpuzzle.activities.GameActivity;

/**
 * Created by User on 02.05.2016.
 */
public class Field {
    public static final int DIST_FOR_MERGE = 25;
    public static final int DIST_FOR_FIX = 20;

    List<Layer> layers;
    int puzzleCount;

    public Field(List<Layer> layers) {
        this.layers = layers;
        for (Layer layer : layers) {
            for (Puzzle puzzle : layer.getPuzzles()) {
                puzzle.setParentField(this);
                puzzle.setParentLayer(layer);
            }
        }
        puzzleCount = layers.size();
    }

    public boolean isEnd() {
        if (layers.size() != 1) return false;

        Layer layer = layers.get(0);
        layer.fix(Layer.MODE.WITH_SOUND);
        return true;
    }

    public void tryMergeLayout(Layer currLayer) {
        boolean needSound = false;
        int i = 0;
        while (i < layers.size()) {
            Layer layer = layers.get(i);

            if (isNeedToMerge(currLayer, layer)) {
                mergeLayers(currLayer, layer);
                layers.remove(layer);
                needSound = true;
            } else {
                i++;
            }
        }

        if (needSound)
            new Thread() {
                @Override
                public void run() {
                    MediaPlayer.create(GameActivity.getContext(), R.raw.merge).start();
                }
            }.start();
        GameActivity.updateGameProcess();
    }

    private boolean isNeedToMerge(Layer layer1, Layer layer2) {
        if (layer1.equals(layer2) ||
                Math.abs(layer1.getX() - layer2.getX()) > DIST_FOR_MERGE ||
                Math.abs(layer1.getY() - layer2.getY()) > DIST_FOR_MERGE)
            return false;

        for (Puzzle p1 : layer1.getPuzzles()) {
            for (Puzzle p2 : layer2.getPuzzles()) {
                if (p1.getRealX() - p2.getRealX() == 0 && Math.abs(p1.getRealY() - p2.getRealY()) == p1.getHeight() ||
                        p1.getRealY() - p2.getRealY() == 0 && Math.abs(p1.getRealX() - p2.getRealX()) == p1.getWidth())
                    return true;
            }
        }
        return false;
    }

    public List<Layer> getLayers() {
        return layers;
    }

    private void mergeLayers(Layer toLayer, Layer fromLayer) {
        for (Puzzle p : fromLayer.getPuzzles()) {
            p.setParentLayer(toLayer);
            fromLayer.removeView(p);
            toLayer.addView(p);
            toLayer.getPuzzles().add(p);
        }

        if (!fromLayer.isCanMove()) toLayer.fix(Layer.MODE.WITHOUT_SOUND);
    }

    public double getCountFixPuzzles() {
        int count = 0;
        for (Layer layer : layers) {
            if (layer.isCanMove()) continue;

            count += layer.getPuzzles().size();
        }
        return ((double) count) / puzzleCount;
    }
}
