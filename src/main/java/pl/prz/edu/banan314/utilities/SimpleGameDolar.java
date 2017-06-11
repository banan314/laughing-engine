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


    public static void main(String[] argv) throws InterruptedException {
        final Game game = createDolarGame();
        final Match theMatch = new Match("simpleGameSim."+Match.getRandomString(5), -1, 0, 0, game, "");

        // Set up fake players to pretend to play the game
        List<String> fakeHosts = new ArrayList<String>();
        List<Integer> fakePorts = new ArrayList<Integer>();
        for(int i = 0; i < Role.computeRoles(game.getRules()).size(); i++) {
            fakeHosts.add("SamplePlayer"+i);
            fakePorts.add(9147+i);
        }

        // Set up a game server to play through the game, with all players playing randomly.
        final GameServer theServer = new GameServer(theMatch, fakeHosts, fakePorts);
        for(int i = 0; i < fakeHosts.size(); i++) {
            theServer.makePlayerPlayRandomly(i);
        }

        theServer.addObserver(new ServerObserver(theMatch) );

        theServer.start();
        theServer.join();
    }

    private static Game createDolarGame() {
        File dolarKif = fetchKif();

        String rulesheet = extractRuleSheet(dolarKif);
        return Game.createEphemeralGame(rulesheet);
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
            sb.append(line).append('\n');
        }
        return sb;
    }

    private static String cleanRuleSheet(String rulesheet) {
        rulesheet = Game.preprocessRulesheet(rulesheet);
        return rulesheet;
    }
}
