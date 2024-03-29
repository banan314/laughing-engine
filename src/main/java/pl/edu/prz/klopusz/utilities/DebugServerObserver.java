package pl.edu.prz.klopusz.utilities;

/**
 * Created by kamil on 11.06.17.
 */

import org.ggp.base.server.event.ServerCompletedMatchEvent;
import org.ggp.base.server.event.ServerNewGameStateEvent;
import org.ggp.base.server.event.ServerNewMovesEvent;
import org.ggp.base.util.gdl.grammar.GdlSentence;
import org.ggp.base.util.match.Match;
import org.ggp.base.util.observer.Event;
import org.ggp.base.util.statemachine.MachineState;

import java.util.Set;

public class DebugServerObserver extends ServerObserver {

    public static final boolean hideStepCounter = true;
    public static final boolean hideControlProposition = true;
    public static final boolean showCurrentState = false;
    public static final boolean showMatchInformation = false;

    // TODO: Allow a custom state machine to be plugged into the GameServer so that we can
    // simulate games using this tool with custom state machines, to verify they're sane.

    int nState = 0;
    Match theMatch;

    public DebugServerObserver(Match theMatch) {
        super(theMatch);
    }

    @Override
    public void observe(Event event) {
        if (event instanceof ServerNewGameStateEvent) {
            handleNewState((ServerNewGameStateEvent) event);
        } else if (event instanceof ServerNewMovesEvent) {
            handleNewMoves((ServerNewMovesEvent) event);
        } else if (event instanceof ServerCompletedMatchEvent) {
            handleCompletedMatch((ServerCompletedMatchEvent) event);
        }
    }

    protected void handleNewState(ServerNewGameStateEvent event) {
        MachineState theCurrentState = event.getState();
        /*if (nState > 0) {
            System.out.print("State["+nState+"]: ");
        }*/

        Set<GdlSentence> newContents = theCurrentState.getContents();
        processContents(newContents, oldContents, "+");
//        processContents(oldContents, newContents, "-");

        oldContents.clear();
        oldContents.addAll(newContents);

        if (showCurrentState) {
            System.out.println("State["+nState+"] Full: "+theCurrentState);
        }

        System.out.println();

        nState++;
    }

    private void processContents(Set<GdlSentence> contents, Set<GdlSentence> other, String sign) {
        for(GdlSentence sentence : contents) {
            if (checkForStepAndControl(sentence)) {
                continue;
            }
            if (!other.contains(sentence)) {
                System.out.print(sign+sentence+", ");
            }
        }
    }

    private boolean checkForStepAndControl(GdlSentence newSentence) {
        if (hideStepCounter && newSentence.toString().contains("step")) {
            return true;
        }
        if (hideControlProposition && newSentence.toString().contains("control")) {
            return true;
        }
        return false;
    }

    protected void handleNewMoves(ServerNewMovesEvent event) {
        System.out.println("Move taken: "+event.getMoves());
    }

    protected void handleCompletedMatch(ServerCompletedMatchEvent event) {
        System.out.println("State["+nState+"] Full (Terminal): "+oldContents);
        if (showMatchInformation) {
            System.out.println("Match information: "+theMatch);
        }
        System.out.println("Goals: "+event.getGoals());
        System.out.println("Game over.");
    }
}
