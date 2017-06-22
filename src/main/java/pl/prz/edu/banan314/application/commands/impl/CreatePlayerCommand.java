package pl.prz.edu.banan314.application.commands.impl;

import pl.prz.edu.banan314.application.exceptions.InvalidPlayerException;

import java.io.IOException;

/**
 * Created by kamil on 22.06.17.
 */
public class CreatePlayerCommand extends PlayerCommand {

    private final int port;
    private final String name;

    CreatePlayerCommand(int port, String playerName) {
        this.port = port;
        this.name = playerName;
    }

    @Override
    public void execute() {
        try {
            playerCreator.createPlayer(port, name);
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
}
