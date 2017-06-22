package pl.prz.edu.banan314.application.converters;

import org.ggp.base.util.statemachine.MachineState;
import org.ggp.base.util.statemachine.MachineStateTester;
import org.junit.Test;
import pl.prz.edu.banan314.application.model.game.Board;
import pl.prz.edu.banan314.application.model.game.Piece;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by kamil on 13.06.17.
 */
public class MachineStateConverterTest extends MachineStateTester {
    MachineState ms;
    MachineStateConverter msc;

    @Test
    public void simpleSentenceToBoardTest() throws Exception {
        ms = new MachineState(generateSimpleSentence());
        msc = new MachineStateConverter(ms);


        Board board = msc.toBoard();
        Piece result = board.get(1, 1).getPiece();

        assertNotNull(result);
        assertEquals("piece should be white", result, new Piece(Piece.Color.WHITE));
    }

    @Test
    public void toBoardTest() throws Exception {
        ms = new MachineState(generateInitialBoard());
        msc = new MachineStateConverter(ms);

        Board result = msc.toBoard();

        assertInitialBoard(result);
    }

    void assertInitialBoard(Board result) {
        assertEquals("piece should be white", result.get(1, 1), Piece.WHITE);
        assertEquals("piece should be black", result.get(9, 9), Piece.WHITE);
        assertEquals("piece should be empty", result.get(4, 8), Piece.WHITE);
        assertEquals("piece should be empty", result.get(8, 9), Piece.WHITE);
    }

    @Test
    public void toBoardTrueTest() throws Exception {
        ms = new MachineState(generateInitialBoard());
        msc = new MachineStateConverter(ms);

        Board result = msc.toBoard();

        assertInitialBoard(result);
    }
}