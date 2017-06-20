package pl.prz.edu.banan314.game;

import java.util.List;

/**
 * Created by kamil on 04.06.17.
 */
public abstract class Board {
    Square[][] squares;

    public abstract void initialize();

    public abstract Square get(int row, int file);

    public abstract void set(int row, int file, Square square);
    public abstract void set(Square square);

    public abstract List<Square> getNeighbors(int file, int row);
}
