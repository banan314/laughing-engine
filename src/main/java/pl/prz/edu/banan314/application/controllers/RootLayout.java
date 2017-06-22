package pl.prz.edu.banan314.application.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import pl.prz.edu.banan314.application.commands.impl.CreatePlayerCommand;
import pl.prz.edu.banan314.application.commands.impl.ServerCommand;
import pl.prz.edu.banan314.application.commands.impl.StartServerCommand;
import pl.prz.edu.banan314.application.model.BoardModel;

/**
 * Created by kamil on 13.05.17.
 */
public class RootLayout {

    BoardModel boardModel;

    public void setBoardModel(BoardModel boardModel) {
        this.boardModel = boardModel;
    }

    public void newGameWithRandomPlayers() {
        CreatePlayerCommand createPlayerCommand = new CreatePlayerCommand(9147, "RandomGamer");
        createPlayerCommand.execute();

        createPlayerCommand.setPort(9148)
                .execute();

        StartServerCommand startServerCommand = new StartServerCommand(ServerCommand.getGameServer());
        startServerCommand.execute();

        assert boardModel != null;
        boardModel.initialize();
    }
}
