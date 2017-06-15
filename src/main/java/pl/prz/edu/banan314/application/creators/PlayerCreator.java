package pl.prz.edu.banan314.application.creators;

import org.ggp.base.player.GamePlayer;
import org.ggp.base.player.gamer.Gamer;
import org.ggp.base.util.reflection.ProjectSearcher;
import pl.prz.edu.banan314.application.exceptions.InvalidPlayerException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by kamil on 15.06.17.
 */
public class PlayerCreator {

    public static final Integer WHITE_PORT = 9147;
    public static final Integer BLACK_PORT = 9148;

    public void createWhitePlayer(int port, String name) throws IOException, InstantiationException, IllegalAccessException, InvalidPlayerException {
        Class<?> chosenGamerClass = null;

        List<String> availableGamers = new ArrayList<String>();
        for (Class<?> gamerClass : ProjectSearcher.GAMERS.getConcreteClasses()) {
            availableGamers.add(gamerClass.getSimpleName());
            if (gamerClass.getSimpleName().equals(name)) {
                chosenGamerClass = gamerClass;
            }
        }

        if (chosenGamerClass == null) {
            throw new InvalidPlayerException("Could not find player class with that name. Available choices are: " + Arrays.toString
                    (availableGamers.toArray()));
        }

        Gamer gamer = (Gamer) chosenGamerClass.newInstance();
        new GamePlayer(port, gamer).start();
    }
}
