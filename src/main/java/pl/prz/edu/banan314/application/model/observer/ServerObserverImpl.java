package pl.prz.edu.banan314.application.model.observer;

import org.ggp.base.server.event.ServerCompletedMatchEvent;
import org.ggp.base.server.event.ServerNewGameStateEvent;
import org.ggp.base.server.event.ServerNewMovesEvent;
import org.ggp.base.util.match.Match;
import pl.prz.edu.banan314.application.model.BoardModel;
import pl.prz.edu.banan314.utilities.ServerObserver;

import java.util.Observable;
import java.util.Observer;

/**
 * Created by kamil on 22.06.17.
 */
public class ServerObserverImpl extends ServerObserver {
    private BoardModel boardModel;

    public ServerObserverImpl(Match theMatch) {
        super(theMatch);
    }

    public void setBoardModel(BoardModel boardModel) {
        this.boardModel = boardModel;
    }

    @Override
    protected void handleNewState(ServerNewGameStateEvent event) {

    }

    @Override
    protected void handleNewMoves(ServerNewMovesEvent event) {

    }

    @Override
    protected void handleCompletedMatch(ServerCompletedMatchEvent event) {

    }
}
