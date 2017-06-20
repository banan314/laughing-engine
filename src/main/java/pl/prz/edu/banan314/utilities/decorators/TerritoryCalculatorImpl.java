package pl.prz.edu.banan314.utilities.decorators;

import pl.prz.edu.banan314.game.Board;
import pl.prz.edu.banan314.game.Square;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kamil on 19.06.17.
 */
public class TerritoryCalculatorImpl implements TerritoryCalculator {

    public static final int MIN_INDEX = 0;
    public static final int MAX_INDEX = 8;

    private final Board board;
    private CELL_STATE[][] cellBoard = new CELL_STATE[10][10]; //board is 9x9, but we index from 1

    private int whiteScore, blackScore;

    TerritoryCalculatorImpl(Board board) {
        this.board = board;
    }

    /*TODO: test it!!!!!!*/
    @Override
    public void calculate() {
        initializeCellBoard();
        copyStatesFromGameBoard();

        while(shouldContinueCalculation()) {
            traverseBoard((i, j, state) -> {
                processCellState(i, j, state);
            });
        }

        ExtendedInteger scoreBlack = new ExtendedInteger(0),
                scoreWhite = new ExtendedInteger(0);
        traverseBoard((i, j, state) -> {
            if(state == CELL_STATE.SEEDED_WHITE)
                scoreWhite.increment();
            else if(state == CELL_STATE.SEEDED_BLACK)
                scoreBlack.increment();
        });

        whiteScore = scoreWhite.getValue();
        blackScore = scoreBlack.getValue();
    }

    @Override
    public int getWhite() {
        return whiteScore;
    }

    @Override
    public int getBlack() {
        return blackScore;
    }

    private void processCellState(int file, int row, CELL_STATE state) {
        List<CELL_STATE> adjacent;
        switch (state) {
            case EMPTY:
                adjacent = getNeighbors(file, row);
                boolean white = false, black = false;
                for(CELL_STATE square : adjacent) {
                    if (square.isEmpty()) {
                        continue;
                    }
                    if (square.isWhite()) {
                        white = true;
                    }
                    if(square.isBlack()) {
                        black = true;
                    }
                }
                if(white && black)
                    cellBoard[file][row] = CELL_STATE.SHARED;
                else {
                    if (white) {
                        cellBoard[file][row] = CELL_STATE.SEEDED_WHITE;
                    }
                    else { //black
                        cellBoard[file][row] = CELL_STATE.SEEDED_BLACK;
                    }
                }
                break;
            case OCCUPIED_WHITE:
            case OCCUPIED_BLACK:
                //TODO: needed?
                break;
            case SEEDED_WHITE:
            case SEEDED_BLACK:
                adjacent = getNeighbors(file, row);
                for(CELL_STATE square : adjacent) {
                    if (square == CELL_STATE.SHARED)
                        cellBoard[file][row] = CELL_STATE.SHARED;
                }
                break;
        }
    }

    protected List<CELL_STATE> getNeighbors(int file, int row) {
        List<CELL_STATE> adjacent = new ArrayList<>();

        if(file > MIN_INDEX)
            adjacent.add(cellBoard[file-1][row]);
        if(file < MAX_INDEX)
            adjacent.add(cellBoard[file+1][row]);
        if(row > MIN_INDEX)
            adjacent.add(cellBoard[file][row-1]);
        if(row < MAX_INDEX)
            adjacent.add(cellBoard[file][row+1]);

        return adjacent;
    }

    private boolean shouldContinueCalculation() {
        ExtendedBoolean should = new ExtendedBoolean(true);
        traverseBoard((i, j, state) -> {
            if (state == CELL_STATE.EMPTY || state == CELL_STATE.CONTAMINATED) {
                should.set(false);
            }
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
            Square square = board.get(i, j);
            if (!square.isEmpty()) {
                if(square.getPiece().isBlack())
                    cellBoard[i][j] = CELL_STATE.OCCUPIED_BLACK;
                else {
                    assert square.getPiece().isWhite();
                    cellBoard[i][j] = CELL_STATE.OCCUPIED_WHITE;
                }
            }
        });
    }

    private void initializeCellBoard() {
        traverseBoard((i, j, state) -> {
            cellBoard[i][j] = CELL_STATE.EMPTY;
        });
    }

    /*
    * EMPTY - no piece on square
    * SEEDED - square analyzed (by growth algorithm)
    * OCCUPIED - piece already on square
    * SHARED - piece with access to both black and white pieces
    * CONTAMINATED - temporary state (from SEEDED to SHARED), used to tell when to terminate
    * */
    protected enum CELL_STATE {
        EMPTY,
        SEEDED_WHITE, SEEDED_BLACK,
        OCCUPIED_WHITE, OCCUPIED_BLACK,
        SHARED, CONTAMINATED;

        public boolean isEmpty() {
            return this.equals(EMPTY);
        }

        public boolean isWhite() {
            return this.equals(SEEDED_WHITE) || this.equals(OCCUPIED_WHITE);
        }

        public boolean isBlack() {
            return this.equals(SEEDED_BLACK) || this.equals(OCCUPIED_BLACK);
        }
    }

    protected interface Command {
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

    private class ExtendedInteger {
        public int getValue() {
            return value;
        }

        public void setValue(int value) {
            this.value = value;
        }

        int value;

        public ExtendedInteger(int value) {
            this.value = value;
        }

        public void increment() {
            value++;
        }
    }
}
