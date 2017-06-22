package pl.prz.edu.banan314.application.controller;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import pl.prz.edu.banan314.application.model.game.Piece;
import pl.prz.edu.banan314.application.model.game.Square;

import static pl.prz.edu.banan314.application.model.game.Board.MIN_INDEX;
import static pl.prz.edu.banan314.application.model.game.Board.MAX_INDEX;


/**
 * Created by kamil on 10.06.17.
 */
public class Board {
    Piece.Color onMove = Piece.Color.WHITE;

    @FXML private Group board;

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

        for(int i = MIN_INDEX; i <= MAX_INDEX; i++) {
            for(int j = MIN_INDEX; j < MAX_INDEX; j++) {
                //if empty do nothing
                Square square = board.get(i, j);
                if (square.isEmpty()) {
                    continue;
                }
                putSquarePiece((Rectangle) rectangles.get(i*MAX_INDEX + j), square.getPiece().getColor());
            }
        }
    }

    private void putSquarePiece(Rectangle rectangle, Piece.Color piece) {
        Circle circle = BoardFactory.makePiece(rectangle, onMove);

        board.getChildren().add(circle);
    }
}
