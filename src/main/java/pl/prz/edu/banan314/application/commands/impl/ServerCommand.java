package pl.prz.edu.banan314.application.commands.impl;

import org.ggp.base.server.GameServer;
import pl.prz.edu.banan314.application.commands.Command;
import pl.prz.edu.banan314.application.creators.ServerCreator;

/**
 * Created by kamil on 22.06.17.
 */
public abstract class ServerCommand implements Command {

    ServerCreator serverCreator = new ServerCreator();
    GameServer gameServer;

}
