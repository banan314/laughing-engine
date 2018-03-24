package pl.edu.prz.klopusz.engine.api;

import pl.edu.prz.klopusz.application.model.game.Move;
import pl.edu.prz.klopusz.application.model.game.Piece;

import java.util.Collection;

/**
 * Created by kamil on 06.08.17.
 */
public interface Engine {
    /**
     * @param color who is on turn (the player to make the move)
     * @param legalMoves moves legal for player indicated by the 1st parameter
     * @return the best move for the player
     */
    Move move(Piece.Color color, Collection<Move> legalMoves);
}
