package pl.prz.edu.banan314.application.commands.impl.server;

/**
 * Created by kamil on 25.06.17.
 */
public class StopServerCommand extends ServerCommand {

    @Override
    public void execute() {
        if(gameServer != null) {
            gameServer.abort();
            gameServer = null;
            System.gc();
        }
    }
}
