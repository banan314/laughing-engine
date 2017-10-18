package pl.edu.prz.klopusz.application.model.game;

import lombok.val;

public class SavedState {
    Board board;
    private Piece.Color turn;

    public SavedState(Board board) {
        this.board = board;
    }

    public Board getBoard() {
        return board;
    }

    public SavedState turn(Piece.Color color) {
        this.turn = color;
        return this;
    }

    public static Board loadBoard(String serialized) {
        Board board = new DolarBoard();
        board.initialize();

        assert serialized.length() == Board.MAX_INDEX * Board.MAX_INDEX;

        for(byte file = Board.MIN_INDEX; file <= Board.MAX_INDEX; file++) {
            for(byte row = Board.MIN_INDEX; row <= Board.MAX_INDEX; row++) {
                switch (serialized.charAt(index(row, file))) {
                    case '0':
                        board.set(row, file, new Square()); //empty
                        break;
                    case '1':
                        board.set(row, file, new Square(Piece.WHITE));
                        break;
                    case '2':
                        board.set(row, file, new Square(Piece.BLACK));
                        break;
                }
            }
        }
        return board;
    }

    public static Piece.Color loadColor(String serialized) {
        switch (serialized.charAt(0)) {
            case '1': return Piece.Color.WHITE;
            case '2': return Piece.Color.BLACK;
            default: throw new RuntimeException("no such color");
        }
    }

    static int index(byte row, byte file) {
        return (file-1)*Board.MAX_INDEX + row-1;
    }

    @Override
    @Deprecated
    public String toString() {
        StringBuffer sb = new StringBuffer("");
        for(byte file = Board.MIN_INDEX; file <= Board.MAX_INDEX; file++) {
            for(byte row = Board.MIN_INDEX; row <= Board.MAX_INDEX; row++) {
                val piece = board.get(row, file);
                if(piece.isEmpty())
                    sb.append(emptyToByte());
                else {
                    sb.append(colorToByte(piece.getPiece().color));
                }
            }
        }
        //TODO?
        return sb.toString();
    }

    byte emptyToByte() {
        return '0';
    }

    byte colorToByte(Piece.Color color) {
        switch (color) {
            case WHITE:
                return '1';
            case BLACK:
                return '2';
        }
        throw new RuntimeException("no such color");
    }

    public byte[] toBytes() {
        val bytes = new byte[Board.MAX_INDEX * Board.MAX_INDEX + 2];
        for(byte file = Board.MIN_INDEX; file <= Board.MAX_INDEX; file++) {
            for(byte row = Board.MIN_INDEX; row <= Board.MAX_INDEX; row++) {
                val piece = board.get(row, file);
                if(piece.isEmpty())
                    bytes[index(row, file)] = emptyToByte();
                else {
                    bytes[index(row, file)] = colorToByte(piece.getPiece().color);
                }
            }
        }
        bytes[Board.MAX_INDEX * Board.MAX_INDEX] = '\n';
        if(null != turn) {
            bytes[Board.MAX_INDEX * Board.MAX_INDEX + 1] = colorToByte(turn);
        } else {
            bytes[Board.MAX_INDEX * Board.MAX_INDEX + 1] = '3';
        }
        return bytes;
    }
}
