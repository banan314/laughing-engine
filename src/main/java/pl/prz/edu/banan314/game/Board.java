package pl.prz.edu.banan314.game;

/**
 * Created by kamil on 04.06.17.
 */
public class Board {
    Square[][] squares = new Square[9][9];

    void initialize() {
        squares[0][0].setPiece(new Piece(Piece.Color.WHITE));
        squares[8][8].setPiece(new Piece(Piece.Color.BLACK));
    }
}
