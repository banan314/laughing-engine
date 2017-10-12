package pl.edu.prz.klopusz.application.model.game;

import lombok.val;

public class SavedState {
    Board board;

    public SavedState(Board board) {
        this.board = board;
    }

    public Board getBoard() {
        return board;
    }

    public static Board loadBoard(String serialized) {
        Board board = new DolarBoard();

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

    static int index(byte row, byte file) {
        return (file-1)*Board.MAX_INDEX + row-1;
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer("");
        for(byte file = Board.MIN_INDEX; file <= Board.MAX_INDEX; file++) {
            for(byte row = Board.MIN_INDEX; row <= Board.MAX_INDEX; row++) {
                val piece = board.get(row, file);
                if(piece.isEmpty())
                    sb.append('0');
                else {
                    if(piece.getPiece().isWhite())
                        sb.append('1');
                    else
                        sb.append('2');
                }
            }
        }
        return sb.toString();
    }

    public byte[] toBytes() {
        val bytes = new byte[Board.MAX_INDEX * Board.MAX_INDEX];
        for(byte file = Board.MIN_INDEX; file <= Board.MAX_INDEX; file++) {
            for(byte row = Board.MIN_INDEX; row <= Board.MAX_INDEX; row++) {
                val piece = board.get(row, file);
                if(piece.isEmpty())
                    bytes[index(row, file)] = '0';
                else {
                    if(piece.getPiece().isWhite())
                        bytes[index(row, file)] = '1';
                    else
                        bytes[index(row, file)] = '2';
                }
            }
        }
        return bytes;
    }
}
