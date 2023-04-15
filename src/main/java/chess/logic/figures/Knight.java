package chess.logic.figures;

import chess.logic.moves.FigureMove;

import java.util.ArrayList;
import java.util.List;

public class Knight extends Figure {
    public Knight(FigureColor color) {
        super(color, true);
    }

    @Override
    public List<FigureMove> getAllMovesRegardingToBoard() {
        List<FigureMove> moves = new ArrayList<>();
        addKnightMoves(moves);

        return moves;
    }

    private static void addKnightMoves(List<FigureMove> moves) {
        moves.add(new FigureMove(-1, -2));
        moves.add(new FigureMove(1, -2));
        moves.add(new FigureMove(2, -1));
        moves.add(new FigureMove(2, 1));
        moves.add(new FigureMove(1, 2));
        moves.add(new FigureMove(-1, 2));
        moves.add(new FigureMove(-2, 1));
        moves.add(new FigureMove(-2, -1));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        return o != null && getClass() == o.getClass();
    }

    @Override
    public String toString() {
        return getColorSymbol() + "N";
    }

    @Override
    public String getColorSymbol() {
        return (color == FigureColor.WHITE) ? "w" : "b";
    }
}
