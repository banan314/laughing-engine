package pl.edu.prz.klopusz.engine.impl;

import lombok.*;
import pl.edu.prz.klopusz.application.model.game.Move;
import pl.edu.prz.klopusz.application.model.game.Piece;
import pl.edu.prz.klopusz.engine.api.Territory;
import pl.edu.prz.klopusz.utilities.decorators.TerritoryAnalyzer;
import pl.edu.prz.klopusz.utilities.decorators.TerritoryAnalyzerImpl;

import java.util.*;

import static java.lang.Math.abs;

/**
 * Created by kamil on 06.08.17.
 * @apiNote the board should be initialized via a setter
 */
public class IntelligentEngine extends EngineImpl {

    Territory whiteTerritory;
    Territory blackTerritory;

    @Override
    @NonNull
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
        if(cb.isWithin(move.square()))
            return 0;
        return abs(move.getFile()-cb.lower) + abs(move.getRow()-cb.lower);
    }

    @Override
    protected Move bestMove(List<Move> movesConsidered) {
        Map<Move, Float> moveGoal = new HashMap<>();
        val strategy = new SmartStrategy(board);
        val goalCalculator = new GoalCalculatorImpl(strategy);
        for(val move : movesConsidered) {
            float goal = goalCalculator.calculate(move);
            moveGoal.put(move, goal);
        }

        return findBest(moveGoal);
    }

    Move findBest (Map<Move, Float> moveGoal) {
        assert moveGoal.size() > 0;
        val entrySet = moveGoal.entrySet().stream().min((low, high) -> {
            if(low.getValue() == high.getValue()) {
                return 0;
            }
            if (low.getValue() < high.getValue()) {
                return -1;
            }
            return 1;
        });
        return entrySet.get().getKey();
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

}
