package pl.prz.edu.banan314.utilities.decorators;

import org.ggp.base.util.game.Game;
import org.ggp.base.util.statemachine.MachineState;
import org.ggp.base.util.statemachine.Move;
import org.ggp.base.util.statemachine.Role;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.edu.prz.klopusz.application.services.DolarGameCreator;
import pl.edu.prz.klopusz.utilities.decorators.StateMachineDecorator;

import java.util.List;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Created by kamil on 19.06.17.
 */
class StateMachineDecoratorTest {
    Game dolar;
    Role white, black;

    StateMachineDecorator stateMachine;

    @BeforeEach
    void setUp() {
        dolar = DolarGameCreator.createDolarGame();

        stateMachine = new StateMachineDecorator();
        stateMachine.initialize(dolar.getRules());

        white = stateMachine.getRoles().get(1);
        black = stateMachine.getRoles().get(0);
    }

    @Test
    void goalShouldEqual0() throws Exception {
        MachineState state = stateMachine.getInitialState();

        int result = stateMachine.getGoal(state, white) + stateMachine.getGoal(state, black);

        assertEquals("goal should  be 0", result, 0);
    }

    @Test
    void shouldBeMoreThan1LegalMoves() throws Exception {
        MachineState state = stateMachine.getInitialState();

        List<Move> whiteMoves = stateMachine.getLegalMoves(state, white);
        List<Move> blackMoves = stateMachine.getLegalMoves(state, black);

        System.out.println("white's moves: ");
        for(Move move : whiteMoves) {
            System.out.print(move+", ");
        }
        System.out.println();

        System.out.println("black's moves: ");
        for(Move move : blackMoves) {
            System.out.print(move+", ");
        }
        System.out.println();

        //TODO: if on move! (noop is a single option)

        assertThat(white.toString(), containsString("white"));
        assertTrue(whiteMoves.size() > 1, "white has no more than 1 move");
        assertTrue(blackMoves.size() > 1, "black has no more than 1 move");
    }

}