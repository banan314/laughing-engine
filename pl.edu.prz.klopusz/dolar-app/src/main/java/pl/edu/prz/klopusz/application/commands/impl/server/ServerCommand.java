package pl.edu.prz.klopusz.application.commands.impl.server;

import org.ggp.base.server.GameServer;
import org.ggp.base.util.match.Match;
import pl.edu.prz.klopusz.application.commands.Command;
import pl.edu.prz.klopusz.application.creators.ServerCreator;

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
