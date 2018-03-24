package pl.edu.prz.klopusz.utilities;

import org.ggp.base.server.GameServer;

import pl.edu.prz.klopusz.application.creators.ServerCreator;
import pl.edu.prz.klopusz.application.services.DolarGameCreator;
import pl.edu.prz.klopusz.application.creators.PlayerCreator;
import pl.edu.prz.klopusz.application.exceptions.InvalidPlayerException;

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
