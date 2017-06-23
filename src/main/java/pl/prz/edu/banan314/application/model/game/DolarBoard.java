package pl.prz.edu.banan314.application.model.game;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kamil on 13.06.17.
 */
public class DolarBoard extends Board {

    public DolarBoard() {
        initialize();
    }

    @Override
    public void initialize() {
        assert 9 >= MAX_INDEX;
        squares = new Square[9][9];

        squares[0][0] = new Square(new Piece(Piece.Color.WHITE)); //TODO: flyweight
        squares[8][8] = new Square(new Piece(Piece.Color.BLACK));
    }

    @Override
    public Square get(int row, int file) {
        assert row >= MIN_INDEX && row <= MAX_INDEX;
        assert file >= MIN_INDEX && file <= MAX_INDEX;

        row--; file--;

        if(null == squares[row][file]) {
            return new Square(); //empty - null pattern
        }
        return squares[row][file];
    }

    @Override public void set(int row, int file, Square square) {
        assert row >= MIN_INDEX && row <= MAX_INDEX;
        assert file >= MIN_INDEX && file <= MAX_INDEX;

        row--; file--;

        squares[row][file] = square;
    }

    @Override public void set(Square square) {
        int row = square.getRow();
        int file = square.getFile();

        set(row, file, square);
    }

    @Override
    public List<Square> getNeighbors(int file, int row) {
        List<Square> adjacent = new ArrayList<>();

        if(file > MIN_INDEX)
            adjacent.add(squares[file-1][row]);
        if(file < MAX_INDEX)
            adjacent.add(squares[file+1][row]);
        if(row > MIN_INDEX)
            adjacent.add(squares[file][row-1]);
        if(row < MAX_INDEX)
            adjacent.add(squares[file][row+1]);

        return adjacent;
    }
}
