package pl.prz.edu.banan314.application.model.game;

/**
 * Created by kamil on 04.06.17.
 */
public class Piece {
    public static final Piece WHITE = new Piece(Color.WHITE);
    public static final Piece BLACK = new Piece(Color.BLACK);
    Color color;

    public Piece(Color color) {
        this.color = color;
    }

    public static Piece from(String s) {
        if (s.equals("white")) {
            return WHITE;
        } else {
            return BLACK;
        }
    }

    public Color getColor() {
        return color;
    }

    public boolean isWhite() {
        return this.equals(WHITE);
    }

    public boolean isBlack() {
        return this.equals(BLACK);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Piece piece = (Piece) o;

        return color == piece.color;
    }

    @Override
    public int hashCode() {
        return color.hashCode();
    }

    public enum Color {WHITE, BLACK;}
}
