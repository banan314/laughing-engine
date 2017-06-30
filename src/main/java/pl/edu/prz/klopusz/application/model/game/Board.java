package pl.edu.prz.klopusz.application.model.game;

import java.util.List;

/**
 * Created by kamil on 04.06.17.
 */
public abstract class Board {

    public static final int MIN_INDEX = 1;
    public static final int MAX_INDEX = 9;

    Square[][] squares;

    public abstract void initialize();

    public abstract Square get(int row, int file);

    public abstract void set(int row, int file, Square square);

    public abstract void set(Square square);

    public abstract List<Square> get8Neighbors(int file, int row);

    public abstract boolean isLegal(Move move);
}
