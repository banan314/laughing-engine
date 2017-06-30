package pl.edu.prz.klopusz.utilities.decorators;

import pl.edu.prz.klopusz.application.model.game.Square;
import pl.edu.prz.klopusz.application.model.game.Board;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kamil on 19.06.17.
 */

public class TerritoryCalculatorImpl implements TerritoryCalculator {

    public static final int MIN_INDEX = 1;
    public static final int MAX_INDEX = 9;

    public static boolean DEBUG = false;

    private final Board board;
    private CellState[][] cellBoard = new CellState[10][10]; //board is 9x9, but we index from 1
    private CellState[][] rememberedBoard = new CellState[10][10];

    private int whiteScore, blackScore;

    TerritoryCalculatorImpl(Board board) {
        this.board = board;
    }

    /*TODO: test it!!!!!!*/
    /*FIXME: cell 1,1 is empty sometimes (initially)*/
    @Override
    public void calculate() {
        initializeCellBoard();
        copyStatesFromGameBoard();

        if (DEBUG) {
            System.out.println("---------------------------------");
        }
        do {
            copyToRemembered();
            traverseBoard((i, j, state) -> {
                processCellState(i, j, state);
            });
            if (DEBUG) {
                debugBoardState();
            }
        } while(shouldContinueCalculation());

        if (DEBUG) {
            System.out.println("---------------------------------");
        }

        ExtendedInt scoreBlack = new ExtendedInt(0), scoreWhite = new ExtendedInt(0);
        traverseBoard((i, j, state) -> {
            if (state == CellState.SEEDED_WHITE) {
                scoreWhite.increment();
            } else if (state == CellState.SEEDED_BLACK) {
                scoreBlack.increment();
            }
        });

        whiteScore = scoreWhite.get();
        blackScore = scoreBlack.get();
    }

    private void initializeCellBoard() {
        traverseBoard((i, j, state) -> {
            cellBoard[i][j] = CellState.EMPTY;
        });
    }

    private void copyStatesFromGameBoard() {
        traverseBoard((i, j, state) -> {
            Square square = board.get(i, j);
            if (!square.isEmpty()) {
                if (square.getPiece().isBlack()) {
                    cellBoard[i][j] = CellState.OCCUPIED_BLACK;
                } else {
                    assert square.getPiece().isWhite();
                    cellBoard[i][j] = CellState.OCCUPIED_WHITE;
                }
            }
        });
    }

    private void processCellState(final int file, final int row, final CellState state) {
        List<CellState> adjacent;
        switch (state) {
            case EMPTY:
                adjacent = getNeighbors(file, row);
                checkEmptyCellNeighbors(file, row, adjacent);
                break;
            case OCCUPIED_WHITE:
            case OCCUPIED_BLACK:
                //TODO: needed?
                break;
            case SEEDED_WHITE:
            case SEEDED_BLACK:
                adjacent = getNeighbors(file, row);
                checkSeededCellNeighbors(file, row, state, adjacent);
                break;
            case CONTAMINATED:
                cellBoard[file][row] = CellState.SHARED;
                break;
        }
    }

    private void checkSeededCellNeighbors(int file, int row, CellState state, List<CellState> adjacent) {
        for(CellState neighbor : adjacent) {
            if (neighbor.isDame()) {
                cellBoard[file][row] = CellState.CONTAMINATED;
            }
            if (neighbor.isSeeded() && neighbor != state) {
                cellBoard[file][row] = CellState.CONTAMINATED;
            }
        }
    }

    private void checkEmptyCellNeighbors(int file, int row, List<CellState> adjacent) {
        boolean white = false, black = false, shared = false;
        for(CellState square : adjacent) {
            if (square.isDame()) {
                shared = true;
            }
            if (square.isEmpty()) {
                continue;
            }
            if (square.isWhite()) {
                white = true;
            }
            if (square.isBlack()) {
                black = true;
            }
        }
        if (shared || (white && black)) {
            cellBoard[file][row] = CellState.SHARED;
        } else {
            if (white) {
                cellBoard[file][row] = CellState.SEEDED_WHITE;
            } else if (black) {
                cellBoard[file][row] = CellState.SEEDED_BLACK;
            }
            //else nothing can be said
        }
    }

