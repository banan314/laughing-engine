package pl.edu.prz.klopusz.engine.impl;

import lombok.val;
import pl.edu.prz.klopusz.application.model.game.Square;
import pl.edu.prz.klopusz.engine.api.Territory;

import java.util.HashSet;
import java.util.Set;

public class TerritoryImpl implements Territory {
    Set<Square> squares = new HashSet<>();

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
