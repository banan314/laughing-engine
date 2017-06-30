package pl.edu.prz.klopusz.application.creators;

/**
 * Created by kamil on 15.06.17.
 */

import org.ggp.base.server.GameServer;
import org.ggp.base.util.game.Game;
import org.ggp.base.util.match.Match;
import org.ggp.base.util.statemachine.Role;
import pl.edu.prz.klopusz.application.services.DolarGameCreator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ServerCreator extends DolarGameCreator {
    public static final Integer WHITE_PORT = 9147;
    public static final Integer BLACK_PORT = 9148;

    public Match getMatch() {
        return match;
    }

    Match match;

    public GameServer createServer() {
        final Game game = createDolarGame();

        match = new Match("simpleGameSim"+Match.getRandomString(5), -1, 0, 0, game, "");

        // Set up fake players to pretend to play the game
        List<String> fakeHosts = new ArrayList<String>();
        for(int i = 0; i < Role.computeRoles(game.getRules()).size(); i++) {
            fakeHosts.add("SamplePlayer"+i);
        }

        List<Integer> fakePorts = new ArrayList<Integer>();
        Arrays.stream(new Integer[]{WHITE_PORT, BLACK_PORT}).forEach(port -> fakePorts.add(port));

        // Set up a game server to play through the game, with all players playing randomly.
        final GameServer theServer = new GameServer(match, fakeHosts, fakePorts);

        return theServer;
    }
}
