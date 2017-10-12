package pl.edu.prz.klopusz.engine.impl;

import lombok.val;
import pl.edu.prz.klopusz.application.model.game.Move;
import pl.edu.prz.klopusz.application.model.game.Square;
import pl.edu.prz.klopusz.engine.api.Territory;

import java.util.ArrayList;
import java.util.List;

public class TerritoryImpl implements Territory {
    List<Square> squares = new ArrayList<>();

    @Override
    public boolean contains(Square square) {
        for(val s : squares) {
            if (s.getRow() != square.getRow()) {
                continue;
            }
            if (s.getFile() == square.getFile()) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void add(Square square) {
        squares.add(square);
    }

    @Override
    public int size() {
        return squares.size();
    }
}
