package pl.edu.prz.klopusz.engine.impl;

import pl.edu.prz.klopusz.application.model.game.Move;
import pl.edu.prz.klopusz.engine.api.GoalCalculator;
import pl.edu.prz.klopusz.engine.api.GoalStrategy;

public class GoalCalculatorImpl implements GoalCalculator {
    private final GoalStrategy strategy;

    GoalCalculatorImpl(GoalStrategy strategy) {
        this.strategy = strategy;
    }

    @Override
    public float calculate(Move move) {
        return strategy.goal(move);
    }
}
