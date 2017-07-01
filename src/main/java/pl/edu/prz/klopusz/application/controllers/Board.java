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
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import lombok.val;
import org.ggp.base.util.observer.Event;
import org.ggp.base.util.observer.Observer;
import pl.edu.prz.klopusz.application.DolarMainApp;
import pl.edu.prz.klopusz.application.commands.ConfigurableCommand;
import pl.edu.prz.klopusz.application.common.Messages;
import pl.edu.prz.klopusz.application.common.Point;
import pl.edu.prz.klopusz.application.common.ThreadHelper;
import pl.edu.prz.klopusz.application.model.BoardModel;
import pl.edu.prz.klopusz.application.model.event.BoardEvent;
import pl.edu.prz.klopusz.application.model.event.MoveEvent;
import pl.edu.prz.klopusz.application.model.game.Move;
import pl.edu.prz.klopusz.application.model.game.Square;
import pl.edu.prz.klopusz.application.model.ConfigurationModel;
import pl.edu.prz.klopusz.application.model.game.Piece;


import java.net.URL;
import java.util.ResourceBundle;

import static pl.edu.prz.klopusz.application.common.Messages.*;

/**
 * Created by kamil on 10.06.17.
 */
public class Board implements Observer, Initializable {

    GameMode gameMode = GameMode.PLAYERS;

    @FXML private Group board;
    @FXML private AnchorPane root;
    @FXML private Group piecesGroup;
    @FXML private ChoiceBox<String> whiteEngineChoiceBox;
    @FXML private ChoiceBox<String> blackEngineChoiceBox;
    @FXML private Label whiteGoal, blackGoal;
    @FXML private Button whitePass;
    @FXML private Button blackPass;
    private BoardModel boardModel;
    private ConfigurationModel configurationModel;

    DolarMainApp parentApp;

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
    }

    public void setGameMode(GameMode gameMode) {
        this.gameMode = gameMode;
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
        Rectangle square = (Rectangle) event.getSource();

        try {
            val point = rectangle2Point(square);
            Move move = new Move();
            move.setRow(point.y);
            move.setFile(point.x);
            move.setPiece(new Piece(boardModel.whoseTurn()));

            if (boardModel.isLegal(move)) {
                boardModel.makeMove(move);
                putSquarePiece(square, boardModel.whoseTurn());
                turnColor();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void handlePass(MouseEvent event) {
        Node passButton = (Node) event.getSource();
        if (passButton.getId().equals("whitePass")) {
            whitePass.setDisable(true);
            blackPass.setDisable(false);
            boardModel.setWhitePassed(true);
            turnColor();
            parentApp.showLeftStatus(WHITE_PASSED);
        } else if (passButton.getId().equals("blackPass")) {
            whitePass.setDisable(false);
            blackPass.setDisable(true);
            boardModel.setBlackPassed(true);
            turnColor();
            parentApp.showLeftStatus(BLACK_PASSED);
        }
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

    private void turnColor() {
        Piece.Color onMove = boardModel.whoseTurn();
        boardModel.setTurn(onMove == Piece.Color.WHITE ? Piece.Color.BLACK : Piece.Color.WHITE);
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
    private void takeOffPiecesFromBoard() {
        System.out.println("take off pieces");
        if (Platform.isFxApplicationThread()) {
            removeCircles();
        } else {
            Platform.runLater(() -> removeCircles());
            ThreadHelper.sleep(30);
        }
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
        Circle circle = BoardFactory.makePiece(rectangle, piece);

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

    @Override
    public void observe(Event event) {
        if (event instanceof BoardEvent) {
            BoardEvent boardEvent = (BoardEvent) event;
            pl.edu.prz.klopusz.application.model.game.Board board = boardEvent.board;

            updateBoard(board);
        } else if (event instanceof MoveEvent) {
            MoveEvent moveEvent = (MoveEvent) event;

            putSquarePiece(moveEvent.getMove());
        }
    }

    enum GameMode {PLAYERS, ENGINES;}
}
