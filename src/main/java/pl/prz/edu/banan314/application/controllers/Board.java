package pl.prz.edu.banan314.application.controllers;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import lombok.val;
import org.ggp.base.util.observer.Event;
import org.ggp.base.util.observer.Observer;
import pl.prz.edu.banan314.application.common.Point;
import pl.prz.edu.banan314.application.model.BoardModel;
import pl.prz.edu.banan314.application.model.event.BoardEvent;
import pl.prz.edu.banan314.application.model.event.MoveEvent;
import pl.prz.edu.banan314.application.model.game.Move;
import pl.prz.edu.banan314.application.model.game.Piece;
import pl.prz.edu.banan314.application.model.game.Square;


import static pl.prz.edu.banan314.application.model.game.Board.MIN_INDEX;
import static pl.prz.edu.banan314.application.model.game.Board.MAX_INDEX;


/**
 * Created by kamil on 10.06.17.
 */
public class Board implements Observer {
    Piece.Color onMove = Piece.Color.WHITE;

    @FXML private Group board;
    @FXML private AnchorPane root;
    @FXML private Group piecesGroup;
    @FXML private ChoiceBox whiteEngineChoiceBox;
    @FXML private ChoiceBox blackEngineChoiceBox;
    @FXML private Label whiteGoal, blackGoal;

    private BoardModel boardModel;

    public void setBoardModel(BoardModel boardModel) {
        whiteGoal.textProperty().unbind();
        blackGoal.textProperty().unbind();

        this.boardModel = boardModel;

        whiteGoal.textProperty().bind(boardModel.getWhiteGoalProperty());
        blackGoal.textProperty().bind(boardModel.getBlackGoalProperty());
    }

    @FXML
    public void onSquareClick(MouseEvent event) {
        Rectangle square = (Rectangle) event.getSource();

        try {
            val point = rectangle2Point(square);
            Move move = new Move();
            move.setRow(point.y);
            move.setFile(point.x);
            move.setPiece(new Piece(onMove));

            if(boardModel.isLegal(move)) {
                boardModel.makeMove(move);
                putSquarePiece(square, onMove);
                turnColor();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Point<Integer> rectangle2Point(Rectangle rectangle) throws Exception {
        int which = 0;
        for(Node square : board.getChildren()) {
            Rectangle squareRectangle = (Rectangle)square;
            if(rectangle == squareRectangle) {
                return new Point(which%MAX_INDEX+1, MAX_INDEX - which/MAX_INDEX);
            }
            which++;
        }
        throw new Exception("not in board children");
    }

    private void turnColor() {
        onMove = onMove == Piece.Color.WHITE ? Piece.Color.BLACK : Piece.Color.WHITE;
    }

    public void updateBoard(pl.prz.edu.banan314.application.model.game.Board board) {

        takeOffPiecesFromBoard();
        for(int i = MIN_INDEX; i <= MAX_INDEX; i++) {
            for(int j = MIN_INDEX; j < MAX_INDEX; j++) {
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
        }
        try {
            Thread.sleep(30);
        } catch (InterruptedException e) {
            e.printStackTrace();
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
        return (Rectangle) rectangles.get((MAX_INDEX-row) * MAX_INDEX+file-1);
    }

    private void putSquarePiece(Rectangle rectangle, Piece.Color piece) {
        Circle circle = BoardFactory.makePiece(rectangle, piece);

        if (Platform.isFxApplicationThread()) {
            piecesGroup.getChildren().add(circle);
        } else {
            Platform.runLater(() -> board.getChildren().add(circle));
            try {
                Thread.sleep(15);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void observe(Event event) {
        if (event instanceof BoardEvent) {
            BoardEvent boardEvent = (BoardEvent) event;
            pl.prz.edu.banan314.application.model.game.Board board = boardEvent.board;

            updateBoard(board);
        } else if (event instanceof MoveEvent) {
            MoveEvent moveEvent = (MoveEvent) event;

            putSquarePiece(moveEvent.getMove());
        }
    }
}
