package pl.prz.edu.banan314.application.commands.impl.player;

import pl.prz.edu.banan314.application.commands.Command;
import pl.prz.edu.banan314.application.creators.PlayerCreator;

/**
 * Created by kamil on 22.06.17.
 */
public abstract class PlayerCommand implements Command {
    PlayerCreator playerCreator = new PlayerCreator();
}
