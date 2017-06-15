package pl.prz.edu.banan314.application.controller;

import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import pl.prz.edu.banan314.game.Piece;


/**
 * Created by kamil on 10.06.17.
 */
public class Board {
    Piece.Color onMove = Piece.Color.WHITE;

    public void onSquareClick(MouseEvent event) {
        Rectangle square = (Rectangle) event.getSource();

        AnchorPane root = (AnchorPane) square.getParent();

        Circle piece = BoardFactory.makePiece(square, onMove);

        root.getChildren().add(piece);

        turnColor();
    }

    private void turnColor() {
        onMove = onMove == Piece.Color.WHITE ? Piece.Color.BLACK: Piece.Color.WHITE;
    }
}
