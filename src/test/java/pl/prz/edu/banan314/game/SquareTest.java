package pl.prz.edu.banan314.game;

import org.ggp.base.util.gdl.grammar.GdlSentence;
import org.ggp.base.util.statemachine.MachineStateTester;
import org.junit.Before;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;

/**
 * Created by kamil on 13.06.17.
 */
public class SquareTest {
    GdlSentence sentence;

    @Before public void setUp() throws Exception {
        sentence = MachineStateTester.generateSimpleSentence().stream().findFirst().get();
    }

    @Test public void isAdjacent() throws Exception {
    }

    @Test public void isEmpty() throws Exception {
    }

    @Test public void from() throws Exception {

        Square result = Square.from(sentence);

        assertEquals("file should be 1", 1, result.getFile());
        assertEquals("row should be 1", 1, result.getRow());
        assertEquals("color should be white", Piece.WHITE, result.getPiece());
    }

    @Test
    public void isCellTest() throws Exception {
        assertTrue("should be cell", Square.isCell(sentence.toString()));
    }
}