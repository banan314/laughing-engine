package pl.edu.prz.klopusz.utilities.decorators;

import org.junit.Before;
import org.junit.Test;
import pl.edu.prz.klopusz.application.model.game.Board;
import pl.edu.prz.klopusz.application.model.game.DolarBoard;

import static org.junit.Assert.*;

public class TerritoryAnalyzerImplTest {
    Board board;
    private TerritoryAnalyzerImpl territoryAnalyzer;

    @Before
    public void setUp() throws Exception {
        board = new DolarBoard();
        board.initialize();
        territoryAnalyzer = new TerritoryAnalyzerImpl(board);
        territoryAnalyzer.analyze();
    }

    @Test
    public void getWhiteTerritory_InitialBoard_SizeEquals0() throws Exception {
        assertEquals(0, territoryAnalyzer.getWhiteTerritory().size());
    }

    @Test
    public void getBlackTerritory_InitialBoard_SizeEquals0() throws Exception {
        assertEquals(0, territoryAnalyzer.getBlackTerritory().size());
    }

}