package pl.prz.edu.banan314.application.model.observer;

import org.ggp.base.server.event.ServerCompletedMatchEvent;
import org.ggp.base.server.event.ServerNewGameStateEvent;
import org.ggp.base.server.event.ServerNewMovesEvent;
import org.ggp.base.util.gdl.grammar.GdlSentence;
import org.ggp.base.util.match.Match;
import pl.prz.edu.banan314.application.commands.impl.server.NewServerCommand;
import pl.prz.edu.banan314.application.commands.impl.server.StopServerCommand;
import pl.prz.edu.banan314.application.model.BoardModel;
import pl.prz.edu.banan314.application.model.game.Move;
import pl.prz.edu.banan314.utilities.ServerObserver;

import java.util.List;

/**
 * Created by kamil on 22.06.17.
 */
public class ServerObserverImpl extends ServerObserver {
    public static final int BLACK_INDEX = 0;
    public static final int WHITE_INDEX = 1;
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
        List<org.ggp.base.util.statemachine.Move> moves = event.getMoves();
        GdlSentence blackSentence = moves.get(BLACK_INDEX).getContents().toSentence();
        GdlSentence whiteSentence = moves.get(WHITE_INDEX).getContents().toSentence();

        Move blackMove;
        Move whiteMove;
        System.out.println("black: " + blackSentence);
        System.out.println("white: " + whiteSentence);
        if(!isNoop(blackSentence)) {
            blackMove = Move.from(blackSentence);
            assert blackMove.getPiece().isBlack();
            boardModel.makeMove(blackMove);
        }
        if(!isNoop(whiteSentence)) {
            whiteMove = Move.from(whiteSentence);
            assert whiteMove.getPiece().isWhite();
            boardModel.makeMove(whiteMove);
        }
        try {
            Thread.sleep(20); //so that view can be updated
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private boolean isNoop(GdlSentence blackSentence) {
        return blackSentence.toString().contains("noop");
    }

    @Override
    protected void handleCompletedMatch(ServerCompletedMatchEvent event) {
        List<Integer> goals = event.getGoals();
        int blackGoal = goals.get(BLACK_INDEX), whiteGoal = goals.get(WHITE_INDEX);
        boardModel.changeGoals(whiteGoal, blackGoal);

        new StopServerCommand().execute();
        new NewServerCommand().execute();
    }
}
