package pl.prz.edu.banan314.application.commands.impl;

/**
 * Created by kamil on 25.06.17.
 */
public class StopServerCommand extends ServerCommand {

    @Override
    public void execute() {
        try {
            gameServer.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //FIXME: it won't work
        gameServer.abort();
    }
}
