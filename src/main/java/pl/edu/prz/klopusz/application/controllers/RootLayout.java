package pl.edu.prz.klopusz.application.controllers;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.Region;
import javafx.stage.FileChooser;
import lombok.val;
import pl.edu.prz.klopusz.application.common.ThreadHelper;
import pl.edu.prz.klopusz.application.model.BoardModel;
import pl.edu.prz.klopusz.application.DolarMainApp;
import pl.edu.prz.klopusz.application.commands.ConfigurableCommand;
import pl.edu.prz.klopusz.application.commands.impl.player.CreatePlayerCommand;
import pl.edu.prz.klopusz.application.commands.impl.player.CreatePlayersCommand;
import pl.edu.prz.klopusz.application.commands.impl.server.ServerCommand;
import pl.edu.prz.klopusz.application.commands.impl.server.StartServerCommand;
import pl.edu.prz.klopusz.application.model.game.SavedState;

import java.io.*;
import java.net.URL;
import java.nio.channels.ScatteringByteChannel;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.util.ResourceBundle;
import java.util.Scanner;
import java.util.logging.Logger;

import static pl.edu.prz.klopusz.application.common.Messages.*;

/**
 * Created by kamil on 13.05.17.
 */
public class RootLayout {

    private static final Logger LOG = Logger.getLogger("Root Layout");
    BoardModel boardModel;
    DolarMainApp parentApp;

    @FXML private Label leftStatus;
    @FXML private Label rightStatus;
    @FXML private MenuItem miStopEngines;
    @FXML private RadioMenuItem enginesRadio;

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
    public void saveBoard() {
        val fileChooser = new FileChooser();
        fileChooser.setTitle("Save board in PDN");
        File file = fileChooser.showSaveDialog(parentApp.getStage());
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("PDN", "*.pdn"),
                new FileChooser.ExtensionFilter("All files", "*.*")
        );

        val savedState = new SavedState(parentApp.getBoardController().boardModel.getBoard()).turn(boardModel.whoseTurn());
        try {
            val outputStream = new FileOutputStream(file);
            outputStream.write(savedState.toBytes());
        } catch (FileNotFoundException e) {
            LOG.severe("file " + file.getAbsolutePath() + " not found");
        } catch (IOException e) {
            LOG.severe("IO exception writing to file " + file.getAbsolutePath());
        }
    }

    @FXML
    public void loadBoard() {
        val fileChooser = new FileChooser();
        fileChooser.setTitle("Open board PDN");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("PDN", "*.pdn"),
                new FileChooser.ExtensionFilter("All files", "*.*")
        );
        File file = fileChooser.showOpenDialog(parentApp.getStage());

        pl.edu.prz.klopusz.application.model.game.Board board;
        try {
            Scanner scanner = new Scanner(file);
            board = SavedState.loadBoard(scanner.nextLine());
            val turn = SavedState.loadColor(scanner.nextLine());
//            parentApp.getBoardController().updateBoard(board);
            boardModel.updateBoard(board);
            parentApp.getBoardController().turn(turn);
        } catch (FileNotFoundException e) {
            LOG.severe("file " + file.getAbsolutePath() + " not found");
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
        enginesRadio.setSelected(true);
    }

    @FXML
    public void newGameWithRandomPlayers() {
        setComboBoxesToRandoms();

        CreatePlayerCommand createPlayerCommand = new CreatePlayerCommand(9147, "RandomGamer");
        createPlayerCommand.execute();

        createPlayerCommand.setPort(9148).execute();

        changeModeToEngines();

        prepareGameEnvironment();

        showLeftStatus(NEW_RANDOM_GAME);
    }

    private void setComboBoxesToRandoms() {
        parentApp.getBoardController().setComboBoxesToRandoms();
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
        alert.setWidth(400);
        alert.setHeight(400);
        alert.setTitle("Dolar");
        alert.setHeaderText("Rules");
        alert.setContentText("The game is played on the board of size\n"+"9 × 9 squares by two players, who put " +
                "cross"+" and\n"+"circle (or stones of different colors) alternately. The piece can be put if and " +
                "only if" +
                " there "+"is a\n"+"friendly piece on the adjacent square. The goal of game is to achieve more points" +
                " than "+"the\n"+"rival. Counting takes place after the end of the game, when both players pass or " +
                "the "+"board\n"+"is full. The point is given for:\n"+"• having a piece in opponent's camp (fragment " +
                "of the "+"board in the corner, of size 4x4\n"+"• isolating an area, surrounding it with own pieces " +
                "(it " +
                "is similar "+"to go)");
        alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
        alert.show();
    }

    @FXML
    public void handleGameStop() {
        parentApp.getBoardController().stopEngineGame();
    }

    @FXML
    public void handleEnginesMode() {
        changeModeToEngines();
        miStopEngines.setDisable(false);
    }

    @FXML
    public void handlePlayersMode() {
        parentApp.getBoardController().setGameMode(Board.GameMode.PLAYERS);
        miStopEngines.setDisable(true);
    }

    @FXML
    public void handlePlayerEngineMode() {
        parentApp.getBoardController().setGameMode(Board.GameMode.PLAYER_ENGINE);
        miStopEngines.setDisable(false);
    }

    @FXML
    public void handleAbout() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Dolar 1.2");
        alert.setHeaderText("About");
        alert.setContentText("Author: Kamil Łopuszański\n"+
                "University: Rzeszow University of Technical Sciences\n" +
                "Thanks to: GGP project (ggp.org)");
        alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
        alert.showAndWait();
    }
}
