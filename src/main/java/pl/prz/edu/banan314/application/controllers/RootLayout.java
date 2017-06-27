package pl.prz.edu.banan314.application.controllers;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import pl.prz.edu.banan314.application.DolarMainApp;
import pl.prz.edu.banan314.application.commands.ConfigurableCommand;
import pl.prz.edu.banan314.application.commands.impl.player.CreatePlayerCommand;
import pl.prz.edu.banan314.application.commands.impl.player.CreatePlayersCommand;
import pl.prz.edu.banan314.application.commands.impl.server.ServerCommand;
import pl.prz.edu.banan314.application.commands.impl.server.StartServerCommand;
import pl.prz.edu.banan314.application.model.BoardModel;

/**
 * Created by kamil on 13.05.17.
 */
public class RootLayout {

    BoardModel boardModel;
    DolarMainApp parentApp;

    public void setBoardModel(BoardModel boardModel) {
        this.boardModel = boardModel;
    }

    public void setParentApp(DolarMainApp parentApp) {
        this.parentApp = parentApp;
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

    @FXML
    public void closeApplication() {
        Platform.exit();
        System.exit(0);
    }

    @FXML
    public void handleRules() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Dolar");
        alert.setHeaderText("Rules");
        alert.setContentText("The game is played on the board of size\n"+"9 × 9 squares by two players, who put cross" +
                " and\n"+"circle (or white and black stones) alternately. The piece can be put if and only if there " +
                "is a\n"+"friendly piece on the adjacent square. The goal of game is to achieve more points than " +
                "the\n"+"rival. Counting takes place after the end of the game, when both players pass or the " +
                "board\n"+"is full. The point is given for:\n"+"• having a piece in opponent's camp (fragment of the " +
                "board in the corner, of size\n"+"• isolating an area, surrounding it with own pieces (it is similar " +
                "to go)");

        alert.showAndWait();
    }

    @FXML
    public void handleNewGame() {
        ConfigurableCommand command = new CreatePlayersCommand();
        parentApp.getBoardController().execute(command);
    }

    @FXML
    public void handleAbout() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Dolar");
        alert.setHeaderText("About");
        alert.setContentText("Author: Kamil Łopuszański\n" +
                "Thanks to: GGP project (ggp.org)");

        alert.showAndWait();
    }
}
