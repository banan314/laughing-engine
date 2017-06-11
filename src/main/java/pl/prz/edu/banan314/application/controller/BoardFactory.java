package pl.prz.edu.banan314.application.controller;

import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import pl.prz.edu.banan314.application.common.Point;
import pl.prz.edu.banan314.game.Piece;

public class BoardFactory {
    final static String WHITE_STYLE = "-fx-fill: white";
    final static String BLACK_STYLE = "-fx-fill: black";

    static Circle makePiece(Rectangle square, Piece.Color color) {
        Point squareCenter = new Point(square.getLayoutX()+square.getWidth() / 2, square.getLayoutY()+square.getHeight() / 2);

        Circle piece = new Circle();
        initializePiece(squareCenter, piece, color);
        return piece;
    }

    static void initializePiece(Point squareCenter, Circle piece, Piece.Color color) {
        piece.setCenterX(squareCenter.x);
        piece.setCenterY(squareCenter.y);
        piece.setRadius(13);
        if(color == Piece.Color.WHITE)
            piece.setStyle(WHITE_STYLE);
        else
            piece.setStyle(BLACK_STYLE);
    }
}