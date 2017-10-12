package pl.edu.prz.klopusz.application.model.game;

import lombok.val;

import java.util.ArrayList;
import java.util.List;

import java.util.logging.Logger;

/**
 * Created by kamil on 13.06.17.
 */
public class DolarBoard extends Board {

    public static final int MIN_ARRAY_INDEX = MIN_INDEX-1;
    public static final int MAX_ARRAY_INDEX = MAX_INDEX-1;

    private Logger LOG = Logger.getLogger("Dolar board");

    public DolarBoard() {
        initialize();
    }

    @Override
    public void initialize() {
        assert 9 >= MAX_INDEX;
        squares = new Square[9][9];

        squares[0][0] = new Square(Piece.WHITE);
        squares[8][8] = new Square(Piece.BLACK);
    }

    @Override
    public Square get(int row, int file) {
        assert row >= MIN_INDEX && row <= MAX_INDEX;
        assert file >= MIN_INDEX && file <= MAX_INDEX;

        row--;
        file--;

        if (null == squares[row][file]) {
            return new Square(); //empty - null pattern
        }
        return squares[row][file];
    }

    @Override
    public void set(int row, int file, Square square) {
        assert row >= MIN_INDEX && row <= MAX_INDEX;
        assert file >= MIN_INDEX && file <= MAX_INDEX;

        row--;
        file--;

        squares[row][file] = square;
    }

    @Override
    public void set(Square square) {
        int row = square.getRow();
        int file = square.getFile();

        set(row, file, square);
    }

    @Override
    public List<Square> get8Neighbors(int row, int file) {
        List<Square> adjacent = new ArrayList<>();

        row--;
        file--;

        if (file > MIN_ARRAY_INDEX) {
            adjacent.add(squares[row][file-1]);
            if (row > MIN_ARRAY_INDEX) {
                adjacent.add(squares[row-1][file-1]);
            }
            if (row < MAX_ARRAY_INDEX) {
                adjacent.add(squares[row+1][file-1]);
            }
        }
        if (file < MAX_ARRAY_INDEX) {
            adjacent.add(squares[row][file+1]);
            if (row > MIN_ARRAY_INDEX) {
                adjacent.add(squares[row-1][file+1]);
            }
            if (row < MAX_ARRAY_INDEX) {
                adjacent.add(squares[row+1][file+1]);
            }
        }

        if (row > MIN_ARRAY_INDEX) {
            adjacent.add(squares[row-1][file]);
        }
        if (row < MAX_ARRAY_INDEX) {
            adjacent.add(squares[row+1][file]);
        }

        return adjacent;
    }

    @Override
    public boolean isLegal(Move move) {
        if (isEmpty(move.getRow(), move.getFile())) {
            if (isNeighbor(move.getRow(), move.getFile(), move.getPiece())) {
                return true;
            } else {
                LOG.info("no neighbor, (x, y)=(" + move.getFile() + ", " +move.getRow() + ")");
                return false;
            }
        } else {
            LOG.info("not empty, (x, y)=(" + move.getFile() + ", " +move.getRow() + ")");
            return false;
        }
    }

    private boolean isNeighbor(int row, int file, Piece piece) {
        val neighbors = get8Neighbors(row, file);
        for(val neighbor : neighbors) {
            if (neighbor != null && neighbor.getPiece().equals(piece)) {
                return true;
            }
        }
        return false;
    }

    private boolean isEmpty(int row, int file) {
        row--;
        file--;
        return null == squares[row][file] || squares[row][file].isEmpty();
    }
}
