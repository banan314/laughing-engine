package pl.prz.edu.banan314.application.model.game;

/**
 * Created by kamil on 04.06.17.
 */

import lombok.*;
import org.ggp.base.util.gdl.grammar.GdlSentence;

import java.util.Scanner;

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

    private static boolean isMark(String mark) {
        return mark.contains("mark");
    }
}
