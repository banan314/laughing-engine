package pl.prz.edu.banan314.game;

/**
 * Created by kamil on 04.06.17.
 */
public abstract class Board {
    Piece[][] squares;

    public abstract void initialize();

    public abstract Piece get(int row, int file);

    public abstract void set(int row, int file, Piece square);
    public abstract void set(Square square);
}
