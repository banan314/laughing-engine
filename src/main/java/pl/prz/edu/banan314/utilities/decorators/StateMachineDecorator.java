package pl.prz.edu.banan314.utilities.decorators;

import org.ggp.base.util.statemachine.MachineState;
import org.ggp.base.util.statemachine.Role;
import org.ggp.base.util.statemachine.exceptions.GoalDefinitionException;
import org.ggp.base.util.statemachine.implementation.prover.ProverStateMachine;

/**
 * Created by kamil on 19.06.17.
 */
public class StateMachineDecorator extends ProverStateMachine {

    @Override
    public int getGoal(MachineState state, Role role) throws GoalDefinitionException {
        Role theRival = roles.stream().filter(aRole -> !aRole.equals(role)).findFirst().orElse(null);
        if (theRival == null) {
            throw new GoalDefinitionException("no other role");
        }

        int ourGoal = super.getGoal(state, role);
        int rivalGoal = super.getGoal(state, theRival);

        return ourGoal - rivalGoal;
    }
}
