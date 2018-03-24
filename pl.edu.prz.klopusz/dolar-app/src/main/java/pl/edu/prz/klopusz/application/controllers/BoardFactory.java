package pl.edu.prz.klopusz.application.controllers;

import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.Material;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.shape.Sphere;
import pl.edu.prz.klopusz.application.common.Assets;
import pl.edu.prz.klopusz.application.common.Point;
import pl.edu.prz.klopusz.application.model.game.Piece;

public class BoardFactory {
    private final static String WHITE_STYLE = "-fx-fill: white";
    private final static String BLACK_STYLE = "-fx-fill: black";

    final public static Material whitePieceMaterial =  new PhongMaterial(Assets.pieceColor(Piece.Color.WHITE));
    final public static Material blackPieceMaterial =  new PhongMaterial(Assets.pieceColor(Piece.Color.BLACK));

    static Node makePiece(Rectangle square, Piece.Color color) {
        Point squareCenter = new Point(square.getLayoutX()+square.getWidth() / 2, square.getLayoutY()+square
                .getHeight() / 2);

        Sphere piece = new Sphere();
        //initializeCirclePiece(squareCenter, piece, color);

        initializeSpherePiece(squareCenter, piece, color);

        return piece;
    }

    static void initializeSpherePiece(Point<Double> squareCenter, Sphere piece, Piece.Color color) {
        piece.setLayoutX(squareCenter.x);
        piece.setLayoutY(squareCenter.y);
        piece.setRadius(15);
        if (color == Piece.Color.WHITE) {
            piece.setMaterial(whitePieceMaterial);
        } else {
            piece.setMaterial(blackPieceMaterial);
        }
    }

    static void initializeCirclePiece(Point<Double> squareCenter, Circle piece, Piece.Color color) {
        piece.setCenterX(squareCenter.x);
        piece.setCenterY(squareCenter.y);
        piece.setRadius(13);
        if (color == Piece.Color.WHITE) {
            piece.setStyle(WHITE_STYLE);
        } else {
            piece.setStyle(BLACK_STYLE);
        }
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
        });
    }
}