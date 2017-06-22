package pl.prz.edu.banan314.utilities;

import org.ggp.base.server.event.ServerCompletedMatchEvent;
import org.ggp.base.server.event.ServerNewGameStateEvent;
import org.ggp.base.server.event.ServerNewMovesEvent;
import org.ggp.base.util.gdl.grammar.GdlSentence;
import org.ggp.base.util.match.Match;
import org.ggp.base.util.observer.Event;
import org.ggp.base.util.observer.Observer;
import org.ggp.base.util.statemachine.MachineState;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by kamil on 22.06.17.
 */
public abstract class ServerObserver implements Observer {

    final Set<GdlSentence> oldContents = new HashSet<GdlSentence>();

    Match theMatch;

    public ServerObserver(Match theMatch) {
        this.theMatch = theMatch;
    }

    @Override
    public void observe(Event event) {
        if (event instanceof ServerNewGameStateEvent) {
            handleNewGame((ServerNewGameStateEvent) event);
        } else if (event instanceof ServerNewMovesEvent) {
            handleNewMoves((ServerNewMovesEvent) event);
        } else if (event instanceof ServerCompletedMatchEvent) {
            handleCompletedMatch((ServerCompletedMatchEvent) event);
        }
    }

    abstract protected void handleNewGame(ServerNewGameStateEvent event);

    abstract protected void handleNewMoves(ServerNewMovesEvent event);

    abstract protected void handleCompletedMatch(ServerCompletedMatchEvent event);
}
