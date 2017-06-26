package pl.prz.edu.banan314.application.commands.impl;

import org.ggp.base.server.GameServer;

/**
 * Created by kamil on 22.06.17.
 */
public class StartServerCommand extends ServerCommand {

    public StartServerCommand(GameServer gameServer) {
        this.gameServer = gameServer;
    }

    @Override
    public void execute() {
        gameServer.start();
    }
}
