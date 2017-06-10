package pl.prz.edu.banan314.game;

/**
 * Created by kamil on 04.06.17.
 */
public class Move {
    Piece piece;
    Square square;

    public Piece getPiece() {
        return piece;
    }

    public void setPiece(Piece piece) {
        this.piece = piece;
    }

    public Square getSquare() {
        return square;
    }

    public void setSquare(Square square) {
        this.square = square;
    }
}
