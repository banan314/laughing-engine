package pl.edu.prz.klopusz.application.controllers;

import lombok.Getter;
import lombok.Setter;
import org.ggp.base.util.observer.Event;
import org.ggp.base.util.observer.Observer;
import pl.edu.prz.klopusz.application.model.BoardModel;
import pl.edu.prz.klopusz.application.model.event.BoardEvent;
import pl.edu.prz.klopusz.application.model.event.EndEvent;
import pl.edu.prz.klopusz.application.model.event.MoveEvent;
import pl.edu.prz.klopusz.application.model.game.Move;
import pl.edu.prz.klopusz.application.model.game.Piece;
import pl.edu.prz.klopusz.engine.api.Engine;
import pl.edu.prz.klopusz.engine.impl.IntelligentEngine;

public class GameWithEngineImpl implements GameWithEngine, Observer {
    @Getter @Setter Piece.Color playerColor;
    private BoardModel boardModel;
    IntelligentEngine engine;
    @Setter boolean active = true;

    public GameWithEngineImpl() {
        engine = new IntelligentEngine();
    }

    public GameWithEngineImpl(IntelligentEngine engine) {
        this.engine = engine;
    }

    @Override
    public void setBoardModel(BoardModel model) {
        this.boardModel = model;
        engine.setBoard(boardModel.getBoard());
        model.addObserver(this);
    }

    @Override
    public Move makeMove() {
        assert null != boardModel;
        assert null != playerColor;
        if(!active) {
            return null;
        }
        Move move = engine.move(Piece.Color.opposite(playerColor), boardModel.legalMoves());
        assert move.getColor() != playerColor;
        boardModel.makeMove(move);
        return move;
    }

    @Override
    public void swapPlayer() {
        playerColor = Piece.Color.opposite(playerColor);
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        boardModel.removeObserver(this);
    }

    @Override
    public void observe(Event event) {
        if(event instanceof EndEvent) {
            active = false;
        } else if(event instanceof MoveEvent) {
            MoveEvent moveEvent = (MoveEvent) event;

            if(moveEvent.getMove().getColor() == playerColor) {
                assert boardModel.whoseTurn() != playerColor;
                makeMove();
            }
        } else if(event instanceof BoardEvent) {
            active = true;
        }
    }
}
