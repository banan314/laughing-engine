package pl.edu.prz.klopusz.engine.impl;

import lombok.Getter;
import lombok.Setter;
import pl.edu.prz.klopusz.application.model.game.Board;
import pl.edu.prz.klopusz.application.model.game.Move;
import pl.edu.prz.klopusz.application.model.game.Piece;
import pl.edu.prz.klopusz.engine.api.Engine;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by kamil on 06.08.17.
 */
public abstract class EngineImpl implements Engine {
    @Getter @Setter Board board;

    @Override
    public Move move(Piece.Color color, final Collection<Move> legalMoves) {
        assert board != null;
        List<Move> movesConsidered = new LinkedList<>(legalMoves);
        if(isCampOccupied(color)) {
            movesConsidered = removeMovesOnTerritories(movesConsidered, color);
            return bestMove(movesConsidered);
        } else {
            return leaveOutMoveNearestToTheCamp(movesConsidered);
        }
    }

    /**
     * @param movesConsidered - all the legal moves
     * @return the move nearest to the rival's camp
     */
    protected abstract Move leaveOutMoveNearestToTheCamp(List<Move> movesConsidered);

    /**
     * @param movesConsidered - all the legal moves
     * @return the best move according to the strategy
     */
    protected abstract Move bestMove(List<Move> movesConsidered);

    protected abstract List<Move> removeMovesOnTerritories(List<Move> movesConsidered, Piece.Color color);

    protected abstract boolean isCampOccupied(Piece.Color color);
}
