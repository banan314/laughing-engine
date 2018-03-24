package pl.edu.prz.klopusz.engine.api;

import pl.edu.prz.klopusz.application.model.game.Move;

public interface GoalCalculator {
    float calculate(Move move);
}
