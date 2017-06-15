package pl.prz.edu.banan314.utilities;

import org.ggp.base.server.GameServer;

import pl.prz.edu.banan314.application.creators.ServerCreator;

/**
 * Created by kamil on 11.05.17.
 */
public class SimpleServerGameDolar extends SimpleGameDolar2 {

    private static ServerCreator serverCreator = new ServerCreator();

    public static void main(String[] argv) throws InterruptedException {
        GameServer theServer = serverCreator.createServer();

        theServer.addObserver(new ServerObserver(serverCreator.getMatch()) );

        theServer.start();
        theServer.join();
    }


}
