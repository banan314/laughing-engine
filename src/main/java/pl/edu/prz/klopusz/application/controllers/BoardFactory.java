package pl.edu.prz.klopusz.application.controllers;

import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import pl.edu.prz.klopusz.application.common.Point;
import pl.edu.prz.klopusz.application.model.game.Piece;

public class BoardFactory {
    final static String WHITE_STYLE = "-fx-fill: white";
    final static String BLACK_STYLE = "-fx-fill: black";

    static Circle makePiece(Rectangle square, Piece.Color color) {
        Point squareCenter = new Point(square.getLayoutX()+square.getWidth() / 2, square.getLayoutY()
                +square
                .getHeight() / 2);

        Circle piece = new Circle();
        initializePiece(squareCenter, piece, color);
        return piece;
    }

    static void initializePiece(Point<Double> squareCenter, Circle piece, Piece.Color color) {
        piece.setCenterX(squareCenter.x);
        piece.setCenterY(squareCenter.y);
        piece.setRadius(13);
        if(color == Piece.Color.WHITE)
            piece.setStyle(WHITE_STYLE);
        else
            piece.setStyle(BLACK_STYLE);
    }

    public static Rectangle makeRectangle() {
        Rectangle rectangle = new Rectangle();

        rectangle.setWidth(30);
        rectangle.setHeight(30);
        rectangle.setLayoutX(30);
        rectangle.setLayoutY(30);

        addOnMouseClickEventTo(rectangle);

        return rectangle;
    }

    static void addOnMouseClickEventTo(Rectangle rectangle) {
        rectangle.setOnMouseClicked(event -> {
                    try {
                        Board.class.getMethod("onSquareClick", MouseEvent.class).invoke(new Board(), event);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
        );
    }
}