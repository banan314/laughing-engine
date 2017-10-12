package pl.edu.prz.klopusz.application.controllers;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import pl.edu.prz.klopusz.application.common.ThreadHelper;
import pl.edu.prz.klopusz.application.model.BoardModel;
import pl.edu.prz.klopusz.application.DolarMainApp;
import pl.edu.prz.klopusz.application.commands.ConfigurableCommand;
import pl.edu.prz.klopusz.application.commands.impl.player.CreatePlayerCommand;
import pl.edu.prz.klopusz.application.commands.impl.player.CreatePlayersCommand;
import pl.edu.prz.klopusz.application.commands.impl.server.ServerCommand;
import pl.edu.prz.klopusz.application.commands.impl.server.StartServerCommand;

import static pl.edu.prz.klopusz.application.common.Messages.*;

/**
 * Created by kamil on 13.05.17.
 */
public class RootLayout {

    BoardModel boardModel;
    DolarMainApp parentApp;

    @FXML private Label leftStatus;
    @FXML private Label rightStatus;
    @FXML private MenuItem miStopEngines;

    public void setBoardModel(BoardModel boardModel) {
        this.boardModel = boardModel;
    }

    public void setParentApp(DolarMainApp parentApp) {
        this.parentApp = parentApp;
    }

    public void showLeftStatus(String leftStatus) {
        if (Platform.isFxApplicationThread()) {
            this.leftStatus.setText(leftStatus);
        } else {
            Platform.runLater(() -> this.leftStatus.setText(leftStatus));
            ThreadHelper.sleep(30);
        }
    }

    public void showRightStatus(String rightStatus) {
        if (Platform.isFxApplicationThread()) {
            this.rightStatus.setText(rightStatus);
        } else {
            Platform.runLater(() -> this.rightStatus.setText(rightStatus));
            ThreadHelper.sleep(30);
        }
    }

    @FXML
    public void handleNewGame() {
        switch (parentApp.getBoardController().getGameMode()) {
            case PLAYERS:
                parentApp.getBoardController().handleNewGame();
                break;
            case ENGINES:
                ConfigurableCommand command = new CreatePlayersCommand();
                parentApp.getBoardController().execute(command);

                changeModeToEngines();

                prepareGameEnvironment();
                break;
        }

        showLeftStatus(NEW_GAME);
    }

    private void changeModeToEngines() {
        parentApp.getBoardController().setGameMode(Board.GameMode.ENGINES);
    }

    @FXML
    public void newGameWithRandomPlayers() {
        CreatePlayerCommand createPlayerCommand = new CreatePlayerCommand(9147, "RandomGamer");
        createPlayerCommand.execute();

        createPlayerCommand.setPort(9148).execute();

        changeModeToEngines();

        prepareGameEnvironment();

        showLeftStatus(NEW_RANDOM_GAME);
    }

    private void prepareGameEnvironment() {
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
        alert.setContentText("The game is played on the board of size\n"+"9 × 9 squares by two players, who put " +
                "cross"+" and\n"+"circle (or white and black stones) alternately. The piece can be put if and only if" +
                " there "+"is a\n"+"friendly piece on the adjacent square. The goal of game is to achieve more points" +
                " than "+"the\n"+"rival. Counting takes place after the end of the game, when both players pass or " +
                "the "+"board\n"+"is full. The point is given for:\n"+"• having a piece in opponent's camp (fragment " +
                "of the "+"board in the corner, of size\n"+"• isolating an area, surrounding it with own pieces (it " +
                "is similar "+"to go)");

        alert.showAndWait();
    }

    @FXML
    public void handleGameStop() {
        parentApp.getBoardController().stopEngineGame();
    }

    @FXML
    public void handleEnginesMode() {
        changeModeToEngines();
        miStopEngines.setDisable(true);
    }

    @FXML
    public void handlePlayersMode() {
        parentApp.getBoardController().setGameMode(Board.GameMode.PLAYERS);
        miStopEngines.setDisable(false);
    }

    @FXML
    public void handlePlayerEngineMode() {
        parentApp.getBoardController().setGameMode(Board.GameMode.PLAYER_ENGINE);
        miStopEngines.setDisable(true);
    }

    @FXML
    public void handleAbout() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Dolar");
        alert.setHeaderText("About");
        alert.setContentText("Author: Kamil Łopuszański\n"+"Thanks to: GGP project (ggp.org)");

        alert.showAndWait();
    }
}
