package pl.prz.edu.banan314.simpleGUI.dolar;

import org.ggp.base.apps.kiosk.templates.CommonGraphics;
import org.ggp.base.apps.kiosk.templates.GameCanvas_SimpleGrid;

import java.awt.*;

/**
 * Created by kamil on 26.05.17.
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

    private int selectedColumn = 0;

    @Override
    protected void renderCell(Graphics g, int xCell, int yCell) {
        yCell = 7 - yCell;

        int width = g.getClipBounds().width;
        int height = g.getClipBounds().height;

        g.setColor(Color.BLACK);
        g.drawRect(1, 1, width-2, height-2);

        if(gameStateHasFact("( cell " + xCell + " " + yCell + " white )")) {
            g.setColor(Color.RED);
            CommonGraphics.drawDisc(g);
        } else if(gameStateHasFact("( cell " + xCell + " " + yCell + " black )")) {
            g.setColor(Color.BLACK);
            CommonGraphics.drawDisc(g);
        } else {
            ;
        }

        if(selectedColumn == xCell) {
            g.setColor(Color.GREEN);
            g.drawRect(3, 3, width-6, height-6);
        }
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
