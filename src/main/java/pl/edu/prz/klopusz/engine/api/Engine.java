package pl.edu.prz.klopusz.engine.api;

import pl.edu.prz.klopusz.application.model.game.Move;
import pl.edu.prz.klopusz.application.model.game.Piece;

import java.util.Collection;

/**
 * Created by kamil on 06.08.17.
 */
public interface Engine {
    Move move(Piece.Color color, Collection<Move> legalMoves);
}
