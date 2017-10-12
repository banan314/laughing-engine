package pl.edu.prz.klopusz.application.model;

import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import org.ggp.base.util.observer.Event;
import org.ggp.base.util.observer.Observer;
import org.ggp.base.util.observer.Subject;
import pl.edu.prz.klopusz.application.commands.impl.ShowLeftStatusCommand;
import pl.edu.prz.klopusz.application.commands.impl.ShowRightStatusCommand;
import pl.edu.prz.klopusz.application.common.Messages;
import pl.edu.prz.klopusz.application.model.event.BoardEvent;
import pl.edu.prz.klopusz.application.model.event.EndEvent;
import pl.edu.prz.klopusz.application.model.event.MoveEvent;
import pl.edu.prz.klopusz.application.model.game.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import java.util.logging.Logger;

/**
 * Created by kamil on 22.06.17.
 */
public class BoardModel implements Subject {
    Board board = new DolarBoard();
    private List<Observer> observers = new ArrayList<Observer>();

    private Logger LOG = Logger.getLogger("Board model");

    Piece.Color onMove = Piece.Color.WHITE;

    boolean whitePassed = false, blackPassed = false;

    StringProperty whiteGoal = new SimpleStringProperty("-1"), blackGoal = new SimpleStringProperty("-1");

    public Board getBoard() {
        return board;
    }

    public void initialize() {
        assert board != null;
        board.initialize();
        onMove = Piece.Color.WHITE;
        LOG.info("board model: initialize");

        BoardEvent boardEvent = new BoardEvent(board);
        notifyObservers(boardEvent);
    }

    public void setTurn(Piece.Color onMove) {
        this.onMove = onMove;
    }

    public Piece.Color whoseTurn() {
        assert null != onMove;
        return onMove;
    }

    public void passAsWhite() {
        setWhitePassed(true);
        if(blackPassed)
            onGameEnded();
        swapTurn();
    }

    public void passAsBlack() {
        setBlackPassed(true);
        if(whitePassed)
            onGameEnded();
        swapTurn();
    }

    private void onGameEnded() {
        new ShowRightStatusCommand(Messages.GAME_FINISHED);
        notifyObservers(new EndEvent());
    }

    private void setWhitePassed(boolean whitePassed) {
        this.whitePassed = whitePassed;
    }

    private void setBlackPassed(boolean blackPassed) {
        this.blackPassed = blackPassed;
    }

    @Override
    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers(Event event) {
        for(Observer observer : observers) {
            observer.observe(event);
        }
    }

    public void makeMove(Move move) {
        board.set(move.getRow(), move.getFile(), new Square(move.getPiece()));

        LOG.info("made move: " + move);

        if(move.getPiece().isWhite()) {
            whitePassed = false;
        } else if(move.getPiece().isBlack()) {
            blackPassed = false;
        }

        swapTurn();

        MoveEvent moveEvent = new MoveEvent(move);
        notifyObservers(moveEvent);
    }

    public void changeGoals(int whiteGoal, int blackGoal) {
        Platform.runLater(() -> {
            this.whiteGoal.set(String.valueOf(whiteGoal));
            this.blackGoal.set(String.valueOf(blackGoal));
        });
        try {
            Thread.sleep(20);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public StringProperty getWhiteGoalProperty() {
        return whiteGoal;
    }

    public StringProperty getBlackGoalProperty() {
        return blackGoal;
    }

    public boolean isLegal(Move move) {
        return board.isLegal(move);
    }

    public Collection<Move> legalMoves() {
        //TODO: optimize
        List<Move> legalMoves = new ArrayList<>();
        for(byte file = Board.MIN_INDEX; file <= Board.MAX_INDEX; file++) {
            for(int row = Board.MIN_INDEX; row <= Board.MAX_INDEX; row++) {
                Move move = new Move();
                move.setRow(row);
                move.setFile(file);
                move.setPiece(Piece.from(onMove));
                if(isLegal(move)) {
                    legalMoves.add(move);
                }
            }
        }
        return legalMoves;
    }

    public void swapTurn() {
        onMove = Piece.Color.opposite(onMove);
    }
}
