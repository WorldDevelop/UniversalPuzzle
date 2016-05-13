package pro.world_develop.universalpuzzle;

import java.util.List;

/**
 * Created by User on 02.05.2016.
 */
public class Field {
    List<Layer> layers;

    public Field(List<Layer> layers) {
        this.layers = layers;
        for (Layer layer : layers) {
            for (Puzzle puzzle : layer.getPuzzles()) {
                puzzle.setParentField(this);
                puzzle.setParentLayer(layer);
            }
        }
    }

    public boolean isEnd() {
        if (layers.size() != 1) return false;

        Layer layer = layers.get(0);
        layer.fix();
        return true;
    }

    public void tryMergeLayout(Layer currLayer) {
        int i = 0;
        while (i < layers.size()) {
            Layer layer = layers.get(i);

            //todo сделать проверку, что слои имеют смежные пазлы, иначе можно вообще всякую хрень соединять
            if (!currLayer.equals(layer) &&
                    Math.abs(currLayer.getX() - layer.getX()) < 15 &&
                    Math.abs(currLayer.getY() - layer.getY()) < 15) {
                mergeLayers(currLayer, layer);
                layers.remove(layer);
            } else {
                i++;
            }
        }
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
}
