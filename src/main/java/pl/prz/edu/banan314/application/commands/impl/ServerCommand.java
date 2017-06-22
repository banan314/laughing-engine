package pl.prz.edu.banan314.application.commands.impl;

import org.ggp.base.server.GameServer;
import org.ggp.base.util.match.Match;
import pl.prz.edu.banan314.application.commands.Command;
import pl.prz.edu.banan314.application.creators.ServerCreator;

/**
 * Created by kamil on 22.06.17.
 */
public abstract class ServerCommand implements Command {

    static ServerCreator serverCreator = new ServerCreator();
    static GameServer gameServer;

    public static GameServer getGameServer() {
        return gameServer;
    }

    public static Match getMatch() {
        return serverCreator.getMatch();
    }
}