    protected List<CellState> getNeighbors(int file, int row) {
//        return get4Neighbors(file, row);
        return get8Neighbors(file, row);
    }

    private List<CellState> get8Neighbors(int file, int row) {
        List<CellState> adjacent = get4Neighbors(file, row);

        if (file > MIN_INDEX && row > MIN_INDEX) {
            adjacent.add(cellBoard[file-1][row-1]);
        }
        if (file > MIN_INDEX && row < MAX_INDEX) {
            adjacent.add(cellBoard[file-1][row+1]);
        }
        if (file < MAX_INDEX && row > MIN_INDEX) {
            adjacent.add(cellBoard[file+1][row-1]);
        }
        if (file < MAX_INDEX && row < MAX_INDEX) {
            adjacent.add(cellBoard[file+1][row+1]);
        }

        return adjacent;
    }

    private List<CellState> get4Neighbors(int file, int row) {
        List<CellState> adjacent = new ArrayList<>();

        if (file > MIN_INDEX) {
            adjacent.add(rememberedBoard[file-1][row]);
        }
        if (file < MAX_INDEX) {
            adjacent.add(rememberedBoard[file+1][row]);
        }
        if (row > MIN_INDEX) {
            adjacent.add(rememberedBoard[file][row-1]);
        }
        if (row < MAX_INDEX) {
            adjacent.add(rememberedBoard[file][row+1]);
        }
        return adjacent;
    }

    private boolean shouldContinueCalculation() {
        Mutable<Boolean> should = new Mutable<>(false);
        traverseBoard((i, j, state) -> {
            /*if (state == CellState.EMPTY || state == CellState.CONTAMINATED) {
                should.set(true);
            }*/
            if (state != cellBoard[i][j]) {
                should.set(true);
            }
        });
        return should.get();
    }

    private void copyToRemembered() {
        for(int i = 1; i < 10; i++) {
            for(int j = 1; j < 10; j++) {
                rememberedBoard[i][j] = cellBoard[i][j];
            }
        }
    }

    private void debugBoardState() {
        for(int j = 9; j > 0; j--) {
            for(int k = 1; k < 10; k++) {
                System.out.printf("%2s", rememberedBoard[k][j]);
            }
            System.out.println();
        }
        System.out.println();
    }

    @Override
    public int getWhite() {
        return whiteScore;
    }

    @Override
    public int getBlack() {
        return blackScore;
    }

    private void traverseBoard(Command method) {
        for(int i = 1; i < 10; i++) {
            for(int j = 1; j < 10; j++) {
                method.execute(i, j, rememberedBoard[i][j]);
            }
        }
    }

    /*CONTAMINATED is not needed anymore*/
    /*
    * EMPTY - no piece on square
    * SEEDED - square analyzed (by growth algorithm)
    * OCCUPIED - piece already on square
    * SHARED - piece with access to both black and white pieces
    * CONTAMINATED - temporary state (from SEEDED to SHARED), used to tell when to terminate
    * */
    protected enum CellState {
        EMPTY, SEEDED_WHITE, SEEDED_BLACK, OCCUPIED_WHITE, OCCUPIED_BLACK, SHARED, CONTAMINATED;

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

        //needed for debugging
        @Override
        public String toString() {
            char rc = '?';
            switch (this) {
                case EMPTY:
                    rc = 'E';
                    break;
                case SEEDED_WHITE:
                    rc = 'W';
                    break;
                case SEEDED_BLACK:
                    rc = 'B';
                    break;
                case OCCUPIED_WHITE:
                    rc = 'ｏ';
                    break;
                case OCCUPIED_BLACK:
                    rc = 'ꝋ';
                    break;
                case SHARED:
                    rc = 'S';
                    break;
                case CONTAMINATED:
                    rc = 'C';
                    break;
            }
            return String.valueOf(rc);
        }

    }

    protected interface Command {
        void execute(final int x, final int y, final CellState state);
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
