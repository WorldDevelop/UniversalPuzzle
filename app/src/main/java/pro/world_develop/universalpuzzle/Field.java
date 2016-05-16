package pro.world_develop.universalpuzzle;

import android.media.MediaPlayer;

import java.util.List;

import pro.world_develop.universalpuzzle.activities.GameActivity;

/**
 * Created by User on 02.05.2016.
 */
public class Field {
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
        layer.fix();
        return true;
    }

    public void tryMergeLayout(Layer currLayer) {
        boolean needSound = false;
        int i = 0;
        while (i < layers.size()) {
            Layer layer = layers.get(i);

            //todo сделать проверку, что слои имеют смежные пазлы, иначе можно вообще всякую хрень соединять
            if (!currLayer.equals(layer) &&
                    Math.abs(currLayer.getX() - layer.getX()) < 15 &&
                    Math.abs(currLayer.getY() - layer.getY()) < 15) {
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
