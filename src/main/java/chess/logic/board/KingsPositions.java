package chess.logic.board;

import chess.logic.figures.FigureColor;

import static chess.logic.figures.FigureColor.WHITE;

public class KingsPositions {
    private int whiteKingRow = -1;
    private int whiteKingColumn = -1;
    private int blackKingRow = -1;
    private int blackKingColumn = -1;

    public KingsPositions() {
    }

    public void setKingPosition(FigureColor figureColor, int column, int row) {
        if (figureColor == WHITE) {
            whiteKingColumn = column;
            whiteKingRow = row;
        } else {
            blackKingColumn = column;
            blackKingRow = row;
        }
    }

    public int getKingColumn(FigureColor figureColor) {
        if (figureColor == WHITE) {
            return whiteKingColumn;
        }
        return blackKingColumn;
    }

    public int getWhiteKingRow() {
        return whiteKingRow;
    }

    public int getWhiteKingColumn() {
        return whiteKingColumn;
    }

    public int getBlackKingRow() {
        return blackKingRow;
    }

    public int getBlackKingColumn() {
        return blackKingColumn;
    }
}
