package pl.prz.edu.banan314.utilities.decorators;

import pl.prz.edu.banan314.application.model.game.Board;
import pl.prz.edu.banan314.application.model.game.Square;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * Created by kamil on 19.06.17.
 */
public class TerritoryCalculatorImpl implements TerritoryCalculator {

    public static final int MIN_INDEX = 0;
    public static final int MAX_INDEX = 8;

    private final Board board;
    private CellStateEnum[][] cellBoard = new CellStateEnum[10][10]; //board is 9x9, but we index from 1

    private Stream<CellState> stateStream = IntStream.range(0, 64)
            .mapToObj(i -> {
                int row = i / 8;
                int column = i % 8;
                return new CellState(row, column, cellBoard[row][column]);
            });

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

        ExtendedInt scoreBlack = new ExtendedInt(0),
            scoreWhite = new ExtendedInt(0);
        traverseBoard((i, j, state) -> {
            if(state == CellStateEnum.SEEDED_WHITE)
                scoreWhite.increment();
            else if(state == CellStateEnum.SEEDED_BLACK)
                scoreBlack.increment();
        });

        whiteScore = scoreWhite.get();
        blackScore = scoreBlack.get();
    }

    @Override
    public int getWhite() {
        return whiteScore;
    }

    @Override
    public int getBlack() {
        return blackScore;
    }

    private void processCellState(int file, int row, CellStateEnum state) {
        List<CellStateEnum> adjacent;
        switch (state) {
            case EMPTY:
                adjacent = getNeighbors(file, row);
                boolean white = false, black = false;
                for(CellStateEnum square : adjacent) {
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
                    cellBoard[file][row] = CellStateEnum.SHARED;
                else {
                    if (white) {
                        cellBoard[file][row] = CellStateEnum.SEEDED_WHITE;
                    }
                    else { //black
                        cellBoard[file][row] = CellStateEnum.SEEDED_BLACK;
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
                for(CellStateEnum square : adjacent) {
                    if (square == CellStateEnum.SHARED)
                        cellBoard[file][row] = CellStateEnum.SHARED;
                }
                break;
        }
    }

    protected List<CellStateEnum> getNeighbors(int file, int row) {
        List<CellStateEnum> adjacent = new ArrayList<>();

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
        Mutable<Boolean> should = new Mutable<>(true);
        traverseBoard((i, j, state) -> {
            if (state == CellStateEnum.EMPTY || state == CellStateEnum.CONTAMINATED) {
                should.set(false);
            }
        });
        return should.get();
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
                    cellBoard[i][j] = CellStateEnum.OCCUPIED_BLACK;
                else {
                    assert square.getPiece().isWhite();
                    cellBoard[i][j] = CellStateEnum.OCCUPIED_WHITE;
                }
            }
        });
    }

    private void initializeCellBoard() {
        traverseBoard((i, j, state) -> {
            cellBoard[i][j] = CellStateEnum.EMPTY;
        });
    }

    /*
    * EMPTY - no piece on square
    * SEEDED - square analyzed (by growth algorithm)
    * OCCUPIED - piece already on square
    * SHARED - piece with access to both black and white pieces
    * CONTAMINATED - temporary state (from SEEDED to SHARED), used to tell when to terminate
    * */
    protected enum CellStateEnum {
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
        public void execute(int x, int y, CellStateEnum state);
    }

    private static class CellState {
        private final int row;
        private final int column;
        private final CellStateEnum state;

        public CellState(int row, int column, CellStateEnum state) {
            this.row = row;
            this.column = column;
            this.state = state;
        }

        public int getRow() {
            return row;
        }

        public int getColumn() {
            return column;
        }

        public CellStateEnum getState() {
            return state;
        }

        @Override
        public String toString() {
            return "Cell{" +
                    "row=" + row +
                    ", column=" + column +
                    ", state=" + state +
                    '}';
        }
    }

    public class Mutable<T> {
        T it;

        public Mutable(T it) {
            this.it = it;
        }

        public void set(T it) {
            this.it = it;
        }

        public T get() {
            return it;
        }
    }

    private class ExtendedInt extends Mutable<Integer> {

        public ExtendedInt(Integer it) {
            super(it);
        }

        public void increment() {
            super.it++;
        }
    }
}
