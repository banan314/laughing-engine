package pl.prz.edu.banan314.apps.kiosk.games;

import org.ggp.base.apps.kiosk.GameCanvas;
import org.ggp.base.apps.kiosk.templates.GameCanvas_SimpleGrid;

import java.awt.*;

/**
 * Created by kamil on 10.05.17.
 */
public class DolarCanvas extends GameCanvas_SimpleGrid {
    @Override
    protected int getGridWidth() {
        return 9;
    }

    @Override
    protected int getGridHeight() {
        return 9;
    }

    @Override
    protected void renderCell(Graphics g, int x, int y) {

    }

    @Override
    protected void handleClickOnCell(int xCell, int yCell, int xWithin, int yWithin) {

    }

    @Override
    public String getGameName() {
        return "Dolar";
    }

    @Override
    protected String getGameKey() {
        return "dolar";
    }

    @Override
    public void clearMoveSelection() {

    }
}
