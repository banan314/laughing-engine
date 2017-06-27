package pl.prz.edu.banan314.application.commands.impl.player;

import pl.prz.edu.banan314.application.commands.Command;
import pl.prz.edu.banan314.application.creators.PlayerCreator;

import java.io.IOException;
import java.net.ServerSocket;

/**
 * Created by kamil on 22.06.17.
 */
public abstract class PlayerCommand implements Command {
    PlayerCreator playerCreator = new PlayerCreator();

    protected static boolean isPortOpen(int port) {
        ServerSocket serverSocket;
        try {
            serverSocket = new ServerSocket(port);
            return true;
        } catch (IOException e) {
            return false;
        } finally {
            serverSocket = null;
        }
    }
}
