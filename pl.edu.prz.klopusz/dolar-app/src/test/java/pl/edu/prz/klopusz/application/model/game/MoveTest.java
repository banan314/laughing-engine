package pl.edu.prz.klopusz.application.model.game;

import lombok.val;
import org.ggp.base.util.gdl.factory.GdlFactory;
import org.ggp.base.util.gdl.grammar.GdlSentence;
import org.hamcrest.Description;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import  org.hamcrest.Matcher;

import java.util.function.Predicate;

public class MoveTest {
    Move move;

    Matcher<Move> moveMatcher(Predicate<Move> predicate) {
        return new Matcher<Move>() {
            @Override
            public boolean matches(Object o) {
                return predicate.test((Move) o);
            }

            @Override
            public void describeMismatch(Object o, Description description) {
            }

            @Override
            public void _dont_implement_Matcher___instead_extend_BaseMatcher_() {

            }

            @Override
            public void describeTo(Description description) {

            }
        };
    }

    @Test
    public void fromTest() throws Exception {
        GdlSentence sentence = (GdlSentence)GdlFactory.create("(mark 1 2 white)");

        move = Move.from(sentence);

        assertThat(move, moveMatcher(m -> m.getColor() == Piece.Color.WHITE));
        assertThat(move, moveMatcher(m -> m.getRow() == 2));
        assertThat(move, moveMatcher(m -> m.getFile() == 1));
    }

    @Test
    public void squareTest() throws Exception {
        move = new Move();
        move.setPiece(Piece.WHITE);
        move.setRow(1);
        move.setFile(2);

        val square = move.square();

        assertEquals(Piece.WHITE, square.getPiece());
        assertEquals(1, square.row);
        assertEquals(2, square.file);
    }

    @Test
    public void isPassedTest() throws Exception {
        move = Move.pass(Piece.Color.BLACK);
        assertTrue(move.isPassed());
        assertEquals(move.getColor(), Piece.Color.BLACK);
    }

    @Test
    public void getColorTest()  throws Exception {
        move = new Move();
        move.setPiece(Piece.BLACK);

        assertEquals(move.getColor(), Piece.Color.BLACK);
    }

}