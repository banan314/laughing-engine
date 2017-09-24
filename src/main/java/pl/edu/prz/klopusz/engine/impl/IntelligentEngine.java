package pl.edu.prz.klopusz.engine.impl;

import lombok.*;
import pl.edu.prz.klopusz.application.model.game.Move;
import pl.edu.prz.klopusz.application.model.game.Piece;
import pl.edu.prz.klopusz.engine.api.Territory;
import pl.edu.prz.klopusz.utilities.decorators.TerritoryAnalyzer;
import pl.edu.prz.klopusz.utilities.decorators.TerritoryAnalyzerImpl;

import java.util.LinkedList;
import java.util.List;
import java.util.function.IntFunction;

import static java.lang.Math.abs;

/**
 * Created by kamil on 06.08.17.
 */
public class IntelligentEngine extends EngineImpl {

    Territory whiteTerritory;
    Territory blackTerritory;

    @Override
    protected Move leaveOutMoveNearestToTheCamp(List<Move> movesConsidered) {
        int minDistance = 9;
        Move nearestMove = null;
        for(Move move : movesConsidered) {
            int distance = distanceToTheCamp(move);
            if(distance < minDistance) {
                nearestMove = move;
                minDistance = distance;
            }
        }
        return nearestMove;
    }

    int distanceToTheCamp(Move move) {
        CampBorder cb = new CampBorder(move.getPiece().getColor());
        return abs(move.getFile()-cb.lower) + abs(move.getRow()-cb.lower);
    }

    /*TODO: implement the best move method below*/

    @Override
    protected Move bestMove(List<Move> movesConsidered) {
        return null;
    }

    @Override
    protected List<Move> removeMovesOnTerritories(List<Move> movesConsidered, Piece.Color color) {
        computeTerritories();
        List<Move> removed = new LinkedList<>(movesConsidered);
        switch (color) {
            case WHITE:
                removed.removeIf(move -> whiteTerritory.contains(move.square()));
                break;
            case BLACK:
                removed.removeIf(move -> blackTerritory.contains(move.square()));
                break;
        }

        return removed;
    }

    void computeTerritories() {
        TerritoryAnalyzer analyzer = new TerritoryAnalyzerImpl(board);
        analyzer.analyze();
        whiteTerritory = analyzer.getWhiteTerritory();
        blackTerritory = analyzer.getBlackTerritory();
    }

    @Override
    protected boolean isCampOccupied(Piece.Color color) {
        CampBorder cb = new CampBorder(color);
        val next = cb.next();
        for(int i = cb.lower; i != cb.upper; i = next.apply(i)) {
            for(int j = cb.lower; j != cb.upper; j = next.apply(j)) {
                //we do not want to check the initial stones
                if(i == cb.upper && j == cb.upper)
                    continue;

                if(board.get(i, j).isEmpty()) {
                    return false;
                }
            }
        }
        return true;
    }

    class CampBorder {
        final public int lower, upper;
        final Piece.Color color;

        CampBorder(Piece.Color color) {
            this.color = color;

            switch (color) {
                case WHITE:
                    lower = 5;
                    upper = 9;
                    break;
                case BLACK:
                    lower = 4;
                    upper = 1;
                    break;
                default:
                    lower = -1;
                    upper = -1;
            }
        }

        IntFunction<Integer> next() {
            switch (color) {
                case WHITE:
                    return (int x)->x+1;
                case BLACK:
                    return (int x)->x-1;
                default:
                    return (int x)->x;
            }
        }
    }
}
