package pl.prz.edu.banan314.application.model.game;

import org.ggp.base.util.gdl.grammar.GdlSentence;

import java.util.Scanner;

import static java.lang.Math.abs;

/**
 * Created by kamil on 04.06.17.
 */
public class Square {

    byte file;
    byte row;
    boolean empty = true;
    Piece piece;

    Square() {}

    public Square(Piece piece) {
        this.piece = piece;
    }

    public byte getFile() {
        return file;
    }

    public void setFile(byte file) {
        this.file = file;
    }

    public byte getRow() {
        return row;
    }

    public void setRow(byte row) {
        this.row = row;
    }

    public void setFileRow(byte file, byte row) {
        this.file = file;
        this.row = row;
    }

    public Piece getPiece() {
        return piece;
    }

    public void setPiece(Piece piece) {
        this.empty = false;
        this.piece = piece;
    }

    public boolean isAdjacent(Square other) {
        return abs(file-other.file) <= 1 && abs(row-other.row) <= 1;
    }

    public boolean isEmpty() {
        return empty;
    }

    /*TODO: simplify with GdlSentence.get(index)*/
    public static Square from(GdlSentence sentence) {
        Square square = new Square();
        square.setPiece(Piece.WHITE);

        String cell = sentence.toString();
        assert cell.length() > 5;
        if(isCell(cell)) {
            Scanner scanner = makeScanner(cell);

            byte row = scanner.nextByte();
            byte file = scanner.nextByte();

            square.setFile(file);
            square.setRow(row);
        }

        return square;
    }

    static Scanner makeScanner(String cell) {
        int startIndex = cell.indexOf('l') + 2;
        //index of 'l' in cell shifted 2
        // chars right
        String squareDescription = cell.substring(startIndex);
        return new Scanner(squareDescription);
    }

    public static boolean isCell(String cell) {
        return cell.matches("(.*cell.*)");
    }
}