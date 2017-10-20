package pl.edu.prz.klopusz.application.model.game;

import org.junit.Test;

import static org.junit.Assert.*;

public class BoardTest {
    @Test
    public void get() throws Exception {
        Board board = new DolarBoard();
        board.set(2, 6, new Square());

        Square square = board.get(2, 6);
        assertEquals(2, square.getRow());
        assertEquals(6, square.getFile());
    }
}