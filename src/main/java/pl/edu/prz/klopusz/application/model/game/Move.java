package pl.edu.prz.klopusz.application.model.game;

/**
 * Created by kamil on 04.06.17.
 */

import lombok.*;
import org.ggp.base.util.gdl.grammar.GdlSentence;

public class Move {
    @Getter @Setter int file;
    @Getter @Setter int row;
    @Getter @Setter Piece piece;

    boolean passed = false;
    Piece.Color colorOfPassing;

    public static Move from(GdlSentence sentence) {
        val move = new Move();

        assert isMark(sentence.toString());

        final int row = Byte.parseByte(sentence.get(1).toString());
        final int file = Byte.parseByte(sentence.get(0).toString());
        final Piece piece = Piece.from(sentence.get(2).toString());

        move.setFile(file);
        move.setRow(row);
        move.setPiece(piece);

        return move;
    }

    public static Move pass(Piece.Color color) {
        Move pass = new Move();
        pass.passed = true;
        pass.colorOfPassing = color;
        return pass;
    }

    public boolean isPassed() {
        return passed;
    }

    /**
     * @return square - made out of move, using row and file
     */
    final public Square square() {
        val square = new Square((byte) row, (byte) file);
        square.setPiece(piece);
        return square;
    }

    private static boolean isMark(String mark) {
        return mark.contains("mark");
    }

    @Override
    public String toString() {
        return "Move{"+"file="+file+", row="+row+", piece="+piece+'}';
    }

    public Piece.Color getColor() {
        if (passed) {
            return colorOfPassing;
        } else {
            return piece.color;
        }
    }
}
