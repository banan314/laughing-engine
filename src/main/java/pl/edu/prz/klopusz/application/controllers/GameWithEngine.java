package pl.edu.prz.klopusz.application.controllers;

import pl.edu.prz.klopusz.application.model.BoardModel;
import pl.edu.prz.klopusz.application.model.game.Move;
import pl.edu.prz.klopusz.application.model.game.Piece;

public interface GameWithEngine {
    Piece.Color getPlayerColor();
    void setPlayerColor(Piece.Color color);

    void setBoardModel(BoardModel model);
    Move makeMove();

    void swapPlayer();
}
