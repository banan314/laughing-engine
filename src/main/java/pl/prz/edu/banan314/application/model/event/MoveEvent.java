package pl.prz.edu.banan314.application.model.event;

import org.ggp.base.util.observer.Event;
import pl.prz.edu.banan314.application.model.game.Move;

/**
 * Created by kamil on 23.06.17.
 */
public class MoveEvent extends Event {
    private final Move move;

    public MoveEvent(Move move) {
        this.move = move;
    }

    public Move getMove() {
        return move;
    }
}
