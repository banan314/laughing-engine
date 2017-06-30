package pl.edu.prz.klopusz.application.model;

import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import org.ggp.base.util.observer.Event;
import org.ggp.base.util.observer.Observer;
import org.ggp.base.util.observer.Subject;
import pl.edu.prz.klopusz.application.model.event.BoardEvent;
import pl.edu.prz.klopusz.application.model.event.MoveEvent;
import pl.edu.prz.klopusz.application.model.game.Board;
import pl.edu.prz.klopusz.application.model.game.DolarBoard;
import pl.edu.prz.klopusz.application.model.game.Move;
import pl.edu.prz.klopusz.application.model.game.Square;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kamil on 22.06.17.
 */
public class BoardModel implements Subject {
    Board board = new DolarBoard();
    private List<Observer> observers = new ArrayList<Observer>();

    StringProperty whiteGoal = new SimpleStringProperty("-1"), blackGoal = new SimpleStringProperty("-1");

    public Board getBoard() {
        return board;
    }

    public void initialize() {
        assert board != null;
        board.initialize();
        System.out.println("board model: initialize");

        BoardEvent boardEvent = new BoardEvent(board);
        notifyObservers(boardEvent);
    }

    @Override
    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void notifyObservers(Event event) {
        for(Observer observer : observers) {
            observer.observe(event);
        }
    }

    public void makeMove(Move move) {
        board.set(move.getRow(), move.getFile(), new Square(move.getPiece()));

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
}
