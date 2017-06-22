package pl.prz.edu.banan314.utilities;

import org.ggp.base.server.GameServer;

import pl.prz.edu.banan314.application.creators.PlayerCreator;
import pl.prz.edu.banan314.application.creators.ServerCreator;
import pl.prz.edu.banan314.application.exceptions.InvalidPlayerException;
import pl.prz.edu.banan314.application.services.DolarGameCreator;

import java.io.IOException;

/**
 * Created by kamil on 11.05.17.
 */
public class SimpleServerGameDolar extends DolarGameCreator {

    static ServerCreator serverCreator = new ServerCreator();
    static PlayerCreator playerCreator = new PlayerCreator();

    public static void main(String[] argv) throws InterruptedException, IOException, InstantiationException,
            IllegalAccessException, InvalidPlayerException {
        GameServer theServer = serverCreator.createServer();

        theServer.addObserver(new DebugServerObserver(serverCreator.getMatch()) );

        playerCreator.createPlayer(9147, "RandomGamer");
        playerCreator.createPlayer(9148, "RandomGamer");

        theServer.start();
        theServer.join();
    }


}
