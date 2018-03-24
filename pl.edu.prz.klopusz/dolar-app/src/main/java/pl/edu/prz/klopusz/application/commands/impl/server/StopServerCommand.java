package pl.edu.prz.klopusz.application.commands.impl.server;

import pl.edu.prz.klopusz.application.commands.impl.player.StopPlayersCommand;

/**
 * Created by kamil on 25.06.17.
 */
public class StopServerCommand extends ServerCommand {

    @Override
    public void execute() {
        new StopPlayersCommand().execute();

        if(gameServer != null) {
            gameServer.abort();
            gameServer = null;
            System.gc();
        }
    }
}
