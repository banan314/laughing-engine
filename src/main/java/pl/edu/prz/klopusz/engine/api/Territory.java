package pl.edu.prz.klopusz.engine.api;

import pl.edu.prz.klopusz.application.model.game.Square;

/**
 * Territory isolated by one of the players. It contains all the squares a player
 * gains points for. It can be imagined as a cluster of single areas. Territory is
 * not assumed to be contagious, but it can be so if the player has isolated only one area.
 */
public interface Territory {
    boolean contains(Square square);
    void add(Square square);
    int size();
}
