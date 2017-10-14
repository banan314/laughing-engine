package pl.edu.prz.klopusz.utilities.decorators;

import pl.edu.prz.klopusz.application.model.game.Board;
import pl.edu.prz.klopusz.engine.api.Territory;

/**
 * Created by kamil on 19.06.17.
 */

public class TerritoryCalculatorImpl implements ScoreCalculator {

    Territory whiteTerritory;
    Territory blackTerritory;

    TerritoryAnalyzer territoryAnalyzer;

    private final Board board;

    public TerritoryCalculatorImpl(Board board) {
        this.board = board;
        territoryAnalyzer = new TerritoryAnalyzerImpl(board);
    }

    @Override
    public void calculate() {
        territoryAnalyzer.analyze();

        whiteTerritory = territoryAnalyzer.getWhiteTerritory();
        blackTerritory = territoryAnalyzer.getBlackTerritory();
    }

    @Override
    public int getWhite() {
        return whiteTerritory.size();
    }

    @Override
    public int getBlack() {
        return blackTerritory.size();
    }
}
