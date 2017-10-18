package pl.edu.prz.klopusz.application.controllers;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Sphere;
import lombok.val;
import org.ggp.base.util.observer.Event;
import org.ggp.base.util.observer.Observer;
import pl.edu.prz.klopusz.application.DolarMainApp;
import pl.edu.prz.klopusz.application.commands.ConfigurableCommand;
import pl.edu.prz.klopusz.application.commands.impl.ShowRightStatusCommand;
import pl.edu.prz.klopusz.application.commands.impl.server.StopServerCommand;
import pl.edu.prz.klopusz.application.common.Assets;
import pl.edu.prz.klopusz.application.common.Messages;
import pl.edu.prz.klopusz.application.common.Point;
import pl.edu.prz.klopusz.application.common.ThreadHelper;
import pl.edu.prz.klopusz.application.common.calculator.CampCalculator;
import pl.edu.prz.klopusz.application.common.calculator.PayoutCalculator;
import pl.edu.prz.klopusz.application.model.BoardModel;
import pl.edu.prz.klopusz.application.model.ConfigurationModel;
import pl.edu.prz.klopusz.application.model.event.BoardEvent;
import pl.edu.prz.klopusz.application.model.event.EndEvent;
import pl.edu.prz.klopusz.application.model.event.MoveEvent;
import pl.edu.prz.klopusz.application.model.game.Move;
import pl.edu.prz.klopusz.application.model.game.Piece;
import pl.edu.prz.klopusz.application.model.game.Square;
import pl.edu.prz.klopusz.utilities.decorators.ScoreCalculator;
import pl.edu.prz.klopusz.utilities.decorators.TerritoryCalculatorImpl;

import java.net.URL;
import java.util.ResourceBundle;

import static pl.edu.prz.klopusz.application.common.Messages.BLACK_PASSED;
import static pl.edu.prz.klopusz.application.common.Messages.GAME_FINISHED;
import static pl.edu.prz.klopusz.application.common.Messages.WHITE_PASSED;
import static pl.edu.prz.klopusz.application.controllers.Board.GameMode.*;

import java.util.logging.Logger;

/**
 * Created by kamil on 10.06.17.
 */
public class Board implements Observer, Initializable {

    GameMode gameMode = PLAYERS;
    boolean gameEnded = false;

    @FXML private Sphere whitePiece;
    @FXML private Sphere blackPiece;
    @FXML private Group board;
    @FXML private AnchorPane root;
    @FXML private Group piecesGroup;
    @FXML private ChoiceBox<String> whiteEngineChoiceBox;
    @FXML private ChoiceBox<String> blackEngineChoiceBox;
    @FXML private Label whiteGoal;
    @FXML private Label blackGoal;
    @FXML private Button whitePass;
    @FXML private Button blackPass;
    @FXML private Button flipPlayer;
    @FXML private Label youPlayAsLabel;
    @FXML private Label whitePlayerName;
    @FXML private Label blackPlayerName;
    BoardModel boardModel;
    private ConfigurationModel configurationModel;
    private GameWithEngine gameWithEngine;
    private PayoutCalculator payoutCalculator;

    DolarMainApp parentApp;

    private Logger LOG = Logger.getLogger("Board Controller");

