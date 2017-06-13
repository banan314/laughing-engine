package pl.prz.edu.banan314.utilities.converters;

import org.ggp.base.util.statemachine.MachineState;
import org.ggp.base.util.statemachine.MachineStateTester;
import org.junit.Before;
import org.junit.Test;
import pl.prz.edu.banan314.game.Board;
import pl.prz.edu.banan314.game.DolarBoard;
import pl.prz.edu.banan314.game.Piece;
import pl.prz.edu.banan314.game.Square;

import static org.junit.Assert.*;

/**
 * Created by kamil on 13.06.17.
 */
public class MachineStateConverterTest extends MachineStateTester {
    MachineState ms;

    @Before public void setUp() throws Exception {
        ms = new MachineState(generateSimpleSentence());
    }

    @Test public void toBoardTest() throws Exception {
        MachineStateConverter msc = new MachineStateConverter(ms);

        Board board = msc.toBoard();
        Piece result = board.get(1, 1);

        assertNotNull(result);
        assertEquals("piece not white", result, new Piece(Piece.Color.WHITE));
    }

}