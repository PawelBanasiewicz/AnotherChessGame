package chess.utils;

import chess.logic.board.Square;

public class Constants {
    public static final int FIRST_COLUMN = 0;
    public static final int LAST_COLUMN = 8;
    public static final int FIRST_ROW = 0;
    public static final int LAST_ROW = 8;

    public static final int WHITE_PAWN_START_ROW = 6;
    public static final int WHITE_PAWN_DOUBLE_SQUARE_MOVE_ROW = 4;
    public static final int BLACK_PAWN_DOUBLE_SQUARE_MOVE_ROW = 3;
    public static final int BLACK_PAWN_START_ROW = 1;

    public static final int WHITE_PROMOTION_ROW = 0;
    public static final int BLACK_PROMOTION_ROW = 7;

    public static final int WHITE_CASTLE_ROW = 7;
    public static final int BLACK_CASTLE_ROW = 0;
    public static final int KING_SHORT_CASTLE_COLUMN = 6;
    public static final int KING_LONG_CASTLE_COLUMN = 2;
    public static final int ROOK_SHORT_CASTLE_COLUMN = 5;
    public static final int ROOK_LONG_CASTLE_COLUMN = 3;

    public static final Square WHITE_KING_SHORT_CASTLE_SQUARE = new Square(KING_SHORT_CASTLE_COLUMN, WHITE_CASTLE_ROW);
    public static final Square WHITE_KING_LONG_CASTLE_SQUARE = new Square(KING_LONG_CASTLE_COLUMN, WHITE_CASTLE_ROW);
    public static final Square BLACK_KING_SHORT_CASTLE_SQUARE = new Square(KING_SHORT_CASTLE_COLUMN, BLACK_CASTLE_ROW);
    public static final Square BLACK_KING_LONG_CASTLE_SQUARE = new Square(KING_LONG_CASTLE_COLUMN, BLACK_CASTLE_ROW);

    public static final Square WHITE_ROOK_SHORT_CASTLE_SQUARE = new Square(ROOK_SHORT_CASTLE_COLUMN, WHITE_CASTLE_ROW);
    public static final Square WHITE_ROOK_LONG_CASTLE_SQUARE = new Square(ROOK_LONG_CASTLE_COLUMN, WHITE_CASTLE_ROW);
    public static final Square BLACK_ROOK_SHORT_CASTLE_SQUARE = new Square(ROOK_SHORT_CASTLE_COLUMN, BLACK_CASTLE_ROW);
    public static final Square BLACK_ROOK_LONG_CASTLE_SQUARE = new Square(ROOK_LONG_CASTLE_COLUMN, BLACK_CASTLE_ROW);

    public static final String NEW_GAME = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1";
}
