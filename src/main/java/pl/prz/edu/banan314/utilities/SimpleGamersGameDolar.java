package pl.prz.edu.banan314.utilities;

import org.ggp.base.player.gamer.Gamer;
import org.ggp.base.player.gamer.exception.MetaGamingException;
import org.ggp.base.player.gamer.exception.MoveSelectionException;
import org.ggp.base.player.gamer.statemachine.sample.SampleNoopGamer;
import org.ggp.base.util.game.Game;
import org.ggp.base.util.gdl.grammar.GdlTerm;
import org.ggp.base.util.match.Match;
import org.ggp.base.util.statemachine.Role;
import pl.prz.edu.banan314.application.services.DolarGameCreator;

import java.util.List;

/**
 * Created by kamil on 11.06.17.
 */
public class SimpleGamersGameDolar extends DolarGameCreator {

    public static void main(String[] argv) throws InterruptedException {
        final Game game = createDolarGame();
        final Match theMatch = new Match("simpleGameSim"+Match.getRandomString(5), -1, 0, 0, game, "");

        Gamer[] players = new Gamer[2];
        for(int i = 0; i < players.length; i++) {
            players[i] = new SampleNoopGamer();
            players[i].setMatch(theMatch);
            try {
                players[i].metaGame(200);
            } catch (MetaGamingException e) {
                e.printStackTrace();
            }
        }

        List<Role> roles = Role.computeRoles(game.getRules());
        players[0].setRoleName(roles.get(0).getName());
        players[1].setRoleName(roles.get(1).getName());

        try {
            GdlTerm term = players[0].selectMove(800);
            System.out.println(term.toString());
        } catch (MoveSelectionException e) {
            e.printStackTrace();
        }
    }
}
