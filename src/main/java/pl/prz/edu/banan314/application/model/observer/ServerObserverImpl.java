package pl.prz.edu.banan314.application.model.observer;

import org.ggp.base.server.event.ServerCompletedMatchEvent;
import org.ggp.base.server.event.ServerNewGameStateEvent;
import org.ggp.base.server.event.ServerNewMovesEvent;
import org.ggp.base.util.match.Match;
import pl.prz.edu.banan314.utilities.ServerObserver;

import java.util.Observable;
import java.util.Observer;

/**
 * Created by kamil on 22.06.17.
 */
public class ServerObserverImpl extends ServerObserver implements Observer {
    public ServerObserverImpl(Match theMatch) {
        super(theMatch);
    }

    @Override
    protected void handleNewGame(ServerNewGameStateEvent event) {

    }

    @Override
    protected void handleNewMoves(ServerNewMovesEvent event) {

    }

    @Override
    protected void handleCompletedMatch(ServerCompletedMatchEvent event) {

    }

    @Override
    public void update(Observable o, Object arg) {

    }
}
