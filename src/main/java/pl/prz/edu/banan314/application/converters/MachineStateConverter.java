package pl.prz.edu.banan314.application.converters;

import org.ggp.base.util.gdl.grammar.GdlSentence;
import org.ggp.base.util.statemachine.MachineState;
import pl.prz.edu.banan314.application.exceptions.InvalidCellSentenceException;
import pl.prz.edu.banan314.application.model.game.Board;
import pl.prz.edu.banan314.application.model.game.DolarBoard;
import pl.prz.edu.banan314.application.model.game.Square;

import java.util.Set;

/**
 * Created by kamil on 13.06.17.
 */
public class MachineStateConverter {
    MachineState machineState;

    public MachineStateConverter(MachineState ms) {
        this.machineState = ms;
    }

    public Board toBoard() {
        Set<GdlSentence> sentences = machineState.getContents();
        Board board = new DolarBoard();
        board.initialize();
        for(GdlSentence sentence : sentences) {
            try {
                board.set(Square.from(sentence));
            } catch (InvalidCellSentenceException e) {
                //it is either step or something
            }
        }
        return board;
    }
}
