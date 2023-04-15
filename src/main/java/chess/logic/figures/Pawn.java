package chess.logic.figures;

import chess.logic.moves.FigureMove;

import java.util.ArrayList;
import java.util.List;

public class Pawn extends Figure {
    public Pawn(FigureColor color) {
        super(color, false);
    }

    @Override
    public List<FigureMove> getAllMovesRegardingToBoard() {
        List<FigureMove> moves = new ArrayList<>();
        moves.add(new FigureMove(0, 1));
        moves.add(new FigureMove(0, 2));
        moves.add(new FigureMove(-1, 1));
        moves.add(new FigureMove(1, 1));

        moves.add(new FigureMove(0, -1));
        moves.add(new FigureMove(0, -2));
        moves.add(new FigureMove(-1, -1));
        moves.add(new FigureMove(1, -1));

        return moves;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        return o != null && getClass() == o.getClass();
    }

    @Override
    public String toString() {
        return getColorSymbol() + "P";
    }

    @Override
    public String getColorSymbol() {
        return (color == FigureColor.WHITE) ? "w" : "b";
    }


}
