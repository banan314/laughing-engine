package pl.prz.edu.banan314.utilities;

/**
 * Created by kamil on 11.06.17.
 */

import external.JSON.JSONException;
import external.JSON.JSONObject;
import org.ggp.base.server.event.ServerCompletedMatchEvent;
import org.ggp.base.server.event.ServerNewGameStateEvent;
import org.ggp.base.server.event.ServerNewMovesEvent;
import org.ggp.base.util.crypto.SignableJSON;
import org.ggp.base.util.gdl.grammar.GdlSentence;
import org.ggp.base.util.match.Match;
import org.ggp.base.util.observer.Event;
import org.ggp.base.util.observer.Observer;
import org.ggp.base.util.statemachine.MachineState;

import java.util.HashSet;
import java.util.Set;

public class ServerObserver implements Observer {

    public static final boolean hideStepCounter = true;
    public static final boolean hideControlProposition = true;
    public static final boolean showCurrentState = false;

    // TODO: Allow a custom state machine to be plugged into the GameServer so that we can
    // simulate games using this tool with custom state machines, to verify they're sane.

    final Set<GdlSentence> oldContents = new HashSet<GdlSentence>();
    final int[] nState = new int[1];

    Match theMatch;

    public ServerObserver(Match theMatch) {
        this.theMatch = theMatch;
    }

    @Override public void observe(Event event) {
        if (event instanceof ServerNewGameStateEvent) {
            MachineState theCurrentState = ((ServerNewGameStateEvent) event).getState();
            if (nState[0] > 0) {
                System.out.print("State["+nState[0]+"]: ");
            }
            Set<GdlSentence> newContents = theCurrentState.getContents();
            for(GdlSentence newSentence : newContents) {
                if (hideStepCounter && newSentence.toString().contains("step")) {
                    continue;
                }
                if (hideControlProposition && newSentence.toString().contains("control")) {
                    continue;
                }
                if (!oldContents.contains(newSentence)) {
                    System.out.print("+"+newSentence+", ");
                }
            }
            for(GdlSentence oldSentence : oldContents) {
                if (hideStepCounter && oldSentence.toString().contains("step")) {
                    continue;
                }
                if (hideControlProposition && oldSentence.toString().contains("control")) {
                    continue;
                }
                if (!newContents.contains(oldSentence)) {
                    System.out.print("-"+oldSentence+", ");
                }
            }
            System.out.println();
            oldContents.clear();
            oldContents.addAll(newContents);

            if (showCurrentState) {
                System.out.println("State["+nState[0]+"] Full: "+theCurrentState);
            }
            nState[0]++;
        } else if (event instanceof ServerNewMovesEvent) {
            System.out.println("Move taken: "+((ServerNewMovesEvent) event).getMoves());
        } else if (event instanceof ServerCompletedMatchEvent) {
            System.out.println("State["+nState[0]+"] Full (Terminal): "+oldContents);
            System.out.println("Match information: "+theMatch);
            System.out.println("Goals: "+((ServerCompletedMatchEvent) event).getGoals());
            try {
                System.out.println("Match information cryptographically signed? "+SignableJSON.isSignedJSON
                        (new JSONObject(theMatch.toJSON())));
                System.out.println("Match information cryptographic signature valid? "+SignableJSON
                        .verifySignedJSON(new JSONObject(theMatch.toJSON())));
            } catch (JSONException je) {
                je.printStackTrace();
            }
            System.out.println("Game over.");
        }
    }
}
