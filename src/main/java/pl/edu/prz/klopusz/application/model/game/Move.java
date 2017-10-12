package pl.edu.prz.klopusz.application.model.game;

/**
 * Created by kamil on 04.06.17.
 */

import lombok.*;
import org.ggp.base.util.gdl.grammar.GdlSentence;

//TODO: add a possibility to pass
public class Move {
    @Getter @Setter int file;
    @Getter @Setter  int row;
    @Getter @Setter Piece piece;

    public static Move from(GdlSentence sentence) {
        val move = new Move();

        assert isMark(sentence.toString());

        final int file = Byte.parseByte(sentence.get(0).toString());
        final int row =Byte.parseByte(sentence.get(1).toString());
        final Piece piece = Piece.from(sentence.get(2).toString());

        move.setFile(file);
        move.setRow(row);
        move.setPiece(piece);

        return move;
    }

    /**
     * @return square - made out of move, using row and file
     */
    final public Square square() {
        return new Square((byte)row, (byte)file);
    }

    private static boolean isMark(String mark) {
        return mark.contains("mark");
    }

    @Override
    public String toString() {
        return "Move{"+"file="+file+", row="+row+", piece="+piece+'}';
    }
}
