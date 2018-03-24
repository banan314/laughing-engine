package pl.edu.prz.klopusz.application.common.calculator;

import lombok.val;
import pl.edu.prz.klopusz.application.model.game.Board;
import pl.edu.prz.klopusz.application.model.game.Piece;
import pl.edu.prz.klopusz.engine.api.Territory;
import pl.edu.prz.klopusz.engine.impl.CampBorder;
import pl.edu.prz.klopusz.engine.impl.TerritoryImpl;
import pl.edu.prz.klopusz.utilities.decorators.ScoreCalculator;

public class CampCalculator implements ScoreCalculator {
    Territory whiteTerritory;
    Territory blackTerritory;

    private final Board board;

    public CampCalculator(Board board) {
        this.board = board;
        whiteTerritory = new TerritoryImpl();
        blackTerritory = new TerritoryImpl();
    }

    @Override
    public void calculate() {
        whiteTerritory.clear();
        blackTerritory.clear();

        constructTerritory(whiteTerritory, Piece.Color.WHITE);
        constructTerritory(blackTerritory, Piece.Color.BLACK);
    }

    void constructTerritory(Territory territory, Piece.Color color) {
        CampBorder cb = new CampBorder(color);
        val next = cb.next();
        for(int i = cb.lower; i != cb.upper; i = next.apply(i)) {
            for(int j = cb.lower; j != cb.upper; j = next.apply(j)) {
                val square = board.get(i, j);
                if(square.isEmpty())
                    continue;
                if(square.getColor() == color) {
                    territory.add(square);
                }
            }
        }
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
