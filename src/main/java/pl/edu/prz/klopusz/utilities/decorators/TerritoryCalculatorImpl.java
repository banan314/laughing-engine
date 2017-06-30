package pl.edu.prz.klopusz.utilities.decorators;

import pl.edu.prz.klopusz.application.model.game.Square;
import pl.edu.prz.klopusz.application.model.game.Board;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by kamil on 19.06.17.
 */

public class TerritoryCalculatorImpl implements TerritoryCalculator {

    public static final int MIN_INDEX = 1;
    public static final int MAX_INDEX = 9;

    private final Board board;
    private CellState[][] cellBoard = new CellState[10][10]; //board is 9x9, but we index from 1
    private CellState[][] rememberedBoard = new CellState[10][10];

    private int whiteScore, blackScore;

    TerritoryCalculatorImpl(Board board) {
        this.board = board;
    }

    //FIXME: the cell state board is changing every time we process single cell. Should work on remembered board from
    // previous step
    /*TODO: test it!!!!!!*/
    @Override
    public void calculate() {
        initializeCellBoard();
        copyStatesFromGameBoard();
        copyToRemembered();

        while(shouldContinueCalculation()) {
            traverseBoard((i, j, state) -> {
                processCellState(i, j, state);
            });
            copyToRemembered();
        }

        ExtendedInt scoreBlack = new ExtendedInt(0),
            scoreWhite = new ExtendedInt(0);
        traverseBoard((i, j, state) -> {
            if(state == CellState.SEEDED_WHITE)
                scoreWhite.increment();
            else if(state == CellState.SEEDED_BLACK)
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

    private void processCellState(int file, int row, CellState state) {
        List<CellState> adjacent;
        switch (state) {
            case EMPTY:
                //TODO: should take 8-neighbors
                adjacent = getNeighbors(file, row);
                boolean white = false, black = false, shared = false;
                for(CellState square : adjacent) {
                    if(square.isDame())
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
                    cellBoard[file][row] = CellState.SHARED;
                else {
                    if (white) {
                        cellBoard[file][row] = CellState.SEEDED_WHITE;
                    }
                    else { //black
                        cellBoard[file][row] = CellState.SEEDED_BLACK;
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
                for(CellState neighbor : adjacent) {
                    if (neighbor.isDame())
                        cellBoard[file][row] = CellState.CONTAMINATED;
                    if(neighbor.isSeeded() && neighbor != state) {
                        cellBoard[file][row] = CellState.CONTAMINATED;
                    }
                }
                break;
            case CONTAMINATED:
                System.out.println("contaminated: " + file + ", " + row);
                cellBoard[file][row] = CellState.SHARED;
                break;
        }
    }

    private void copyToRemembered() {
        rememberedBoard = Arrays.copyOf(cellBoard, cellBoard.length);
    }

    protected List<CellState> getNeighbors(int file, int row) {
//        return get4Neighbors(file, row);
        return get8Neighbors(file, row);
    }

    private List<CellState> get8Neighbors(int file, int row) {
        List<CellState> adjacent = get4Neighbors(file, row);

        if(file > MIN_INDEX && row > MIN_INDEX)
            adjacent.add(cellBoard[file-1][row-1]);
        if(file > MIN_INDEX && row < MAX_INDEX)
            adjacent.add(cellBoard[file-1][row+1]);
        if(file < MAX_INDEX && row > MIN_INDEX)
            adjacent.add(cellBoard[file+1][row-1]);
        if(file < MAX_INDEX && row < MAX_INDEX)
            adjacent.add(cellBoard[file+1][row+1]);

        return adjacent;
    }

    private List<CellState> get4Neighbors(int file, int row) {
        List<CellState> adjacent = new ArrayList<>();

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

    //FIXME: bad condition - should be no change
    private boolean shouldContinueCalculation() {
        Mutable<Boolean> should = new Mutable<>(false);
        traverseBoard((i, j, state) -> {
            if (state == CellState.EMPTY || state == CellState.CONTAMINATED) {
                should.set(true);
            }
        });
        return should.get();
    }

    private void traverseBoard(Command method) {
        for(int i = 1; i < 10; i++) {
            for(int j = 1; j < 10; j++) {
                method.execute(i, j, rememberedBoard[i][j]);
            }
        }
    }

    private void copyStatesFromGameBoard() {
        traverseBoard((i, j, state) -> {
            Square square = board.get(i, j);
            if (!square.isEmpty()) {
                if(square.getPiece().isBlack())
                    cellBoard[i][j] = CellState.OCCUPIED_BLACK;
                else {
                    assert square.getPiece().isWhite();
                    cellBoard[i][j] = CellState.OCCUPIED_WHITE;
                }
            }
        });
    }

    private void initializeCellBoard() {
        traverseBoard((i, j, state) -> {
            cellBoard[i][j] = CellState.EMPTY;
        });
    }

    /*
    * EMPTY - no piece on square
    * SEEDED - square analyzed (by growth algorithm)
    * OCCUPIED - piece already on square
    * SHARED - piece with access to both black and white pieces
    * CONTAMINATED - temporary state (from SEEDED to SHARED), used to tell when to terminate
    * */
    protected enum CellState {
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

        public boolean isSeeded() {
            return this.equals(SEEDED_WHITE) || this.equals(SEEDED_BLACK);
        }

        public boolean isDame() {
            return this.equals(SHARED) || this.equals(CONTAMINATED);
        }
    }

    protected interface Command {
        public void execute(int x, int y, CellState state);
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
