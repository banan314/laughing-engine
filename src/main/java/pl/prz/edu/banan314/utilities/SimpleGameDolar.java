package pl.prz.edu.banan314.utilities;

import external.JSON.JSONException;
import external.JSON.JSONObject;
import org.ggp.base.server.GameServer;
import org.ggp.base.server.event.ServerCompletedMatchEvent;
import org.ggp.base.server.event.ServerNewGameStateEvent;
import org.ggp.base.server.event.ServerNewMovesEvent;
import org.ggp.base.util.crypto.SignableJSON;
import org.ggp.base.util.game.Game;
import org.ggp.base.util.gdl.grammar.GdlSentence;
import org.ggp.base.util.match.Match;
import org.ggp.base.util.observer.*;
import org.ggp.base.util.statemachine.MachineState;
import org.ggp.base.util.statemachine.Role;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.*;

/**
 * Created by kamil on 11.05.17.
 */
public class SimpleGameDolar {
    public static final boolean hideStepCounter = true;
    public static final boolean hideControlProposition = true;
    public static final boolean showCurrentState = false;

    public static void main(String[] argv) throws InterruptedException {
        File dolarKif = fetchKif();


        String rulesheet = extractRuleSheet(dolarKif);
        final Game game = Game.createEphemeralGame(rulesheet);
        final Match theMatch = new Match("simpleGameSim." + Match.getRandomString(5), -1, 0, 0, game, "");

        // Set up fake players to pretend to play the game
        List<String> fakeHosts = new ArrayList<String>();
        List<Integer> fakePorts = new ArrayList<Integer>();
        for (int i = 0; i < Role.computeRoles(game.getRules()).size(); i++) {
            fakeHosts.add("SamplePlayer" + i);
            fakePorts.add(9147+i);
        }

        // Set up a game server to play through the game, with all players playing randomly.
        final GameServer theServer = new GameServer(theMatch, fakeHosts, fakePorts);
        for (int i = 0; i < fakeHosts.size(); i++) {
            theServer.makePlayerPlayRandomly(i);
        }

        // TODO: Allow a custom state machine to be plugged into the GameServer so that we can
        // simulate games using this tool with custom state machines, to verify they're sane.

        final Set<GdlSentence> oldContents = new HashSet<GdlSentence>();
        final int[] nState = new int[1];
        theServer.addObserver(new org.ggp.base.util.observer.Observer() {
            @Override
            public void observe(Event event) {
                if (event instanceof ServerNewGameStateEvent) {
                    MachineState theCurrentState = ((ServerNewGameStateEvent)event).getState();
                    if(nState[0] > 0) System.out.print("State[" + nState[0] + "]: ");
                    Set<GdlSentence> newContents = theCurrentState.getContents();
                    for(GdlSentence newSentence : newContents) {
                        if(hideStepCounter && newSentence.toString().contains("step")) continue;
                        if(hideControlProposition && newSentence.toString().contains("control")) continue;
                        if(!oldContents.contains(newSentence)) {
                            System.out.print("+" + newSentence + ", ");
                        }
                    }
                    for(GdlSentence oldSentence : oldContents) {
                        if(hideStepCounter && oldSentence.toString().contains("step")) continue;
                        if(hideControlProposition && oldSentence.toString().contains("control")) continue;
                        if(!newContents.contains(oldSentence)) {
                            System.out.print("-" + oldSentence + ", ");
                        }
                    }
                    System.out.println();
                    oldContents.clear();
                    oldContents.addAll(newContents);

                    if(showCurrentState) System.out.println("State[" + nState[0] + "] Full: " + theCurrentState);
                    nState[0]++;
                } else if (event instanceof ServerNewMovesEvent) {
                    System.out.println("Move taken: " + ((ServerNewMovesEvent)event).getMoves());
                } else if (event instanceof ServerCompletedMatchEvent) {
                    System.out.println("State[" + nState[0] + "] Full (Terminal): " + oldContents);
                    System.out.println("Match information: " + theMatch);
                    System.out.println("Goals: " + ((ServerCompletedMatchEvent)event).getGoals());
                    try {
                        System.out.println("Match information cryptographically signed? " + SignableJSON.isSignedJSON(new JSONObject(theMatch.toJSON())));
                        System.out.println("Match information cryptographic signature valid? " + SignableJSON.verifySignedJSON(new JSONObject(theMatch.toJSON())));
                    } catch (JSONException je) {
                        je.printStackTrace();
                    }
                    System.out.println("Game over.");
                }
            }
        });

        theServer.start();
        theServer.join();
    }

    static File fetchKif() {
        return new File("./games/games/dolar/dolar.kif");
    }

    static String extractRuleSheet(File dolarKif) {
        String rulesheet = null;
        rulesheet = readKif(dolarKif).toString();
        rulesheet = cleanRuleSheet(rulesheet);
        return rulesheet;
    }

    static StringBuffer readKif(File dolarKif) {
        StringBuffer sb = new StringBuffer();
        List<String> lines = null;
        try {
            lines = Files.readAllLines(dolarKif.toPath(), Charset.defaultCharset());
        } catch (IOException e) {
            e.printStackTrace();
        }
        for(String line : lines) {
            sb.append(line)
                .append('\n');
        }
        return sb;
    }

    private static String cleanRuleSheet(String rulesheet) {
        rulesheet = Game.preprocessRulesheet(rulesheet);
        return rulesheet;
    }
}
