package chess.logic.board;

import chess.logic.figures.Figure;

import java.util.Objects;

public class BoardSquare {
    private Figure figure;
    private final SquareControls squareControls;

    public BoardSquare(Figure figure) {
        this.figure = figure;
        squareControls = new SquareControls();
    }

    public void setUncontrolledAndUnavailable() {
        squareControls.setUncontrolledAndAvailable();
    }

    public Figure getFigure() {
        return figure;
    }

    public boolean isControlledByWhite() {
        return squareControls.isControlledByWhite();
    }

    public boolean isControlledByBlack() {
        return squareControls.isControlledByBlack();
    }

    public boolean isAvailableForWhiteKing() {
        return squareControls.isAvailableForWhiteKing();
    }

    public boolean isAvailableForBlackKing() {
        return squareControls.isAvailableForBlackKing();
    }

    public void setFigure(Figure figure) {
        this.figure = figure;
    }

    public void setControlledByWhite(boolean controlledByWhite) {
        squareControls.setControlledByWhite(controlledByWhite);
    }

    public void setControlledByBlack(boolean controlledByBlack) {
        squareControls.setControlledByBlack(controlledByBlack);
    }

    public void setUnavailableForWhiteKing() {
        squareControls.setUnavailableForWhiteKing();
    }

    public void setUnavailableForBlackKing() {
        squareControls.setUnavailableForBlackKing();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BoardSquare that = (BoardSquare) o;
        return Objects.equals(figure, that.figure) && Objects.equals(squareControls, that.squareControls);
    }

    @Override
    public int hashCode() {
        return Objects.hash(figure, squareControls);
    }
}
