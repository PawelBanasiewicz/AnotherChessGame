package chess.logic.figures;

import chess.logic.moves.FigureMove;

import java.util.ArrayList;
import java.util.List;

import static chess.logic.figures.Bishop.addBishopMoves;
import static chess.logic.figures.Rook.addRookMoves;

public class Queen extends Figure {
    public Queen(FigureColor color) {
        super(color, false);
    }

    @Override
    public List<FigureMove> getAllMovesRegardingToBoard() {
        List<FigureMove> moves = new ArrayList<>();
        addQueenMoves(moves);

        return moves;
    }

    private static void addQueenMoves(List<FigureMove> moves) {
        addRookMoves(moves);
        addBishopMoves(moves);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        return o != null && getClass() == o.getClass();
    }

    @Override
    public String toString() {
        return getColorSymbol() + "Q";
    }

    @Override
    public String getColorSymbol() {
        return (color == FigureColor.WHITE) ? "w" : "b";
    }
}
