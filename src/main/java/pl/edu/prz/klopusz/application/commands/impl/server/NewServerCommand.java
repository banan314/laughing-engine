package pl.edu.prz.klopusz.application.commands.impl.server;

import pl.edu.prz.klopusz.application.creators.BoardCreator;

/**
 * Created by kamil on 22.06.17.
 */
public class NewServerCommand extends ServerCommand {
    @Override
    public void execute() {
        gameServer = serverCreator.createServer();
        BoardCreator.getInstance().fillServerTraits();
    }
}
