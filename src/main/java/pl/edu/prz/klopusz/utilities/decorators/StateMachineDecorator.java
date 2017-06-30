package pl.edu.prz.klopusz.utilities.decorators;

import org.ggp.base.util.statemachine.MachineState;
import org.ggp.base.util.statemachine.Role;
import org.ggp.base.util.statemachine.exceptions.GoalDefinitionException;
import org.ggp.base.util.statemachine.implementation.prover.ProverStateMachine;
import pl.edu.prz.klopusz.application.converters.MachineStateConverter;
import pl.edu.prz.klopusz.application.model.game.Board;

/**
 * Created by kamil on 19.06.17.
 */
public class StateMachineDecorator extends ProverStateMachine {

    private Board board;
    private TerritoryCalculator territoryCalculator;

    @Override
    public int getGoal(MachineState state, Role role) throws GoalDefinitionException {
        Role theRival = getTheSecondRole(role);

        int ourGoal = super.getGoal(state, role);
        int rivalGoal = super.getGoal(state, theRival);

        MachineStateConverter converter = new MachineStateConverter(state);

        board = converter.toBoard();
        territoryCalculator = new TerritoryCalculatorImpl(board);

        territoryCalculator.calculate();
        int whiteTerritory = computeWhiteTerritory();
        int blackTerritory = computeBlackTerritory();

        ourGoal += isWhite(role) ? (whiteTerritory-blackTerritory) : (blackTerritory-whiteTerritory);

        return ourGoal-rivalGoal;
    }

    private boolean isWhite(Role role) {
        final String WHITE = "white";
        String roleString = role.toString();

        return roleString.indexOf(WHITE) >= 0;
    }

    private int computeBlackTerritory() {
        return territoryCalculator.getBlack();
    }

    //TODO: implement
    private int computeWhiteTerritory() {
        return territoryCalculator.getWhite();
    }

    Role getTheSecondRole(Role role) throws GoalDefinitionException {
        Role theRival = roles.stream().filter(aRole -> !aRole.equals(role)).findFirst().orElse(null);
        if (theRival == null) {
            throw new GoalDefinitionException("no other role");
        }
        return theRival;
    }
}
