package chess.logic.figures;

import chess.logic.moves.FigureMove;

import java.util.List;

public class None extends Figure {
    public None() {
        super(FigureColor.NONE, false);
    }

    public FigureColor getColor() {
        return FigureColor.NONE;
    }

    public List<FigureMove> getAllMovesRegardingToBoard() {
        return null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        return o != null && getClass() == o.getClass();
    }

    @Override
    public String getColorSymbol() {
        return null;
    }

    @Override
    public String toString() {
        return "  ";
    }
}
