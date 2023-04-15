package chess.logic.figures;

import chess.logic.board.SquareColor;
import chess.logic.moves.FigureMove;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Bishop extends Figure {
    SquareColor squareColor;

    public Bishop(FigureColor color) {
        super(color, false);
    }


    @Override
    public List<FigureMove> getAllMovesRegardingToBoard() {
        List<FigureMove> moves = new ArrayList<>();
        addBishopMoves(moves);

        return moves;
    }

    protected static void addBishopMoves(List<FigureMove> moves) {
        for (int delta = -1; delta > -8; delta--)
            moves.add(new FigureMove(delta, delta));

        for (int delta = -1; delta > -8; delta--)
            moves.add(new FigureMove(delta, -delta));

        for (int delta = 1; delta < 8; delta++)
            moves.add(new FigureMove(delta, delta));

        for (int delta = 1; delta < 8; delta++)
            moves.add(new FigureMove(delta, -delta));
    }

    public SquareColor getSquareColor() {
        return squareColor;
    }

    public void setSquareColor(SquareColor squareColor) {
        this.squareColor = squareColor;
    }


    @Override
    public String toString() {
        return getColorSymbol() + "B";
    }

    public String getColorSymbol() {
        return (color == FigureColor.WHITE) ? "w" : "b";
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Bishop bishop = (Bishop) o;
        return squareColor == bishop.squareColor;
    }

    @Override
    public int hashCode() {
        return Objects.hash(squareColor);
    }
}
