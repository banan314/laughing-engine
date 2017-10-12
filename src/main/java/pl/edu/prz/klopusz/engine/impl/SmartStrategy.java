package pl.edu.prz.klopusz.engine.impl;

import lombok.val;
import pl.edu.prz.klopusz.application.model.game.Board;
import pl.edu.prz.klopusz.application.model.game.Move;
import pl.edu.prz.klopusz.application.model.game.Piece;
import pl.edu.prz.klopusz.application.model.game.Square;
import pl.edu.prz.klopusz.engine.api.GoalStrategy;

public class SmartStrategy implements GoalStrategy {
    private final Board board;

    SmartStrategy(Board board) {
        this.board = board;
    }

    /*TODO: implement*/
    @Override
    public float goal(Move move) {
        Factors distance1 = new Factors(0.5f, 0.3f, 0.2f, 0.1f);
        Factors distance2 = new Factors(0.4f, 0.24f, 0.16f, 0.08f);
        Factors distance3 = new Factors(0.4f, 0.24f, 0.16f, 0.08f);

        float goal = 0.0f;
        goal += neighbourhood(distance1, (byte) 1, move) / 8;
        goal += neighbourhood(distance2, (byte) 2, move) / 16;
        goal += neighbourhood(distance3, (byte) 3, move) / 24;

        return 0;
    }

    float neighbourhood(Factors factors, byte distance, Move move) {
        float sum = 0.0f;
        val color = move.getPiece().getColor();

        sum += bottomSum(factors, distance, move, color);
        sum += rightSum(factors, distance, move, color);
        sum += topSum(factors, distance, move, color);
        sum += leftSum(factors, distance, move, color);

        return sum;
    }

    float leftSum(Factors factors, byte distance, Move move, Piece.Color color) {
        float sum = 0.0f;
        for(byte d = distance; d > (byte) (-distance); d--) {
            sum += applyFactors(factors, otherSquare(move.square(), -distance, d), color);
        }
        return sum;
    }

    float topSum(Factors factors, byte distance, Move move, Piece.Color color) {
        float sum = 0.0f;
        for(byte d = distance; d > (byte) (-distance); d--) {
            sum += applyFactors(factors, otherSquare(move.square(), d, distance), color);
        }
        return sum;
    }

    float rightSum(Factors factors, byte distance, Move move, Piece.Color color) {
        float sum = 0.0f;
        for(byte d = (byte) -distance; d < distance; d++) {
            sum += applyFactors(factors, otherSquare(move.square(), distance, d), color);
        }
        return sum;
    }

    float bottomSum(Factors factors, byte distance, Move move, Piece.Color color) {
        float sum = 0.0f;
        for(byte d = (byte) -distance; d < distance; d++) {
            sum += applyFactors(factors, otherSquare(move.square(), d, -distance), color);
        }
        return sum;
    }

    private Square otherSquare(Square square, int horizontally, int vertically) {
        return otherSquare(square, (byte) horizontally, (byte) vertically);
    }

    Square otherSquare(Square square, byte horizontally, byte vertically) {
        Square other = new Square(square);
        other.setFileRow((byte) (other.getFile()+vertically), (byte) (other.getRow()+horizontally));
        return other;
    }

    float applyFactors(Factors factors, Square square, Piece.Color color) {
        if (isOutOfTheBoard(square)) {
            return factors.out;
        }
        if (isEmpty(square)) {
            return factors.empty;
        }
        // the content of square is asserted
        if (isAlly(square, color)) {
            return factors.ally;
        }
        if (isEnemy(square, color)) {
            return factors.enemy;
        }
        return 0;
    }

    boolean isOutOfTheBoard(Square square) {
        if (square.getRow() < Board.MIN_INDEX || square.getRow() > Board.MAX_INDEX) {
            return true;
        }
        if (square.getFile() < Board.MIN_INDEX || square.getFile() > Board.MAX_INDEX) {
            return true;
        }
        return false;
    }

    private boolean isEmpty(Square square) {
        return board.get(square.getRow(), square.getFile()).isEmpty();
    }

    boolean isAlly(Square square, Piece.Color color) {
        return board.get(square.getRow(), square.getFile()).getColor() == color;
    }

    boolean isEnemy(Square square, Piece.Color color) {
        return board.get(square.getRow(), square.getFile()).getColor() != color;
    }

    class Factors {
        public float out, ally, enemy, empty;

        public Factors() {
        }

        public Factors(float out, float ally, float enemy, float empty) {
            this.out = out;
            this.ally = ally;
            this.enemy = enemy;
            this.empty = empty;
        }
    }

    static float getCoefficient(int distance) {
        switch (distance) {
            case 1:
                return 1.0f;
            case 2:
                return 0.5f;
            case 3:
                return 0.1f;
            default:
                return 0.0f;
        }
    }
}
