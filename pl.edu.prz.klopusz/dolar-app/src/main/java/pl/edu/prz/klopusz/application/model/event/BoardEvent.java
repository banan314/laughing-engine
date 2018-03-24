package pl.edu.prz.klopusz.application.model.event;

import org.ggp.base.util.observer.Event;
import pl.edu.prz.klopusz.application.model.game.Board;

/**
 * Created by kamil on 22.06.17.
 */
public class BoardEvent extends Event {
    public Board board;

    public BoardEvent(Board board) {
        this.board = board;
    }
}
