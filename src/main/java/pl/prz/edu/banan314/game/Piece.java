package pl.prz.edu.banan314.game;

/**
 * Created by kamil on 04.06.17.
 */
public class Piece {
    enum Color {WHITE, BLACK};

    Color color;

    public Piece(Color color) {
        this.color = color;
    }
}
