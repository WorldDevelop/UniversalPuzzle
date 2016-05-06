package pro.world_develop.universalpuzzle;

import java.util.List;

/**
 * Created by User on 02.05.2016.
 */
public class Field {
    Puzzle[][] puzzles;
    int[][] mask;
    int layersCount;

    public Field(Puzzle[][] puzzles) {
        this.puzzles = puzzles;
        mask = new int[puzzles.length][puzzles[0].length];
        layersCount = 0;
        for (int i = 0; i < puzzles.length; i++) {
            for (int j = 0; j < puzzles[0].length; j++) {
                puzzles[i][j].setParentField(this);
                puzzles[i][j].setFieldPos(i, j);
                mask[i][j] = ++layersCount;
            }
        }
    }

    public void connectNearPuzzle(Puzzle p) {
        int i = p.getI();
        int j = p.getJ();
        int newLayer = ++layersCount;
        try {
            if (Puzzle.isNear(p, puzzles[i - 1][j])) mergeLayer(mask[i][j], mask[i - 1][j], newLayer);
        } catch (IndexOutOfBoundsException ignored) {

        }
        try {
            if (Puzzle.isNear(p, puzzles[i][j - 1])) mergeLayer(mask[i][j], mask[i][j - 1], newLayer);
        } catch (IndexOutOfBoundsException ignored) {

        }
        try {
            if (Puzzle.isNear(p, puzzles[i + 1][j])) mergeLayer(mask[i][j], mask[i + 1][j], newLayer);
        } catch (IndexOutOfBoundsException ignored) {

        }
        try {
            if (Puzzle.isNear(p, puzzles[i][j + 1])) mergeLayer(mask[i][j], mask[i][j + 1], newLayer);
        } catch (IndexOutOfBoundsException ignored) {

        }
        connectPuzzles(p);
    }

    private void mergeLayer(int layer1, int layer2, int newLayer) {
        for (int i = 0; i < mask.length; i++) {
            for (int j = 0; j < mask[0].length; j++) {
                if (mask[i][j] == layer1 || mask[i][j] == layer2) mask[i][j] = newLayer;
            }
        }
    }

    private void connectPuzzles(Puzzle p) {
        int currLayer = mask[p.getI()][p.getJ()];
        for (int i = 0; i < mask.length; i++) {
            for (int j = 0; j < mask[0].length; j++) {
                if (mask[i][j] == currLayer && puzzles[i][j].canMove()) {
                    int x = p.getRealX() - puzzles[i][j].getRealX();
                    int y = p.getRealY() - puzzles[i][j].getRealY();
                    puzzles[i][j].setX(p.getX() - x);
                    puzzles[i][j].setY(p.getY() - y);
                }
            }
        }
    }

    public void movePuzzlesInSameLayer(Puzzle p) {
        int currLayer = mask[p.getI()][p.getJ()];
        for (int i = 0; i < mask.length; i++) {
            for (int j = 0; j < mask[0].length; j++) {
                if (mask[i][j] == currLayer && puzzles[i][j].canMove()) {
                    int x = p.getRealX() - puzzles[i][j].getRealX();
                    int y = p.getRealY() - puzzles[i][j].getRealY();
                    puzzles[i][j].setX(p.getX() - x);
                    puzzles[i][j].setY(p.getY() - y);
                }
            }
        }
    }

    public boolean isEnd() {
        int layer = mask[0][0];
        for (int i = 0; i < mask.length; i++) {
            for (int j = 0; j < mask[0].length; j++) {
                if (mask[i][j] != layer) return false;
            }
        }
        return true;
    }
}
