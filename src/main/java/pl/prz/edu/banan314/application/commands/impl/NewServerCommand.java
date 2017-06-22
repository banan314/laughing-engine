package pl.prz.edu.banan314.application.commands.impl;

/**
 * Created by kamil on 22.06.17.
 */
public class NewServerCommand extends ServerCommand {
    @Override
    public void execute() {
        gameServer = serverCreator.createServer();
    }
}
