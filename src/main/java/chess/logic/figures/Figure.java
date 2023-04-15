package chess.logic.figures;


import chess.logic.moves.FigureMove;
import chess.utils.PinDetails;

import java.util.List;
import java.util.Objects;

public abstract class Figure {
    protected FigureColor color;
    protected boolean canJump;
    protected PinDetails pinDetails;


    public Figure(FigureColor color, boolean canJump) {
        this.color = color;
        this.canJump = canJump;
        this.pinDetails = new PinDetails();
    }

    public abstract List<FigureMove> getAllMovesRegardingToBoard();

    public abstract String getColorSymbol();

    public FigureColor getColor() {
        return color;
    }

    public boolean canJump() {
        return canJump;
    }

    public boolean isPinned() {
        return pinDetails.isPinned();
    }
    public PinDetails getPinDetails() {
        return pinDetails;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Figure figure = (Figure) o;
        return canJump == figure.canJump && color == figure.color && Objects.equals(pinDetails, figure.pinDetails);
    }

    @Override
    public int hashCode() {
        return Objects.hash(color, canJump, pinDetails);
    }
}