    @Override // This method is called by the FXMLLoader when initialization is complete
    public void initialize(URL fxmlFileLocation, ResourceBundle resources) {
        whiteEngineChoiceBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                configurationModel.setWhiteEngine(newValue);
            }
        });
        blackEngineChoiceBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                configurationModel.setBlackEngine(newValue);
            }
        });

        initializeLabels();
        initializePieces();
        updateViewAccordingTo(GameMode.PLAYERS);
    }

    private void initializePayoutCalculator() {
        ScoreCalculator territoryCalculator = new TerritoryCalculatorImpl(boardModel.getBoard());
        ScoreCalculator campCalculator = new CampCalculator(boardModel.getBoard());
        payoutCalculator = new PayoutCalculator().calculateTerritoryUsing(territoryCalculator).calculateCampUsing
                (campCalculator).observeBoardModel(boardModel);
    }

    private void initializeLabels() {
        whitePlayerName.setText(Assets.playerName(Piece.Color.WHITE));
        blackPlayerName.setText(Assets.playerName(Piece.Color.BLACK));
    }

    private void disablePassButtons(boolean white, boolean black) {
        whitePass.setDisable(white);
        blackPass.setDisable(black);
    }

    private void enableOnly1PassButton(Piece.Color active) {
        switch (active) {
            case WHITE:
                disablePassButtons(false, true);
                break;
            case BLACK:
                disablePassButtons(true, false);
                break;
        }
    }

    private void initializePieces() {
        whitePiece.setMaterial(BoardFactory.whitePieceMaterial);
        blackPiece.setMaterial(BoardFactory.blackPieceMaterial);
    }

    public void setGameMode(GameMode gameMode) {
        this.gameMode = gameMode;
        updateViewAccordingTo(gameMode);
    }

    private void updateViewAccordingTo(GameMode gameMode) {
        if (gameMode == PLAYER_ENGINE) {
            gameWithEngine = new GameWithEngineImpl();
            gameWithEngine.setBoardModel(boardModel);
            gameWithEngine.setPlayerColor(Piece.Color.WHITE);
            triggerGameWithEngineIfNeed();
            updatePlayAsLabel();
            youPlayAsLabel.setVisible(true);
        } else {
            youPlayAsLabel.setVisible(false);
            if (gameWithEngine != null) {
                boardModel.removeObserver(((Observer) gameWithEngine));
                gameWithEngine = null;
                System.gc();
            }
        }
        updateButtonsFeatures();
    }

    private void updateButtonsFeatures() {
        switch (gameMode) {
            case PLAYERS:
                flipPlayer.setDisable(true);
                disablePassButtons(false, true);
                break;
            case ENGINES:
                flipPlayer.setDisable(true);
                disablePassButtons(true, true);
                break;
            case PLAYER_ENGINE:
                flipPlayer.setDisable(false);
                enableOnly1PassButton(gameWithEngine.getPlayerColor());
                break;
        }
    }

    private void triggerGameWithEngineIfNeed() {
        if (boardModel.whoseTurn() != gameWithEngine.getPlayerColor()) {
            gameWithEngine.makeMove();
        }
    }

    public void setBoardModel(BoardModel boardModel) {
        whiteGoal.textProperty().unbind();
        blackGoal.textProperty().unbind();

        this.boardModel = boardModel;

        whiteGoal.textProperty().bind(boardModel.getWhiteGoalProperty());
        blackGoal.textProperty().bind(boardModel.getBlackGoalProperty());
    }

    public void setConfigurationModel(ConfigurationModel configurationModel) {
        this.configurationModel = configurationModel;

        configurationModel.setWhiteEngine(whiteEngineChoiceBox.getSelectionModel().getSelectedItem());
        configurationModel.setBlackEngine(blackEngineChoiceBox.getSelectionModel().getSelectedItem());
    }

    public void setParentApp(DolarMainApp parentApp) {
        this.parentApp = parentApp;
    }

    @FXML
    public void onSquareClick(MouseEvent event) {
        if (shouldIgnoreMove()) {
            return;
        }

        Rectangle square = (Rectangle) event.getSource();

        try {
            val point = rectangle2Point(square);
            LOG.info("clicked: (x="+point.x+", y="+point.y+")");
            val playerMoving = boardModel.whoseTurn();
            Move move = new Move();
            move.setRow(point.y);
            move.setFile(point.x);
            move.setPiece(new Piece(playerMoving));

            if (boardModel.isLegal(move)) {
                boardModel.makeMove(move);
                putSquarePiece(square, playerMoving);
            } else {
                LOG.info("move not legal; "+"turn: "+playerMoving);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void handlePass(MouseEvent event) {
        if (shouldIgnoreMove()) {
            return;
        }

        Node passButton = (Node) event.getSource();
        if (passButton.getId().equals("whitePass")) {
            parentApp.showLeftStatus(WHITE_PASSED);
            boardModel.makeMove(Move.pass(Piece.Color.WHITE));
        } else if (passButton.getId().equals("blackPass")) {
            parentApp.showLeftStatus(BLACK_PASSED);
            boardModel.makeMove(Move.pass(Piece.Color.BLACK));
        }
        updatePassDisability();
    }

    private void updatePassDisability() {
        enableOnly1PassButton(boardModel.whoseTurn());
    }

    @FXML
    private void flipPlayer(MouseEvent event) {
        gameWithEngine.swapPlayer();
        updatePlayAsLabel();
        triggerGameWithEngineIfNeed(); // assuming mode PLAYER_ENGINE
    }

    private void updatePlayAsLabel() {
        youPlayAsLabel.setText(String.format("You play as: %s", Assets.playerName(gameWithEngine.getPlayerColor())
                .toUpperCase()));
    }

    private boolean shouldIgnoreMove() {
        return gameMode == ENGINES || gameEnded;
    }

    private Point<Integer> rectangle2Point(Rectangle rectangle) throws Exception {
        int which = 0;
        for(Node square : board.getChildren()) {
            Rectangle squareRectangle = (Rectangle) square;
            if (rectangle == squareRectangle) {
                return new Point(which % pl.edu.prz.klopusz.application.model.game.Board.MAX_INDEX+1, pl.edu.prz
                        .klopusz.application.model.game.Board.MAX_INDEX-which / pl.edu.prz.klopusz.application.model
                        .game.Board.MAX_INDEX);
            }
            which++;
        }
        throw new Exception("not in board children");
    }

    public void updateBoard(pl.edu.prz.klopusz.application.model.game.Board board) {
        takeOffPiecesFromBoard();
        for(int i = pl.edu.prz.klopusz.application.model.game.Board.MIN_INDEX; i <= pl.edu.prz.klopusz.application
                .model.game.Board.MAX_INDEX; i++) {
            for(int j = pl.edu.prz.klopusz.application.model.game.Board.MIN_INDEX; j < pl.edu.prz.klopusz.application
                    .model.game.Board.MAX_INDEX; j++) {
                //if empty do nothing
                Square square = board.get(i, j);
                if (square.isEmpty()) {
                    continue;
                }
                putSquarePiece(getRectangle(i, j), square.getPiece().getColor());
            }
        }
    }

    @FXML
    private void handleClearBoard() {
        initializeGame();
    }

    private void takeOffPiecesFromBoard() {
        LOG.info("take off pieces");
        if (Platform.isFxApplicationThread()) {
            removeCircles();
        } else {
            Platform.runLater(() -> removeCircles());
            ThreadHelper.sleep(30);
        }
    }

    private void initializeGame() {
        gameEnded = false;
        boardModel.initialize();

        updatePassDisability();
        if (gameMode == PLAYER_ENGINE) {
            triggerGameWithEngineIfNeed();
        }

        takeOffPiecesFromBoard();
    }

    private synchronized void removeCircles() {
        piecesGroup.getChildren().clear();
    }

    private void putSquarePiece(Move move) {
        Rectangle rectangle = getRectangle(move.getRow(), move.getFile());

        putSquarePiece(rectangle, move.getPiece().getColor());
    }

    private Rectangle getRectangle(int row, int file) {
        ObservableList<Node> rectangles = this.board.getChildren();
        return (Rectangle) rectangles.get((pl.edu.prz.klopusz.application.model.game.Board.MAX_INDEX-row) * pl.edu
                .prz.klopusz.application.model.game.Board.MAX_INDEX+file-1);
    }

    private void putSquarePiece(Rectangle rectangle, Piece.Color piece) {
        Node circle = BoardFactory.makePiece(rectangle, piece);

        if (Platform.isFxApplicationThread()) {
            piecesGroup.getChildren().add(circle);
        } else {
            Platform.runLater(() -> piecesGroup.getChildren().add(circle));
            try {
                Thread.sleep(15);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void execute(ConfigurableCommand command) {
        command.configure(configurationModel);
        command.execute();
    }

    public void handleNewGame() {
        boardModel.setTurn(Piece.Color.WHITE);
        initializeGame();
    }

    @Override
    public void observe(Event event) {
        if (event instanceof BoardEvent) {
            BoardEvent boardEvent = (BoardEvent) event;
            pl.edu.prz.klopusz.application.model.game.Board board = boardEvent.board;

            updateBoard(board);
        } else if (event instanceof MoveEvent) {
            MoveEvent moveEvent = (MoveEvent) event;

            if (!moveEvent.getMove().isPassed()) {
                putSquarePiece(moveEvent.getMove());
            }

            if (gameMode != ENGINES) {
                initializePayoutCalculator();
                payoutCalculator.calculate();
                boardModel.changeGoals(payoutCalculator.getWhite(), payoutCalculator.getBlack());
            }

            updatePassDisability();
        } else if (event instanceof EndEvent) {
            new ShowRightStatusCommand(GAME_FINISHED).execute();
            gameEnded = true;
            if (gameMode != ENGINES) {
                initializePayoutCalculator();
                payoutCalculator.calculate();
                boardModel.changeGoals(payoutCalculator.getWhite(), payoutCalculator.getBlack());
            }
        }
    }

    public void stopEngineGame() {
        new StopServerCommand().execute();
    }

    public GameMode getGameMode() {
        return gameMode;
    }

    public void turn(Piece.Color turn) {
        boardModel.setTurn(turn);
        if(gameMode == PLAYER_ENGINE)
            triggerGameWithEngineIfNeed();
    }

    public void setComboBoxesToRandoms() {
        whiteEngineChoiceBox.setValue("Random Gamer");
        blackEngineChoiceBox.setValue("Random Gamer");
    }

    enum GameMode {PLAYERS, ENGINES, PLAYER_ENGINE;}
}
