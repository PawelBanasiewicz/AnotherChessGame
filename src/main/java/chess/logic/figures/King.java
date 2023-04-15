package chess.logic.figures;

import chess.logic.moves.FigureMove;

import java.util.ArrayList;
import java.util.List;

import static chess.logic.figures.FigureColor.WHITE;

public class King extends Figure {
    private boolean isFirstMove = true;
    private boolean isInCheck = false;

    public King(FigureColor color) {
        super(color, false);
    }

    @Override
    public List<FigureMove> getAllMovesRegardingToBoard() {
        List<FigureMove> moves = new ArrayList<>();
        addKingMoves(moves, true);
        addKingMoves(moves, false);
        return moves;
    }

    private void addKingMoves(List<FigureMove> moves, boolean haveToCapture) {
        moves.add(new FigureMove(-1, -1));
        moves.add(new FigureMove(-1, 0));
        moves.add(new FigureMove(-1, 1));
        moves.add(new FigureMove(0, -1));
        moves.add(new FigureMove(0, 1));
        moves.add(new FigureMove(1, -1));
        moves.add(new FigureMove(1, 0));
        moves.add(new FigureMove(1, 1));
    }

    public boolean isFirstMove() {
        return isFirstMove;
    }

    public void setFirstMove(boolean firstMove) {
        isFirstMove = firstMove;
    }

    public boolean isInCheck() {
        return isInCheck;
    }

    public void setInCheck(boolean inCheck) {
        isInCheck = inCheck;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        return o != null && getClass() == o.getClass();
    }

    @Override
    public String toString() {
        return getColorSymbol() + "K";
    }

    @Override
    public String getColorSymbol() {
        return (color == WHITE) ? "w" : "b";
    }
}
