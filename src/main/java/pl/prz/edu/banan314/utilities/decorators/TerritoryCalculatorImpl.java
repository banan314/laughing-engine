package pl.prz.edu.banan314.utilities.decorators;

import pl.prz.edu.banan314.game.Board;
import pl.prz.edu.banan314.game.Square;

import java.util.List;

/**
 * Created by kamil on 19.06.17.
 */
public class TerritoryCalculatorImpl implements TerritoryCalculator {

    private final Board board;
    private CELL_STATE[][] cellBoard = new CELL_STATE[10][10]; //board is 9x9, but we index from 1

    /*
    * EMPTY - no piece on square
    * SEEDED - square analyzed (by growth algorithm)
    * OCCUPIED - piece already on square
    * SHARED - piece with access to both black and white pieces
    * CONTAMINATED - temporary state (from SEEDED to SHARED), used to tell when to terminate
    * */
    protected enum CELL_STATE { EMPTY, SEEDED, OCCUPIED, SHARED, CONTAMINATED }

    TerritoryCalculatorImpl(Board board) {
        this.board = board;
    }

    @Override
    public int calculate() {
        initializeCellBoard();
        copyStatesFromGameBoard();

        while(shouldContinueCalculation()) {
            traverseBoard((i, j, state) -> {
                processCellState(i, j, state);
            });
        }

        return 0;
    }

    private void processCellState(int file, int row, CELL_STATE state) {
        switch (state) {
            case EMPTY:
                List<Square> adjacent = board.getAdjacent(file, row);
                //TODO: change if necessary...
                break;
            case OCCUPIED:
                break;
            case SEEDED:
                //TODO: and so on...
                break;
        }
    }

    private boolean shouldContinueCalculation() {
        ExtendedBoolean should = new ExtendedBoolean(true);
        traverseBoard((i, j, state) -> {
            if(state == CELL_STATE.EMPTY) should.set(false);
        });
        return should.is();
    }

    private void traverseBoard(Command method) {
        for(int i = 1; i < 10; i++) {
            for(int j = 1; j < 10; j++) {
                method.execute(i, j, cellBoard[i][j]);
            }
        }
    }

    private void copyStatesFromGameBoard() {
        traverseBoard((i, j, state) -> {
            if(!board.get(i, j).isEmpty()) {
                cellBoard[i][j] = CELL_STATE.OCCUPIED;
            }
        });
    }

    private void initializeCellBoard() {
        traverseBoard((i, j, state) -> {
            cellBoard[i][j] = CELL_STATE.EMPTY;
        });
    }

    protected interface Command
    {
        public void execute(int x, int y, CELL_STATE state);
    }

    private class ExtendedBoolean {
        boolean bool;

        public ExtendedBoolean(boolean bool) {
            this.bool = bool;
        }

        public void set(boolean bool) {
            this.bool = bool;
        }

        public boolean is() {
            return bool;
        }
    }
}
