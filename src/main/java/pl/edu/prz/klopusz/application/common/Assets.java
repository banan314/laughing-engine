package pl.edu.prz.klopusz.application.common;

import javafx.scene.paint.Color;
import pl.edu.prz.klopusz.application.model.game.Piece;

public class Assets {
    public static String playerName(Piece.Color color) {
        switch (color) {
            case WHITE:
                return "White";
            case BLACK:
                return "Black";
            default:
                return "Unknown player";
        }
    }

    public static Color pieceColor(Piece.Color color) {
        switch (color) {
            case WHITE:
                return Color.color(0.0, 0.5019, 0.1, 0.6);
            case BLACK:
                return Color.color(1.0, 0.60, 0.01);
            default:
                return Color.CYAN;
        }
    }
}
