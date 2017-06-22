package pl.prz.edu.banan314.application.controllers;

import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Rectangle;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by kamil on 14.06.17.
 */
class BoardFactoryTest {
    @Test void makeRectangle() {
        assertNotNull(BoardFactory.makeRectangle());
    }

    @Test void addOnMouseClickEventTo() {
        Rectangle rectangle = new Rectangle();
        BoardFactory.addOnMouseClickEventTo(rectangle);
    }

    @Test void testMethodExists() {
        try {
            Board.class.getMethod("onSquareClick", MouseEvent.class);
        } catch (NoSuchMethodException e) {
            fail("no such method");
        }
    }
}