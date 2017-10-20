package pl.edu.prz.klopusz.application.model.game;

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
        if (s.toLowerCase().equals("white")) {
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

    public static Piece from(Color color) {
        switch (color) {
            case WHITE:
                return WHITE;
            case BLACK:
                return BLACK;
            default:
                throw new RuntimeException("no such color");
        }
    }

    public enum Color {WHITE, BLACK;

        public static Color opposite(Color player) {
            return player == Piece.Color.WHITE ? Piece.Color.BLACK : Piece.Color.WHITE;
        }
    }

    @Override
    public String toString() {
        return "Piece{"+"color="+color+'}';
    }
}
