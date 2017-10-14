package pl.edu.prz.klopusz.engine.impl;

import lombok.val;
import pl.edu.prz.klopusz.application.model.game.Piece;
import pl.edu.prz.klopusz.application.model.game.Square;

import java.util.function.IntFunction;

public class CampBorder {
    final public int lower, upper;
    final Piece.Color color;

    public CampBorder(Piece.Color color) {
        this.color = color;

        switch (color) {
            case WHITE:
                lower = 6;
                upper = 9; //TODO: Board.MAX_INDEX
                break;
            case BLACK:
                lower = 4;
                upper = 1;
                break;
            default:
                lower = -1;
                upper = -1;
        }
    }

    public IntFunction<Integer> next() {
        switch (color) {
            case WHITE:
                return (int x)->x+1;
            case BLACK:
                return (int x)->x-1;
            default:
                return (int x)->x;
        }
    }

    public boolean isWithin(Square square) {
        val range = new Range(lower, upper);
        return range.contains(square.getRow()) && range.contains(square.getFile());
    }

    private class Range {
        private final int low;
        private final int high;

        public Range(int low, int high) {
            this.low = low;
            this.high = high;
        }

        public boolean contains(int number) {
            if(low <= high)
                return low<=number && number<=high;
            return high<=number && number<=low;
        }
    }
}
