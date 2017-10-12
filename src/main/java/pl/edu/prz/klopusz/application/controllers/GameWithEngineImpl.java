package pl.edu.prz.klopusz.application.controllers;

import org.ggp.base.util.observer.Event;
import org.ggp.base.util.observer.Observer;
import pl.edu.prz.klopusz.application.model.BoardModel;
import pl.edu.prz.klopusz.application.model.game.Move;
import pl.edu.prz.klopusz.application.model.game.Piece;
import pl.edu.prz.klopusz.engine.api.Engine;
import pl.edu.prz.klopusz.engine.impl.IntelligentEngine;

public class GameWithEngineImpl implements GameWithEngine, Observer {
    Piece.Color playerColor;
    private BoardModel boardModel;
    IntelligentEngine engine;

    public GameWithEngineImpl() {
        engine = new IntelligentEngine();
        engine.setBoard(boardModel.getBoard());
    }

    public GameWithEngineImpl(IntelligentEngine engine) {
        this.engine = engine;
    }

    @Override
    public Piece.Color getPlayerColor() {
        return playerColor;
    }

    @Override
    public void setPlayerColor(Piece.Color color) {
        playerColor = color;
    }

    @Override
    public void setBoardModel(BoardModel model) {
        this.boardModel = model;
        model.addObserver(this);
    }

    @Override
    public Move makeMove() {
        Move move = engine.move(playerColor, boardModel.legalMoves());
        boardModel.makeMove(move);
        return move;
    }

    @Override
    public void observe(Event event) {
        //TODO: handle events
    }
}
