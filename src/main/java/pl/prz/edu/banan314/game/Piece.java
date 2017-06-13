package pl.prz.edu.banan314.game;

/**
 * Created by kamil on 04.06.17.
 */
public class Piece {
    public static final Piece WHITE = new Piece(Color.WHITE);
    public static final Piece BLACK = new Piece(Color.BLACK);

    public enum Color {WHITE, BLACK};

    Color color;

    public Piece(Color color) {
        this.color = color;
    }

    @Override public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Piece piece = (Piece) o;

        return color == piece.color;
    }

    @Override public int hashCode() {
        return color.hashCode();
    }
}
