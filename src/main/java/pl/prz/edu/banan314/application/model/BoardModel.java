package pl.prz.edu.banan314.application.model;

import javafx.application.Platform;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;
import org.ggp.base.util.observer.Event;
import org.ggp.base.util.observer.Observer;
import org.ggp.base.util.observer.Subject;
import pl.prz.edu.banan314.application.model.event.BoardEvent;
import pl.prz.edu.banan314.application.model.event.MoveEvent;
import pl.prz.edu.banan314.application.model.game.Board;
import pl.prz.edu.banan314.application.model.game.DolarBoard;
import pl.prz.edu.banan314.application.model.game.Move;
import pl.prz.edu.banan314.application.model.game.Square;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by kamil on 22.06.17.
 */
public class BoardModel implements Subject {
    Board board = new DolarBoard();
    private List<Observer> observers = new ArrayList<Observer>();

    public Board getBoard() {
        return board;
    }

    StringProperty whiteGoal = new SimpleStringProperty("-1"), blackGoal = new SimpleStringProperty("-1");

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
