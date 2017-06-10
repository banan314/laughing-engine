package pl.prz.edu.banan314.game;

import static java.lang.Math.abs;

/**
 * Created by kamil on 04.06.17.
 */
public class Square {
    byte file, row;
    Piece piece = new Piece(Piece.Color.BLACK);

    public boolean isAdjacent(Square other) {
        return abs(file-other.file) <= 1 && abs(row-other.row)<=1;
    }

    public void setPiece(Piece piece) {
        this.piece = piece;
    }
}
