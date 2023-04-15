package chess.logic.figures;

import chess.logic.moves.FigureMove;

import java.util.ArrayList;
import java.util.List;

public class Rook extends Figure {
    private boolean isFirstMove = true;

    public Rook(FigureColor color) {
        super(color, false);
    }

    @Override
    public List<FigureMove> getAllMovesRegardingToBoard() {
        List<FigureMove> moves = new ArrayList<>();
        addRookMoves(moves);

        return moves;
    }

    protected static void addRookMoves(List<FigureMove> moves) {
        for (int column = -1; column > -8; column--)
            moves.add(new FigureMove(column, 0));

        for (int column = 1; column < 8; column++)
            moves.add(new FigureMove(column, 0));

        for (int row = -1; row > -8; row--)
            moves.add(new FigureMove(0, row));

        for (int row = 1; row < 8; row++)
            moves.add(new FigureMove(0, row));
    }

    public boolean isFirstMove() {
        return isFirstMove;
    }

    public void setFirstMove(boolean firstMove) {
        isFirstMove = firstMove;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        return o != null && getClass() == o.getClass();
    }

    @Override
    public String toString() {
        return getColorSymbol() + "R";
    }

    @Override
    public String getColorSymbol() {
        return (color == FigureColor.WHITE) ? "w" : "b";
    }
}
