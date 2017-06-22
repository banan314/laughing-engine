package pl.prz.edu.banan314.application.controllers;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import org.ggp.base.util.observer.Event;
import org.ggp.base.util.observer.Observer;
import pl.prz.edu.banan314.application.model.BoardModel;
import pl.prz.edu.banan314.application.model.event.BoardEvent;
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

    private volatile boolean circleRemoved = true;

    @FXML
    public void onSquareClick(MouseEvent event) {
        Rectangle square = (Rectangle) event.getSource();

        putSquarePiece(square, onMove);

        turnColor();
    }

    private void turnColor() {
        onMove = onMove == Piece.Color.WHITE ? Piece.Color.BLACK : Piece.Color.WHITE;
    }

    public void updateBoard(pl.prz.edu.banan314.application.model.game.Board board) {
        ObservableList<Node> rectangles = this.board.getChildren();

        takeOffPiecesFromBoard();
        for(int i = MIN_INDEX; i <= MAX_INDEX; i++) {
            for(int j = MIN_INDEX; j < MAX_INDEX; j++) {
                //if empty do nothing
                Square square = board.get(i, j);
                if (square.isEmpty()) {
                    continue;
                }
                putSquarePiece((Rectangle) rectangles.get(i * MAX_INDEX+j), square.getPiece().getColor());
            }
        }
    }

    @FXML
    private void takeOffPiecesFromBoard() {
        System.out.println("root children");
        Platform.runLater(() -> {
            removeCircles();
        });
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private synchronized void removeCircles() {
        board.getChildren().removeIf(node -> node instanceof Circle);
    }

    private void putSquarePiece(Rectangle rectangle, Piece.Color piece) {
        Circle circle = BoardFactory.makePiece(rectangle, onMove);

        board.getChildren().add(circle);
    }

    @Override
    public void observe(Event event) {
        if (event instanceof BoardEvent) {
            BoardEvent boardEvent = (BoardEvent) event;
            pl.prz.edu.banan314.application.model.game.Board board = boardEvent.board;

            updateBoard(board);
        }
    }
}
