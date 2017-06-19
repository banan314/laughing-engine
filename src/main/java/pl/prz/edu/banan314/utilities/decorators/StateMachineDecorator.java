package pl.prz.edu.banan314.utilities.decorators;

import org.ggp.base.util.statemachine.MachineState;
import org.ggp.base.util.statemachine.Role;
import org.ggp.base.util.statemachine.exceptions.GoalDefinitionException;
import org.ggp.base.util.statemachine.implementation.prover.ProverStateMachine;
import pl.prz.edu.banan314.application.converters.MachineStateConverter;
import pl.prz.edu.banan314.game.Board;

/**
 * Created by kamil on 19.06.17.
 */
public class StateMachineDecorator extends ProverStateMachine {

    private Board board;

    @Override
    public int getGoal(MachineState state, Role role) throws GoalDefinitionException {
        Role theRival = getTheSecondRole(role);

        int ourGoal = super.getGoal(state, role);
        int rivalGoal = super.getGoal(state, theRival);

        MachineStateConverter converter = new MachineStateConverter(state);

        if(null == board) {
            board = converter.toBoard();
        }

        int whiteTerritory = computeWhiteTerritory();
        int blackTerritory = computeBlackTerritory();

        ourGoal += white(role) ? (whiteTerritory-blackTerritory) : (blackTerritory-whiteTerritory);

        return ourGoal-rivalGoal;
    }

    private boolean white(Role role) {
        final String WHITE = "white";
        String roleString = role.toString();

        return roleString.indexOf(WHITE) >= 0;
    }

    //TODO: implement
    private int computeBlackTerritory() {
        return 0;
    }

    //TODO: implement
    private int computeWhiteTerritory() {
        return 0;
    }

    Role getTheSecondRole(Role role) throws GoalDefinitionException {
        Role theRival = roles.stream().filter(aRole -> !aRole.equals(role)).findFirst().orElse(null);
        if (theRival == null) {
            throw new GoalDefinitionException("no other role");
        }
        return theRival;
    }
}
