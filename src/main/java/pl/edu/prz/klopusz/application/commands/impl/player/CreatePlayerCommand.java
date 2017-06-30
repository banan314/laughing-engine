package pl.edu.prz.klopusz.application.commands.impl.player;

import pl.edu.prz.klopusz.application.exceptions.InvalidPlayerException;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by kamil on 22.06.17.
 */
public class CreatePlayerCommand extends PlayerCommand {

    private int port;
    private String name;
    static Map<Integer, Boolean> playing = new HashMap<>();

    public CreatePlayerCommand(int port, String playerName) {
        this.port = port;
        this.name = playerName;
    }

    @Override
    public void execute() {
        if(playing.containsKey(port)) {
            if(playing.get(port))
                return;
        }
        try {
            playerCreator.createPlayer(port, name);
            playing.put(port, true);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvalidPlayerException e) {
            e.printStackTrace();
        }
    }

    public CreatePlayerCommand setPort(int port) {
        this.port = port;
        return this;
    }
}
