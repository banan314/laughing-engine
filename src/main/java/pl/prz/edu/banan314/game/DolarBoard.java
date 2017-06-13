package pl.prz.edu.banan314.game;

/**
 * Created by kamil on 13.06.17.
 */
public class DolarBoard extends Board {

    @Override
    public void initialize() {
        squares = new Piece[9][9];

        squares[0][0] = (new Piece(Piece.Color.WHITE));
        squares[8][8] = (new Piece(Piece.Color.BLACK));
    }

    @Override
    public Piece get(int row, int file) {
        row--; file--;

        assert row >= 0 && row <= 8;
        assert file >= 0 && file <= 8;

        return squares[row][file];
    }

    @Override public void set(int row, int file, Piece square) {
        row--; file--;

        assert row >= 0 && row <= 8;
        assert file >= 0 && file <= 8;

        squares[row][file] = square;
    }

    @Override public void set(Square square) {
        int row = square.getRow();
        int file = square.getFile();
        Piece piece = square.getPiece();

        set(row, file, piece);
    }
}
